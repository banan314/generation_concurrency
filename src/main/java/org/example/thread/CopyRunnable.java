package org.example.thread;

import org.example.Scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CopyRunnable implements Runnable, Receiver, Sender {

    final BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<>();
    final Scheduler scheduler;
    final List<Receiver> outputs = new ArrayList<>();

    public CopyRunnable(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long received = receivedQueue.take();
                for (Receiver output : outputs) {
                    output.receive(received);
                    TimeUnit.MILLISECONDS.sleep(50);
                }
                if(scheduler.isMergingComplete() && receivedQueue.isEmpty())
                    scheduler.setCopyComplete(true);
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
