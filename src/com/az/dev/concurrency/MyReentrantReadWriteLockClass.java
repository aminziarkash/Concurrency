package com.az.dev.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by aziarkash on 4-7-2016.
 */
public class MyReentrantReadWriteLockClass {

    private List<Integer> integers = new ArrayList<>();

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        MyReentrantReadWriteLockClass app = new MyReentrantReadWriteLockClass();
        app.add(45);
        app.findMax();
    }

    public void add(Integer i) {
        rwl.writeLock().lock();     // one at a time
        try {
            integers.add(i);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public int findMax() {
        rwl.readLock().lock();      // many at a time
        try {
            return Collections.max(integers);
        } finally {
            rwl.readLock().unlock();
        }
    }
}
