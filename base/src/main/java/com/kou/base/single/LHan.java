package com.kou.base.single;

/**
 * @author JIAJUN KOU
 *
 * 单例模式
 * *   1.单例类只能有一个实例
 * *   2.单例类必须自己创建自己的唯一实例
 * *   3.单例类必须给其他对象提供这一实例
 * *
 * * 实现步骤：
 * *   1.构造器私有化
 * *   2.提供静态方法返回实例对象
 *
 *
 *
 * 懒汉式 单例模式：
 */
public class LHan {
    /**
     * 初始化一个静态变量；
     */
    private static LHan instance;

    /**
     * 让构造函数为 private，这样该类就不会被实例化
     */
    private LHan(){
    }

    /**
     * 获取唯一可用的对象 静态方法
     * @return
     */
    public static LHan getInstance(){
        if(instance==null){
            instance=new LHan();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("单例模式运行了!");
    }

    public static void main(String[] args) {

        LHan lazySingle= LHan.getInstance();
        lazySingle.showMessage();

    }
}
