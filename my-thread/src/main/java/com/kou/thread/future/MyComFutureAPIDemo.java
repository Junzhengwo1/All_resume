package com.kou.thread.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 学习任务编排 api
 */
public class MyComFutureAPIDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        // 类似于观察者模式/
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "hello";
        });

        // String s = future.get();
        //System.out.println(s);
        String s1 = future.get(2L, TimeUnit.SECONDS);
        System.out.println(s1);

    }


}
