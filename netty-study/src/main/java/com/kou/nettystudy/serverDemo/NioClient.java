package com.kou.nettystudy.serverDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
    public static void main(String[] args) throws IOException{

        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress localhost = new InetSocketAddress("localhost", 6666);
        if(!socketChannel.connect(localhost)){
            while (!socketChannel.finishConnect()){
                System.out.println("客户端因为链接需要时间，不会阻塞，可以做其他工作");
            }
        }
        String string = "hello king";
        ByteBuffer buffer = ByteBuffer.wrap(string.getBytes());
        //把buffer 数据写入到channel中
        socketChannel.write(buffer);
        System.in.read();
    }
}
