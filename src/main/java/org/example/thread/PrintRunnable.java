package org.example.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PrintRunnable implements Runnable, Inputable {

    BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<Long>();

    @Override
    public void run() {
        try {
            while (true) {
                long received = receivedQueue.take();
                System.out.println(received);
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
}
