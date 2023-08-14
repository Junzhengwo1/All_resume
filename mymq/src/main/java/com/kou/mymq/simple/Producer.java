package com.kou.mymq.simple;


import java.util.HashSet;

/**
 * 生产者
 */
public class Producer {

    private Integer age;

    public static void main(String[] args) {
        HashSet<Object> objects = new HashSet<>();
        objects.add(null);
        objects.add(1);
        objects.forEach(System.out::println);

        Thread thread = new Thread();
    }

}
