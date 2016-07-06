package com.az.dev.concurrency.executors.executor;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class Order implements Runnable {

    private String name;

    public Order(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Ordering\t:\t" + name);
    }
}
