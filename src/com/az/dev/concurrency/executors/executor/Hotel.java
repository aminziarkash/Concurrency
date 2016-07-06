package com.az.dev.concurrency.executors.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Created by aziarkash on 5-7-2016.
 */
class Hotel implements Executor {

    final Queue<Runnable> custQueue = new ArrayDeque<>();

    @Override
    public void execute(Runnable r) {   //  Implement the execute method
        synchronized (custQueue) {
            custQueue.offer(r);         // Add Runnable object to a queue
        }
        processEarliestOrder();
    }

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.execute(new Order("Tea"));
        hotel.execute(new Order("Coffee"));
        hotel.execute(new Order("Burger"));
    }

    private void processEarliestOrder() {
        synchronized (custQueue) {
            Runnable task = custQueue.poll();   // Retrieve Runnable object from execute
            new Thread(task).start();           // Start new thread for executing submitted task
        }
    }
}
