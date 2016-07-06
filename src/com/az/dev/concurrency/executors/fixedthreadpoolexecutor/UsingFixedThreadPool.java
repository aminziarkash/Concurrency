package com.az.dev.concurrency.executors.fixedthreadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class UsingFixedThreadPool {

    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new LoopTaskA());
        executorService.execute(new LoopTaskA());
        executorService.execute(new LoopTaskA());
//        executorService.execute(new LoopTaskA());  // UNCOMMENT FOR PRESENTATION
//        executorService.execute(new LoopTaskA());
//        executorService.execute(new LoopTaskA());

        executorService.shutdown();

//        executorService.execute(new LoopTaskA());   // UNCOMMENT FOR DEMO (RejectedExecutionException)

        System.out.println("Main thread ends here...");
    }
}
