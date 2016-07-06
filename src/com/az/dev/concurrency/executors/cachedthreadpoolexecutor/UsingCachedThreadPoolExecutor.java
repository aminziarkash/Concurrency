package com.az.dev.concurrency.executors.cachedthreadpoolexecutor;

import com.az.dev.concurrency.executors.fixedthreadpoolexecutor.LoopTaskA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by aziarkash on 5-7-2016.
 */
public class UsingCachedThreadPoolExecutor {

    public static void main(String[] args) {
        System.out.println("Main thread starts here...");

        ExecutorService executorService = Executors.newCachedThreadPool(); // HERE IS THE CACHED THREAD POOL METHOD
        executorService.execute(new LoopTaskA());
        executorService.execute(new LoopTaskA());
        executorService.execute(new LoopTaskA());
        //        executorService.execute(new LoopTaskA());  // UNCOMMENT FOR PRESENTATION
        //        executorService.execute(new LoopTaskA());
        //        executorService.execute(new LoopTaskA());

        executorService.shutdown();

        System.out.println("Main thread ends here...");
    }

}
