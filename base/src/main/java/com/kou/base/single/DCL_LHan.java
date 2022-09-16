package com.kou.base.single;

/**
 * @author JIAJUN KOU
 * 双重检测锁模式的
 * DCL_模式的单例模式
 */
public class DCL_LHan {

    /**
     * 初始化一个静态变量；
     */
    private volatile static DCL_LHan instance;

    /**
     * 让构造函数为 private，这样该类就不会被实例化
     */
    private DCL_LHan(){
    }

    /**
     * 获取唯一可用的对象 静态方法
     * 双重检测锁模式的 懒汉式
     * @return
     */
    public static DCL_LHan getInstance(){
        if(instance==null){
            //上一个锁
            synchronized (DCL_LHan.class){
                if(instance==null){
                    instance=new DCL_LHan();
                }
            }
        }
        return instance;
    }
}
