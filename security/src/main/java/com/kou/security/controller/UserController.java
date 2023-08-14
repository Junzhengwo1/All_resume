package com.kou.security.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public static void main(String[] args) {
        ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
        ThreadLocal<Object> objectThreadLocal2 = new ThreadLocal<>();
        objectThreadLocal.set("king");
        objectThreadLocal2.set("niaho");
        System.out.println(objectThreadLocal.get());
        System.out.println(objectThreadLocal2.get());
    }


}
