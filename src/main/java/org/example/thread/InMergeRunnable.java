package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InMergeRunnable implements Runnable, Inputable, Outputable {

    long received;
    long value;
    List<Inputable> outputs = new ArrayList<>();

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value *= received;
        for (Inputable output : outputs) {
            output.receive(value);
        }
    }

    @Override
    public void receive(long value) {
        received = value;
    }

    @Override
    public void addOutput(Inputable output) {
        this.outputs.add(output);
    }
}
