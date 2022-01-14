package org.example.thread;

import org.example.CyclicBarrier;
import org.example.UniquePriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMergeRunnable implements Runnable, Inputable, Outputable {

    BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<Long>();
    CyclicBarrier cyclicBarrier;
    List<Inputable> outputs = new ArrayList<>();
    PriorityQueue<Long> priorityQueue = new UniquePriorityQueue<>();
    long min, max;

    public InMergeRunnable(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            while (true) {
                cyclicBarrier.await();
                cyclicBarrier.updateCanMultRun(false);
                while(!receivedQueue.isEmpty()) {
                    long received = receivedQueue.take();
                    priorityQueue.add(received);
                }
                if(!priorityQueue.isEmpty()) {
                    min = priorityQueue.peek();
                    max = 2 * min;
                }
                while(!priorityQueue.isEmpty()) {
                    if(priorityQueue.peek() < max) {
                        long value = priorityQueue.poll();
                        for (Inputable output : outputs) {
                            output.receive(value);
                        }
                    } else
                        break;
                }
                cyclicBarrier.updateCanMultRun(true);
                cyclicBarrier.reset();
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
    public void addOutput(Inputable output) {
        this.outputs.add(output);
    }
}
