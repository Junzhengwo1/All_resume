package juc;

public class Test {
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());
        //看一下线程的状态（线程其实有六个状态，有一个枚举类）

        /**一、
         * wait和sleep的区别？
         * 1、来自不同类wait来自于Object;sleep来自工具类
         * 2、关于锁的释放 wait会释放锁；
         * 3、使用的范围是不同的wait(必须在同步代码块中) sleep任意地方都可以睡
         * 4、sleep必须捕获异常
         */

        /**
         * 二、lock锁（重点）--（锁本质会锁两个东西，一个是对象，一个是class）
         * 1、同比代码块的syn关键字（本质就是排队）
         * 2、加锁解锁，的结构，有点像try_catch
         *          --new一个lock
         *          --加锁，try_catch中写业务代码
         *          --解锁
         *          --lock.tryLock()尝试获取锁
         * 3、syn与lock的区别？
         *  --lock可以判断锁的状态
         *  --syn自动解锁。lock必须手动释放锁，否则会死锁
         *  --
         * 4、syn适合锁少量代码，lock适合大量的
         */


        /**
         * 三、锁是什么，如何判断锁是谁
         * 1、线程之间的通信
         * 2、存在多个线程，怎么保证安全
         *      --if判断会出现虚假唤醒
         *      --得用while来判断
         */



    }
}
