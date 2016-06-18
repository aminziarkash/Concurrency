package com.az.dev.concurrency;

/**
 * Created by aziarkash on 15-6-2016.
 */
public class App {

    public static void main(String[] args) {

        App app = new App();

        app.atomicVariablesExample();

        app.reentrantLockExample();
    }

    private void atomicVariablesExample() {
        // Counter counter = new Counter(); // The shared thread object
        CounterAtomic counter = new CounterAtomic();
        IncrementerThread it1 = new IncrementerThread(counter);
        IncrementerThread it2 = new IncrementerThread(counter);
        it1.start(); // thread 1 increments the count by 10000
        it2.start(); // thread 2 increments the count by 10000
        try {
            it1.join(); // wait for thread 1 to finish
            it2.join(); // wait for thread 2 to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2 Threads incrementing the same int variable from 0 to 10.000\t:\t" + counter.getValue());
    }

    private void reentrantLockExample() {
        
    }
}
