package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CopyRunnable implements Runnable, Inputable, Outputable {

    long value;
    List<Inputable> outputs = new ArrayList<>();

    @Override
    public void run() {
        for (Inputable output : outputs) {
            output.receive(value);
        }
    }

    @Override
    public void receive(long value) {
        this.value = value;
    }

    @Override
    public void addOutput(Inputable output) {
        this.outputs.add(output);
    }

    public void setValue(long value) {
        this.value = value;
    }
}
