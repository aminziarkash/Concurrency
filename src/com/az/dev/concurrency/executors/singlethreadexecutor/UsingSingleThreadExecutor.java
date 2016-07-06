package com.az.dev.concurrency.executors.singlethreadexecutor;

import com.az.dev.concurrency.executors.fixedthreadpoolexecutor.LoopTaskA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class UsingSingleThreadExecutor {

    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newSingleThreadExecutor(); // Here is the single thread execotpr
        executorService.execute(new LoopTaskA());
        executorService.execute(new LoopTaskA());
        executorService.execute(new LoopTaskA());

        executorService.shutdown();

        System.out.println("Main thread ends here...");
    }
}
