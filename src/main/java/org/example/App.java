package org.example;

import org.example.thread.CopyRunnable;
import org.example.thread.InMergeRunnable;
import org.example.thread.MultRunnable;
import org.example.thread.PrintRunnable;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        MultRunnable multBy2 = new MultRunnable(2),
                multBy3 = new MultRunnable(3),
                multBy5 = new MultRunnable(5);
        CopyRunnable copyRunnable = new CopyRunnable();
        InMergeRunnable inMergeRunnable = new InMergeRunnable();
        PrintRunnable printRunnable = new PrintRunnable();

        multBy2.addOutput(inMergeRunnable);
        multBy3.addOutput(inMergeRunnable);
        multBy5.addOutput(inMergeRunnable);
        inMergeRunnable.addOutput(copyRunnable);
        copyRunnable.addOutput(printRunnable);
        copyRunnable.addOutput(multBy2);
        copyRunnable.addOutput(multBy3);
        copyRunnable.addOutput(multBy5);

        copyRunnable.receive(1);

        Thread multBy2Thread = new Thread(multBy2),
                multBy3Thread = new Thread(multBy3),
                multBy5Thread = new Thread(multBy5),
                copyThread = new Thread(copyRunnable),
                inMergeThread = new Thread(inMergeRunnable),
                printThread = new Thread(printRunnable);

        copyThread.start();
        multBy2Thread.start();
        multBy3Thread.start();
        multBy5Thread.start();
        inMergeThread.start();
        printThread.start();

//        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println( "Hello World!" );
    }
}
