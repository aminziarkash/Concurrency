package com.az.dev.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aziarkash on 4-7-2016.
 */
public class MyReentrantLockClass extends Thread {

    public static void main(String[] args) {
        MyReentrantLockClass app = new MyReentrantLockClass();
        MyReentrantLockClass app2 = new MyReentrantLockClass();
        app.start();
        app2.start();

    }

    @Override
    public void run() {
        acquireLock();
    }

    private void acquireLock() {
        int count = 0;

        // attempt to acquire a lock
        Lock lock1 = new ReentrantLock();

        try {
            // boolean locked = lock1.tryLock(); // try without waiting
            boolean locked = lock1.tryLock(3000, TimeUnit.MILLISECONDS); // try with waiting
            if (locked) {
                try {
                    // do work
                    for (int i = 0; i < 100; i++) {
                        count++;
                    }
                } finally {     // to ensure we unlock
                    lock1.unlock();
                }
            }
            System.out.println(count);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught!");
        }
    }

    private void bla() {
        // deadlock avoidance with tryLock method
        Lock lock2 = new ReentrantLock();
        Lock lock3 = new ReentrantLock();
        boolean aq2 = lock2.tryLock();
        boolean aq3 = lock3.tryLock();
        try {
            if (aq2 && aq3) {
                // work
            }
        } finally {
            if (aq3) {
                lock3.unlock(); // don't unlock if not locked
            }
            if (aq2) {
                lock2.unlock();
            }
        }

        //
        loop2:
        while (true) {
            boolean aq33 = lock3.tryLock();
            boolean aq22 = lock2.tryLock();
            try {
                if (aq22 && aq33) {
                    // work
                    break loop2;
                }
            } finally {
                if (aq33) {
                    lock3.unlock();
                }
                if (aq22) {
                    lock2.unlock();
                }
            }
        }
    }
}
