package com.az.dev.concurrency.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class RainBow {

    private Lock myLock = new ReentrantLock();

    private static List<String> colors = new ArrayList<>();

    public void addColor(String newColor) {
        myLock.lock();
        try {
            colors.add(newColor);
        } finally {
            myLock.unlock();
        }
    }

    public static void main(String[] args) {
        new RainBow().addColor("Blue");
        System.out.println("Color added to the rainbow\t:\t" + colors);
    }
}
