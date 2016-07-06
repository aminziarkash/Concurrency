package com.az.dev.concurrency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziarkash on 4-7-2016.
 */
public class ConcurrentCollectionsAndDequeue implements Runnable {

    // Shared by all threads
    private List<Integer> list = new ArrayList<>();

    public ConcurrentCollectionsAndDequeue() {
        // add some elements
        for (int i = 0; i < 100_000; i++) {
            list.add(i);
        }
    }

    // might run concurrently, you cannot be sure. To be safe, we must assume it does
    @Override
    public void run() {
        String tName = Thread.currentThread().getName();
        while (!list.isEmpty()) {
            System.out.println(tName + " removed " + list.remove(0));
        }
    }

    // might throw ArrayIndexOutOfBoundsException or IndexOutOfBoundsException etc.
    public static void main(String[] args) {
        ConcurrentCollectionsAndDequeue alr = new ConcurrentCollectionsAndDequeue();
        Thread t1 = new Thread(alr);
        Thread t2 = new Thread(alr);
        t1.start();
        t2.start();
    }
}
