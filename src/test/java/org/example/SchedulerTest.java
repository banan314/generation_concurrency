package org.example;

import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

public class SchedulerTest extends TestCase {

    Scheduler scheduler = new Scheduler(1);
    Thread worker;

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
                    scheduler.notifyThatMultComplete();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        worker.start();
        scheduler.awaitMultRun();
        assertEquals(true, true);
    }

    public void testReset() {
        worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        scheduler.notifyThatMultComplete();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        worker.start();
        scheduler.awaitMultRun();
        scheduler.allowMultToRun();
        scheduler.awaitMultRun();
        assertEquals(true, true);
    }
}