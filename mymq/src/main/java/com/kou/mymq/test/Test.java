package com.kou.mymq.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        byte[] kings = md5("king");
        System.out.println(kings.length);
        System.out.println(B2Hex(kings));
        System.out.println(B2Hex(kings).length());
    }


    public static byte[] md5 (String a) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("md5").digest(a.getBytes());
    }


    public static String B2Hex(byte[] bytes){
        return new BigInteger(1,bytes).toString(16).toUpperCase();
    }





}
