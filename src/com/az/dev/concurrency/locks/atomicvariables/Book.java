package com.az.dev.concurrency.locks.atomicvariables;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class Book {

    String title;

    AtomicInteger copiesSold = new AtomicInteger(0);

    Book(String title) {
        this.title = title;
    }

    public void newSale() {
        copiesSold.incrementAndGet();
    }

    public void returnBook() {
        copiesSold.decrementAndGet();
    }
}
