package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.example.Scheduler;
import java.util.concurrent.LinkedBlockingQueue;

public class MultRunnable implements Runnable, Receiver, Sender {

    final int n;
    final BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<>();
    final Scheduler scheduler;
    final List<Receiver> outputs = new ArrayList<>();

    public MultRunnable(int n, Scheduler scheduler) {
        this.n = n;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(scheduler.isMergingComplete()) {
                    while(!receivedQueue.isEmpty()) {
                        long received = receivedQueue.take();
                        for (Receiver output : outputs) {
                            output.receive(received * n);
                        }
                    }
                    if(scheduler.isCopyComplete() && receivedQueue.isEmpty())
                        scheduler.notifyThatMultComplete();
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
    public void addOutput(Receiver output) {
        this.outputs.add(output);
    }
}
