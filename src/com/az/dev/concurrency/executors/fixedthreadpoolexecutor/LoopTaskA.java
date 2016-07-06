package com.az.dev.concurrency.executors.fixedthreadpoolexecutor;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class LoopTaskA implements Runnable {

    private static int count = 0;
    private int id;

    public LoopTaskA() {
        this.id = ++count;
    }

    @Override
    public void run() {
        System.out.println("######## <TASK-" + id + "> STARTING ########");
        for (int i = 10; i > 0; i--) {
            System.out.println("<" + id + ">TICK TICK " + i);

            Random random = new Random();
            int duration = random.nextInt(1000);

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("******* <TASK-" + id + "> DONE ********");
    }
}
