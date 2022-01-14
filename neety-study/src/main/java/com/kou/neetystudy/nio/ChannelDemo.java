package com.kou.neetystudy.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ChannelDemo {
    public static void main(String[] args) throws Exception {
        //writeFile();
        //readFile();
        //fileCopyByOneBuffer();
        //copyImg();
        //mappedBufferTest();
        scatteringAndGatheringTest();
    }

    public static void writeFile() throws IOException {
        String str = "hello FileChannel";
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\jiakou\\Desktop\\hello.txt");

        //获取对应的FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        //byteBuffer反转
        byteBuffer.flip();
        //把buffer数据写入到Channel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }



    public static void readFile() throws Exception {
        File file = new File("C:\\Users\\jiakou\\Desktop\\hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(byteBuffer);
        //将字节转成字符串
        byte[] array = byteBuffer.array();
        System.out.println(new String(array));
        fileInputStream.close();
    }


    public static void fileCopyByOneBuffer() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel1 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true){
            //关键操作
            byteBuffer.clear();
            int read = fileChannel1.read(byteBuffer);
            if(read == -1){
                break;
            }
            fileChannel2.write((ByteBuffer) byteBuffer.flip());
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 使用buffer来实现图片copy
     */
    public static void copyImg() throws IOException {
        File file = new File("C:\\Users\\jiakou\\Desktop\\1.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\jiakou\\Desktop\\2. png ");
        FileChannel fileChannel = fileInputStream.getChannel();
        FileChannel channel = fileOutputStream.getChannel();

        channel.transferFrom(fileChannel,0,fileChannel.size());
        fileChannel.close();
        channel.close();
        fileInputStream.close();
        fileInputStream.close();


    }


    /**
     * MappedByteBuffer
     * 可以让文件直接在堆外内存操作
     */
    public static void mappedBufferTest() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = rw.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'H');
        rw.close();


    }


    /**
     * Scattering:将数据写入到buffer，可以采用 buffer数组，依次写入数据【分散】
     * Gathering: 从buffer读取数据时，可以采用buffer数组，依次
     */
    public static void scatteringAndGatheringTest() throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        open.socket().bind(inetSocketAddress);

        //服务器端创建一个buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);
        //等待客户端链接
        SocketChannel socketChannel = open.accept();
        //循环读取
        int messageLen=8;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLen) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println(byteRead);
                Arrays.stream(byteBuffers).map(o->"position="+o.position()+"limit="+o.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            //将数据显示到客户端
            long byteWrite=0;
            while (byteWrite<messageLen){
                long write = socketChannel.write(byteBuffers);
                byteWrite+=write;
            }
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead="+byteRead+";byteWrite="+byteWrite);
        }

    }



}
