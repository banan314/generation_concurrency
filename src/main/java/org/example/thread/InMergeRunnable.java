package org.example.thread;

import org.example.Scheduler;
import org.example.UniquePriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMergeRunnable implements Runnable, Receiver, Sender {

    final BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<>();
    final Scheduler scheduler;
    final List<Receiver> outputs = new ArrayList<>();
    final PriorityQueue<Long> priorityQueue = new UniquePriorityQueue<>();
    long min, max;

    public InMergeRunnable(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                scheduler.awaitMultRun();
                scheduler.setMergingComplete(false);
                while(!receivedQueue.isEmpty()) {
                    long received = receivedQueue.take();
                    priorityQueue.add(received);
                }
                if(!priorityQueue.isEmpty()) {
                    min = priorityQueue.peek();

                    // maximum number to which we pass the numbers from the priority queue. The minimum factor is 2, and
                    // we pass a number which equals min in a batch from the copy thread. So the minimum number in the next
                    // batch is equal to max = 2 * min
                    max = 2 * min;
                }
                while(!priorityQueue.isEmpty()) {
                    if(priorityQueue.peek() <= max) {
                        long value = priorityQueue.poll();
                        for (Receiver output : outputs) {
                            output.receive(value);
                        }
                    } else
                        break;
                }
                scheduler.setMergingComplete(true);
                scheduler.setCopyComplete(false);
                scheduler.allowMultToRun();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @Override
    public void receive(long value) {
        try {
            receivedQueue.put(value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @Override
    public void addOutput(Receiver output) {
        this.outputs.add(output);
    }
}
