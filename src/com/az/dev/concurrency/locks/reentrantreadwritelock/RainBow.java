package com.az.dev.concurrency.locks.reentrantreadwritelock;

/**
 * Created by aziarkash on 5-7-2016.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RainBow {

    static Map<Integer, String> colors = new HashMap<>();

    private static int pos;

    private final ReadWriteLock myLock = new ReentrantReadWriteLock();

    public void addColor(String newColor) {
        myLock.writeLock().lock();
        try {
            colors.put(new Integer(++pos), newColor);
        } finally {
            myLock.writeLock().unlock();
        }
    }

    public void display() {
        myLock.readLock().lock();
        try {
            for (String s : colors.values()) {
                System.out.println(s);
            }
        } finally {
            myLock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        new RainBow().addColor("Blue");
        new RainBow().addColor("Red");
        new RainBow().addColor("Green");
        new RainBow().addColor("Yellow");
        System.out.println("Colors in the rainbow list\t:\t" + colors);
    }
}
