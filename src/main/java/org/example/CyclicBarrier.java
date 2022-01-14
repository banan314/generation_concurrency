package org.example;

public class CyclicBarrier {
    final int initialCount;
    Integer count;

    public CyclicBarrier(Integer initialCount) {
        this.initialCount = initialCount;
        this.count = initialCount;
    }

    public void await() {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void countDown() {
        count--;
        if(count == 0)
            notifyAll();
    }

    public void reset() throws Exception {
        if(count == 0)
            count = initialCount;
        else {
            notifyAll();
            throw new Exception("Some threads are waiting on the cyclic barier");
        }
    }
}
