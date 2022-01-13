package org.example.thread;

public class PrintRunnable implements Runnable, Inputable {
    private long value;

    @Override
    public void run() {
        if (value < 1000000000000000L)
            System.out.println(value);
    }

    @Override
    public void receive(long value) {
        this.value = value;
    }
}
