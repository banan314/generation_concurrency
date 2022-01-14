package org.example;

import java.util.concurrent.CountDownLatch;

public class CyclicBarrier {

    final int initialCount;
    boolean multRunAllowed = true;
    boolean mergingComplete = true;
    CountDownLatch countDownLatch;

    public CyclicBarrier(Integer initialCount) {
        this.initialCount = initialCount;
        this.countDownLatch = new CountDownLatch(initialCount);
    }

    public boolean isMultRunAllowed() {
        return this.multRunAllowed;
    }

    public void setMultRunAllowed(boolean canMultRun) {
        this.multRunAllowed = canMultRun;
    }

    public boolean isMergingComplete() {
        return mergingComplete;
    }

    public void setMergingComplete(boolean mergingComplete) {
        this.mergingComplete = mergingComplete;
    }

    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void countDown() {
        countDownLatch.countDown();
    }

    public void reset() {
        countDownLatch = new CountDownLatch(initialCount);
    }
}
