package com.kou.base.single;

/**
 * @author JIAJUN KOU
 * 饿汉方式
 */
public class EHan {

    private static final EHan instance=new EHan();//自行创建并用静态变量存储
    private EHan(){}//构造器私有化
    public static EHan getInstance(){
        return instance;
    }


    public static void main(String[] args) {

        EHan eHan= EHan.getInstance();
        System.out.println(eHan);

    }
}
