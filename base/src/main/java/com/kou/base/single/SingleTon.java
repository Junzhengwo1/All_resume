package com.kou.base.single;

/**
 *  静态内部类单例模式
 */
public class SingleTon {

    private SingleTon(){}

    private static class SingleTonHoer{
        private static final SingleTon INSTANCE = new SingleTon();
    }

    public static SingleTon getInstance(){
        return SingleTonHoer.INSTANCE;
    }

}
