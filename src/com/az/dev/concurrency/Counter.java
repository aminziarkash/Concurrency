package com.az.dev.concurrency;

/**
 * Created by aziarkash on 15-6-2016.
 */
public class Counter {

    private int counter;

    public void increment() {
        counter++; // a single line is not atomic
    }

    public int getValue() {
        return counter;
    }
}
