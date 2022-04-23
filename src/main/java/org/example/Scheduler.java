package org.example;

import java.util.concurrent.CountDownLatch;

public class Scheduler {

    final int initialCount;

    boolean mergingComplete = true;
    boolean copyComplete = false;
    CountDownLatch countDownLatch;
    public Scheduler(Integer initialCount) {
        this.initialCount = initialCount;
        this.countDownLatch = new CountDownLatch(initialCount);
    }

    synchronized public boolean isMergingComplete() {
        return mergingComplete;
    }

    synchronized public void setMergingComplete(boolean mergingComplete) {
        this.mergingComplete = mergingComplete;
    }

    synchronized public boolean isCopyComplete() {
        return copyComplete;
    }

    synchronized public void setCopyComplete(boolean copyComplete) {
        this.copyComplete = copyComplete;
    }

    public void awaitMultRun() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void notifyThatMultComplete() {
        countDownLatch.countDown();
    }

    public void allowMultToRun() {
        countDownLatch = new CountDownLatch(initialCount);
    }
}
