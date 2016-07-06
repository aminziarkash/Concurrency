package com.az.dev.concurrency.executors.scheduledexecutorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by aziarkash on 6-7-2016.
 */
public class ReminderMgr {

    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);     // Call Executors.newScheduled-ThreadPool to get a
                                                                                // Scheduled-ExecutorService object.
    Reminder reminder = new Reminder();

    public static void main(String args[]) {
        ReminderMgr mgr = new ReminderMgr();
        mgr.sendReminders();
    }

    public void sendReminders() {
        service.scheduleAtFixedRate(reminder, 0, 5, SECONDS);        // Execute task reminder now and every 5 seconds
    }
}
