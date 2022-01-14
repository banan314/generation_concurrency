package org.example.thread;

import org.example.CyclicBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CopyRunnable implements Runnable, Inputable, Outputable {

    final BlockingQueue<Long> receivedQueue = new LinkedBlockingQueue<>();
    final CyclicBarrier cyclicBarrier;
    final List<Inputable> outputs = new ArrayList<>();

    public CopyRunnable(CyclicBarrier cyclicBarrier) {
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
                if(!(cyclicBarrier.isMultRunAllowed()) && cyclicBarrier.isMergingComplete())
                    cyclicBarrier.setMultRunAllowed(true);
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
