package com.az.dev.concurrency.executors.scheduledexecutorservice;

/**
 * Created by aziarkash on 6-7-2016.
 */
public class Reminder implements Runnable {         // When started, this Runnable object sends
                                                    // out emails to all employees.
    @Override
    public void run() {
        // send reminder emails to all employees
        System.out.println("All Mails sent");
    }
}
