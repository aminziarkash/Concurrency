package com.az.dev.concurrency.locks.interruptiblelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aziarkash on 5-7-2016.
 */
class Bus {
    Lock lock = new ReentrantLock();
    public void boardBus(String name) {
        System.out.println(name + "\t:\tboarded");
    }
}
class Employee extends Thread {
    Bus bus;
    String name;
    Employee(String name, Bus bus) {
        this.bus = bus;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            bus.lock.lockInterruptibly();       // Try to acquire lock while being available for interruption
            try {
                bus.boardBus(name);
            } finally {
                bus.lock.unlock();              // Release acquired lock in finally block
            }
        } catch(InterruptedException e) {
            System.out.println(name + "\t:\tInterrupted!!");
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String args[]) {
        Bus bus = new Bus();
        Employee e1 = new Employee("Amin", bus);
        e1.start();         // start thread
        e1.interrupt();     // interrupt thread
        Employee e2 = new Employee("Varun", bus);
        e2.start();
    }
}
