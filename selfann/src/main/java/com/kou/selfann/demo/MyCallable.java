package com.kou.selfann.demo;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {
    private final int num;

    public MyCallable(int num) {
        this.num = num;
    }

    @Override
    public Integer call() throws Exception {
        long startTime = System.currentTimeMillis();
        if (isPrime(num)) {
            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getId()+":找到质数：" + num + " 耗时：" + (endTime - startTime) + "ms");
            return num;
        }
        return 0;
    }

    /**
     * 判断一个数是不是质数
     **/
    public static boolean isPrime(Integer num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

}
