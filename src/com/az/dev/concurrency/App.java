package com.az.dev.concurrency;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by aziarkash on 15-6-2016.
 */
public class App {

    public static void main(String[] args) {

        App app = new App();

        app.atomicVariablesExample();

        app.boundedQueuesExample();

        try {
            app.linkedTransferQueueExample();
            app.linkedTransferQueueExample1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void linkedTransferQueueExample() throws InterruptedException {
        TransferQueue<Integer> tq = new LinkedTransferQueue<>();        // not bounded
        boolean b1 = tq.add(1);                                         // returns true if added or throws IllegalStateException if full
        tq.put(2);                                                      // blocks if bounded and full
        boolean b3 = tq.offer(3);                                       // returns true if added or false if bounded and full (recommended over add())
        boolean b4 = tq.offer(4, 10, TimeUnit.MILLISECONDS);            // returns true if added within the given time, false if bound and full
        tq.transfer(5);                                                 // blocks until this element is consumed
        boolean b6 = tq.tryTransfer(6);                                 // returns true if consumed by an awaiting thread or returns false without adding if there was no awaiting consumer
        boolean b7 = tq.tryTransfer(7, 10, TimeUnit.MILLISECONDS);      // will wait the given time for a consumer
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
}

