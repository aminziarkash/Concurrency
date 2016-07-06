package com.az.dev.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aziarkash on 4-7-2016.
 */
public class MyConditionClass {

    private void conditionClassExample() {
        // part 1
        Lock lock = new ReentrantLock();
        Condition blockingPoolA = lock.newCondition();

        // part 2
        lock.lock();
        try {
            blockingPoolA.await();              // wait here, the lock will be reacquired
            // do some work
        } catch (InterruptedException e) {
            System.out.println("Interrupted during await");
            e.printStackTrace();
        } finally {
            lock.unlock();      // To ensure we unlock, we must manually release the lock
        }

        // part 3
        lock.lock();
        try {
            // work
            blockingPoolA.signalAll();      // wake all awaiting threads
        } finally {
            lock.unlock();                  // now an awaken thread can run
        }

        Lock lock1 = new ReentrantLock();
        Condition blockingPoolAA = lock.newCondition();
        Condition blockingPoolBB = lock.newCondition();
    }

}
