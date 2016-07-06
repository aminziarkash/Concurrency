package com.az.dev.concurrency.locks.reentrantlock.solution;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class Inventory {

    int inStock;

    String name;

    Lock lock = new ReentrantLock();

    public Inventory(String name) {
        this.name = name;
    }

    public void stockIn(long qty) {
        inStock += qty;
    }

    public void stockOut(long qty) {
        inStock -= qty;
    }
}

class Shipment extends Thread {

    private Inventory loc1, loc2;

    private int qty;

    public Shipment(Inventory loc1, Inventory loc2, int qty) {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.qty = qty;
    }

    @Override
    public void run() {
        if(loc1.lock.tryLock()) {           // tries to acquire a lock on object loc1.lock and returns immediately
            if(loc2.lock.tryLock()) {       // tries to acquire a lock on object loc2.lock and returns immediately
                loc2.stockOut(qty);
                loc1.stockIn(qty);

                System.out.println(loc1.inStock + "\t:\t" + loc2.inStock);

                loc2.lock.unlock();     // release lock on loc2
                loc1.lock.unlock();     // release lock on loc1
            } else {
                System.out.println("Locking false\t:\t" + loc2.name);   // if lock couldn't be acquired, output appropriate messages
            }
        } else {
            System.out.println("Locking false\t:\t:" + loc1.name);   // if lock couldn't be acquired, output appropriate messages
        }
    }

    public static void main(String[] args) {

        Inventory loc1 = new Inventory("Rijswijk");
        loc1.inStock = 100;
        Inventory loc2 = new Inventory("Amsterdam");
        loc2.inStock = 200;

        Shipment shipment1 = new Shipment(loc1, loc2, 1);
        Shipment shipment2 = new Shipment(loc2, loc1, 10);

        shipment1.start();
        shipment2.start();
    }
}
