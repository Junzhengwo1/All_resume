package com.kou.base.study.design.single;

/**
 * 静态类部类单例
 */
public class StaticSingle {

    // jvm 保证线程安全
    private static class InnerClassHolder{
        private static StaticSingle instance = new StaticSingle();
    }

    private StaticSingle(){
        if (null != InnerClassHolder.instance){
            throw new RuntimeException("单例不允许多个实例");
        }
    }

    public static StaticSingle getInstance(){
        return InnerClassHolder.instance;
    }
}
