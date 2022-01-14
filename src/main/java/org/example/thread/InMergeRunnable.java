package org.example.thread;

import org.example.CyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InMergeRunnable implements Runnable, Inputable, Outputable {

    BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<Long>();
    CyclicBarrier cyclicBarrier;
    List<Inputable> outputs = new ArrayList<>();

    public InMergeRunnable(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long received = receivedQueue.take();
                for (Inputable output : outputs) {
                    output.receive(received);
                }
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
