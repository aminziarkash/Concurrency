package com.az.dev.concurrency.forkjoinframework;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by aziarkash on 6-7-2016.
 */
public class CalcSum extends RecursiveTask<Integer> {       // CalcSum extends RecursiveTask <Integer>

    private int UNIT_SIZE = 15;

    private int[] values;

    private int startPos;

    private int endPos;

    public CalcSum(int[] values) {
        this(values, 0, values.length);
    }

    public CalcSum(int[] values, int startPos, int endPos) {
        this.values = values;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    protected Integer compute() {       // For CalcSum, overridden method compute() returns an integer value
        final int currentSize = endPos - startPos;
        if (currentSize <= UNIT_SIZE) {
            return computeSum();
        } else {
            int center = currentSize / 2;
            int leftEnd = startPos + center;
            CalcSum leftSum = new CalcSum(values, startPos, leftEnd);
            leftSum.fork();         // Calling fork on leftSum makes it execute asynchronous

            int rightStart = startPos + center + 1;
            CalcSum rightSum = new CalcSum(values, rightStart, endPos);
            return (rightSum.compute() + leftSum.join());       // leftSum.join waits until it returns a value;
                                                                // compute is main computation performed by task
        }
    }

    private int computeSum() {
        int sum = 0;
        for (int i = startPos; i <endPos; i++) {
            sum += values[i];
        }
        System.out.println("Sum(" + startPos + "-" + endPos + "):" + sum);
        return sum;
    }

    public static void main(String[] args) {
        int[] intArray = new int[100];
        Random randomValues = new Random();

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = randomValues.nextInt(10);
        }

        ForkJoinPool pool = new ForkJoinPool();         // Instantiates a ForkJoinPool
        CalcSum calculator = new CalcSum(intArray);

        System.out.println(pool.invoke(calculator));    // invoke() awaits and obtains result
    }
}
