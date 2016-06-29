package com.az.dev.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aziarkash on 15-6-2016.
 */
public class AtomicCounter {

    private AtomicInteger count = new AtomicInteger();

    // CAS (Compare And Swap) instructions
    public void increment() {
        count.getAndIncrement(); // atomic operation
    }

    public int getValue() {
        return count.intValue();
    }
}
