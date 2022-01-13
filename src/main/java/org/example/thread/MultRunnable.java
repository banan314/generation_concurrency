package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MultRunnable implements Runnable, Inputable, Outputable {

    BlockingQueue<Long> receivedQueue = new ArrayBlockingQueue<Long>(10);
    final int n;
    List<Inputable> outputs = new ArrayList<>();

    public MultRunnable(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long received = receivedQueue.take();
                for (Inputable output : outputs) {
                    output.receive(received * n);
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
