package com.az.dev.concurrency.locks.reentrantlock.problem;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class Inventory {

    int inStock;

    private String name;

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
        synchronized (loc1) {           // acquire lock on loc1
            synchronized (loc2) {       // acquire lock oon loc2
                loc2.stockOut(qty);
                loc1.stockIn(qty);

                System.out.println(loc1.inStock + "\t:\t" + loc2.inStock);
            }   // release lock on loc2
        }       // release lock on loc1
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
