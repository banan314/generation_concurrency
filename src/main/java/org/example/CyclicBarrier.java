package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CyclicBarrier {
    final int initialCount;
    boolean canMultRun = true;
    CountDownLatch countDownLatch;

    public CyclicBarrier(Integer initialCount) {
        this.initialCount = initialCount;
        this.countDownLatch = new CountDownLatch(initialCount);
    }

    public void updateCanMultRun(boolean canMultRun) {
        this.canMultRun = canMultRun;
    }

    public boolean isCanMultRun() {
        return this.canMultRun;
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
