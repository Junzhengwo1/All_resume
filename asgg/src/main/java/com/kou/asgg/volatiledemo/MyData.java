package com.kou.asgg.volatiledemo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyData {
    public static void main(String[] args) {
//        while (Thread.activeCount()>2){
//            Thread.yield();
//        }

        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList<Integer> integers = new CopyOnWriteArrayList<>();
    }
}
