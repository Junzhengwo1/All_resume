package com.kou.selfann.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FIndPrime {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                50,
                55,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        AtomicInteger base =new AtomicInteger(22222222) ;
        int target = 1000;

        HashSet<Integer> integers = new HashSet<>();
//        //模拟处理
        //AtomicInteger i = new AtomicInteger();
       long start = System.currentTimeMillis();
//        List<CompletableFuture> completableFutures = new ArrayList<>();
//        try {
//            while (target.get() > 0) {
//                base.incrementAndGet();
//                CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
//                    if (isPrime(base.get())) {
//                        target.getAndDecrement();
//                        integers.add(base.intValue());
//                        System.out.println("当前线程"+Thread.currentThread().getId()+"字数->"+base.intValue());
//                    }
//                }, poolExecutor);
//                completableFutures.add(voidCompletableFuture);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //for循环有问题
        while (target > 0) {
            MyCallable myCallable = new MyCallable(base.incrementAndGet());
            Future<Integer> result = poolExecutor.submit(myCallable);
            // result>0表示当前数字是质数
            if (result.get() > 0) {
                target--;
                integers.add(result.get());
            }
        }

        //CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        long end = System.currentTimeMillis();
        System.out.println("找到质数：" + integers.size() + "个;共耗时：" + (end - start));
        // 关闭线程池，释放资源
        poolExecutor.shutdown();

    }


//    /**
//     * 判断一个数是不是质数
//     **/
//    public static boolean isPrime(Integer num) {
//        if (num <= 1) {
//            return false;
//        }
//        for (int i = 2; i < Math.sqrt(num); i++) {
//            if (num % i == 0) {
//                return false;
//            }
//        }
//        return true;
//    }

}
