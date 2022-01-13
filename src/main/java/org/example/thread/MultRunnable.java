package org.example.thread;

import java.util.ArrayList;
import java.util.List;

public class MultRunnable implements Runnable, Inputable, Outputable {
    long value;
    int n;
    List<Inputable> outputs = new ArrayList<>();

    public MultRunnable(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        value *= n;
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
}
