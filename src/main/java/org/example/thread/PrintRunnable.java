package org.example.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PrintRunnable implements Runnable, Inputable {

    BlockingQueue<Long> receivedQueue = new ArrayBlockingQueue<Long>(1);

    @Override
    public void run() {
        try {
            while (true) {
                long received = receivedQueue.take();
                if (received < 1000000000000000L)
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
