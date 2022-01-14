package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.example.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MultRunnable implements Runnable, Inputable, Outputable {

    final int n;
    final BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<>();
    final CyclicBarrier cyclicBarrier;
    final List<Inputable> outputs = new ArrayList<>();

    public MultRunnable(int n, CyclicBarrier cyclicBarrier) {
        this.n = n;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(cyclicBarrier.isMultRunAllowed()) {
                    while(!receivedQueue.isEmpty()) {
                        long received = receivedQueue.take();
                        for (Inputable output : outputs) {
                            output.receive(received * n);
                        }
                    }
                    cyclicBarrier.countDown();
                    TimeUnit.MILLISECONDS.sleep(50);
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
