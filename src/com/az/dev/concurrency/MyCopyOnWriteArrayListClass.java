package com.az.dev.concurrency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by aziarkash on 4-7-2016.
 */
public class MyCopyOnWriteArrayListClass {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        System.out.println("ArrayList1 content\t\t:\t" + list);

        CopyOnWriteArrayList list1 = new CopyOnWriteArrayList();
        list1.addIfAbsent("A");
        list1.addIfAbsent("C");
        System.out.println("\nCopyOnWriteArrayList content\t:\t" + list1);
        list1.addAll(list);     // adding all from ArrayList above
        System.out.println("CopyOnWriteArrayList content after adding the ArrayList1 content\t:\t" + list1);

        ArrayList list2 = new ArrayList();
        list2.add("A");
        list2.add("E");
        System.out.println("\nArrayList2 content\t:\t" + list2);

        list1.addAllAbsent(list2);  // Add all absent from list2 to list1
        System.out.println("\nCopyOnWriteArrayList after adding absent members from ArrayList2\t:\t" + list1);

        System.out.println("\nTrying to remove 'E' from the list");
        removeAnElementFromTheList(list1);
    }

    private static void iteratingCOWAL() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.add("A");
        list.add("B");
        list.add("C");

        Iterator it = list.iterator();
        list.add("D");  // Will not be available for the iterator
        list.add("E");

        while (it.hasNext()) {
            String s = (String) it.next();
            System.out.println(s);  // prints [A, B, C] only
        }
    }

    private static void removeAnElementFromTheList(CopyOnWriteArrayList cowl) {
        Iterator it = cowl.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            if (s.equals("E")) {
                try {
                    it.remove();    // This will throw the UnsupportedOperationException
                } catch (UnsupportedOperationException e) {
                    System.out.println("UnsupportedOperationException caught!");
                }
            }
        }
    }
}

class MyCopyOnWriteArrayListThread extends Thread {

    private static CopyOnWriteArrayList cowal = new CopyOnWriteArrayList();

    @Override
    public void run() {
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Child thread is updating the list");
        cowal.add("C");
    }

    public static void main(String[] args) {
        cowal.add("A");
        cowal.add("B");

        MyCopyOnWriteArrayListThread thread = new MyCopyOnWriteArrayListThread();
        thread.start();

        Iterator it = cowal.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            System.out.println("Main thread is iterating and the object is\t:\t" + s);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nFinal list content\t:\t" + cowal);
    }
}

// PROBLEM WITH ArrayList
class MyThread extends Thread {

    static ArrayList<String> list = new ArrayList<>();

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Caught an InterruptedException");
        }
        System.out.println("Child thread updating");
        list.add("D");
    }

    public static void main(String[] args) throws InterruptedException {
        list.add("A");
        list.add("B");
        list.add("C");

        // start the child thread
        MyThread thread = new MyThread();
        thread.start();

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println("Main thread iterating list, and the current object is\t:\t" + s);
            Thread.sleep(1500);
        }
    }
}
