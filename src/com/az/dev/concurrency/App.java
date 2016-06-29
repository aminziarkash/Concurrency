package com.az.dev.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by aziarkash on 15-6-2016.
 */
public class App {

    public static void main(String[] args) {

        App app = new App();

        // app.atomicVariablesExample();

        // app.reentrantLockExample();

        // app.conditionInterfaceExample();

        // app.reentrantReadWriteLockExample();

        // app.concurrentCollectionsAndDequeueExample();

        // app.boundedQueuesExample();

        try {
            // app.linkedTransferQueueExample();
            app.linkedTransferQueueExample1();
        } catch (InterruptedException e) {
            System.out.println("Exception");
        }
    }

    private void linkedTransferQueueExample1() throws InterruptedException {

        TransferQueue<Integer> tq = new LinkedTransferQueue<>(); // not bounded

        Integer i1 = tq.element();                              // gets without removing. Throws NoSuchElementException if empty

        Integer i2 = tq.peek();                                 // gets without removing, returns null if empty

        Integer i3 = tq.poll();                                 // removes the head of the queue, returns null if empty

        Integer i4 = tq.poll(10, TimeUnit.MILLISECONDS);        // removes the head of the queue, waits up to the time specified before returning null if empty

        Integer i5 = tq.remove();                               // removes the head of the queue, throws NoSuchElementException if empty

        Integer i6 = tq.take();                                 // removes the head of the queue, blocks until an element is ready

    }

    private void linkedTransferQueueExample() throws InterruptedException {

        TransferQueue<Integer> tq = new LinkedTransferQueue<>(); // not bounded

        boolean b1 = tq.add(1);                                         // returns true if added or throws IllegalStateException if full

        tq.put(2);                                                      // blocks if bounded and full

        boolean b3 = tq.offer(3);                                       // returns true if added or false if bounded and full (recommended over add())

        boolean b4 = tq.offer(4, 10, TimeUnit.MILLISECONDS);            // returns true if added within the given time, false if bound and full

        tq.transfer(5);                                                 // blocks until this element is consumed

        boolean b6 = tq.tryTransfer(6);                                 // returns true if consumed by an awaiting thread or returns false without adding if there was no awaiting consumer

        boolean b7 = tq.tryTransfer(7, 10, TimeUnit.MILLISECONDS);      // will wait the given time for a consumer

    }

    private void boundedQueuesExample(){
        BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(1);
        try {
            bq.put(42);
            bq.put(43);     // blocks until previous value is removed
        } catch(InterruptedException e) {
            // log and handle
            e.printStackTrace();
        }
    }

    private void concurrentCollectionsAndDequeueExample() {
        // check ArrayListRunnable class below
    }

    private void reentrantReadWriteLockExample() {
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        Lock readLock = rwl.readLock();
        Lock writeLock = rwl.writeLock();

        // check MaxValueCollection class below
    }

    private void conditionInterfaceExample() {

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

    private void atomicVariablesExample() {
        // Counter counter = new Counter(); // The shared thread object
        AtomicCounter counter = new AtomicCounter();
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
        Object obj = new Object();
        synchronized (obj) {    // traditional locking, blocks until acquired
                                // work
        }                       // release lock automatically

        // equivalent
        Lock lock = new ReentrantLock();
        lock.lock();        // blocks until acquired
        try {
            // do some work here
        } finally {     // to ensure we unlock
            lock.unlock();
        }

        // attempt to acquire a lock
        Lock lock1 = new ReentrantLock();
        // boolean locked = lock1.tryLock(); // try without waiting
        try {
            boolean locked = lock1.tryLock(3000, TimeUnit.MILLISECONDS); // try with waiting
            if (locked) {
                try {
                    // do work
                } finally {     // to ensure we unlock
                    lock1.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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



class MyThread extends Thread {

    static ArrayList<String> list = new ArrayList<>();

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Caught an exception");
        }
        System.out.println("Child thread updating");
        list.add("D");
    }

    public static void main(String[] args) throws InterruptedException {
        list.add("A");
        list.add("B");
        list.add("C");

        MyThread thread = new MyThread();
        thread.start();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println("Main thread iterating, and the current object is\t:\t" + s);
            Thread.sleep(1500);
        }
    }
}


class MaxValueCollection {
    private List<Integer> integers = new ArrayList<>();
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void add (Integer i) {
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

class ArrayListRunnable implements Runnable {

    // Shared by all threads
    private List<Integer> list = new ArrayList<>();

    public ArrayListRunnable() {
        // add some elements
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
    }

    // might run concurrently, you cannot be sure. To be safe, you must assume it does
    @Override
    public void run() {
        String tName = Thread.currentThread().getName();
        while (!list.isEmpty()) {
            System.out.println(tName + " removed " + list.remove(0));
        }
    }

    // might throw ArrayIndexOutOfBoundsException or IndexOutOfBoundsException etc.
    public static void main(String[] args) {
        ArrayListRunnable alr = new ArrayListRunnable();
        Thread t1 = new Thread(alr);
        Thread t2 = new Thread(alr);
        t1.start();
        t2.start();
    }
}


class A {

    public void add() {
        System.out.println("adding from A");
    }
}

class B extends A {

    public void show() {
        System.out.println("showing from B");
    }
}

class C {

    public static void main(String[] args) {
        B b = new B();
        b.add();
        b.show();

        A a = new A();
        a.add();

        A ab = new B();
        ab.add();
        ((B)ab).show();
    }
}