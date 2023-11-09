package com.kou.nettystudy.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class BasicBufferDemo {

    public static void main(String[] args) {
        //intBufferDome();
        //fileBufferDome();
        fileBufferWritDome();
    }


    private static void fileBufferDome() {
        try(FileChannel channel = new FileInputStream("1.txt").getChannel()){
            ByteBuffer allocate = ByteBuffer.allocate(10);
            // 读文件 写入到 allocate
            while (channel.read(allocate) != -1) {
                allocate.flip();
                while (allocate.hasRemaining()) {
                    byte b = allocate.get();
                    System.out.println((char) b);
                }
                allocate.clear();
            }
        }catch (Exception ignored){
        }

    }


    private static void fileBufferWritDome() {
        try(FileChannel channel = new RandomAccessFile("2.txt","rw").getChannel()){
            ByteBuffer hello = StandardCharsets.UTF_8.encode("hello kou");
            long write = channel.write(hello);
            System.out.println(write);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void intBufferDome() {
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
