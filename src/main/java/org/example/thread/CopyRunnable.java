package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CopyRunnable implements Runnable, Inputable, Outputable {

    BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<Long>();
    List<Inputable> outputs = new ArrayList<>();

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
