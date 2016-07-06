package com.az.dev.concurrency;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by aziarkash on 4-7-2016.
 */
public class MyConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentHashMap chm = new ConcurrentHashMap<>();
        chm.put(1, "A");
        chm.put(2, "B");
        chm.putIfAbsent(3, "C");
        chm.putIfAbsent(1, "D");
        chm.remove(1, "D");
        chm.replace(2, "B", "E");

        System.out.println(chm);
    }
}

// modify map in safe manner
class ConcurrentHashMapThread extends Thread {

    static ConcurrentHashMap chm = new ConcurrentHashMap();

    public static void main(String[] args) {
        chm.put(1, "A");
        chm.put(2, "B");

        ConcurrentHashMapThread myThread = new ConcurrentHashMapThread();
        myThread.start();

        Set set = chm.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Integer i = (Integer) it.next();
            System.out.println("Main thread iterating map and current entry is\t:\t" + i + " " + chm.get(i));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(chm);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("Child thread updating map");
        chm.put(3, "C");
    }
}
