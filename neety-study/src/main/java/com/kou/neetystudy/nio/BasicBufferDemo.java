package com.kou.neetystudy.nio;

import java.nio.IntBuffer;

public class BasicBufferDemo {

    public static void main(String[] args) {

        //Buffer 的举例说明
        IntBuffer allocate = IntBuffer.allocate(5);
        //存数据
        allocate.put(10);
        allocate.put(12);
        allocate.put(13);
        allocate.put(14);
        allocate.put(15);
        //取数据-这个方法是buffer的读写切换
        allocate.flip();
        while (allocate.hasRemaining()){
            System.out.println(allocate.get());
        }


    }

}
