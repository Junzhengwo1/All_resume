package com.kou.selfann.demo;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {

 

    public Test() {
    }

    public void test(){

    }
    public static void main(String[] args) {

        int i = 0;
        //i++;
        ++i;
        System.out.println(i);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        Thread thread = new Thread();
        thread.start();
//        thread.start();
        thread.run();

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();

    }



}


