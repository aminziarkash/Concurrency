package com.az.dev.concurrency.executors.callableandfuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class UsingCallableAndFuture {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IOException("Sleeping for too long");
                }

                System.out.println("Starting ...");
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finished.");
                return duration;
            }
        });

        int result = 0;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            IOException ex = (IOException) e.getCause();
            System.out.println(ex.getMessage());
        }
        System.out.println("Result is\t:\t" + result);

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                Random random = new Random();
//                int duration = random.nextInt(4000);
//
//                System.out.println("Starting");
//                try {
//                    Thread.sleep(duration);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Finished");
//            }
//        });

        executorService.shutdown();
    }
}
