package org.example;

import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest extends TestCase {

    CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
    Thread worker;

//    public void setUp() throws Exception {
//
//
//        super.setUp();
//    }

    public void tearDown() throws Exception {
        worker.interrupt();
        worker.join();
    }

    public void testAwait() {
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                    cyclicBarrier.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        worker.start();
        cyclicBarrier.await();
        assertEquals(true, true);
    }

    public void testReset() {
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        cyclicBarrier.countDown();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        worker.start();
        cyclicBarrier.await();
        cyclicBarrier.reset();
        cyclicBarrier.await();
        assertEquals(true, true);
    }
}