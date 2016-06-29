package com.az.dev.concurrency;

/**
 * Created by aziarkash on 15-6-2016.
 */
public class IncrementerThread extends Thread {

    // problem
    // private Counter counter;

    // solution
    private AtomicCounter counter;

    // problem
    // all instances are passed the same counter
//    public IncrementerThread(final Counter counter) {
//        this.counter = counter;
//    }

    // solution
    public IncrementerThread(final AtomicCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        // "i" is local and Thread-Safe
        for (int i = 0; i < 10_000; i++) {
            counter.increment();
        }
    }
}
