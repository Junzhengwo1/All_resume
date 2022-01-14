package com.kou.neetystudy.serverDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //得到选择器
        Selector selector = Selector.open();
        socketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到 selector
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if(selector.select(1000)==0){//说明没有事件发生
                System.out.println("服务器等待了一秒，无链接");
                continue;
            }
            //如果返回的不是0
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey selectionKey = keyIterator.next();
                //通过这个key判断是什么channel
                if(selectionKey.isAcceptable()){//说明有新的客户端连接
                    System.out.println("客户端链接成功--------");
                    //给该客户端生成SocketChannel
                    SocketChannel accept = socketChannel.accept();
                    accept.configureBlocking(false);
                    //将当前的Channel 也注册到selector
                    socketChannel.register(selector,SelectionKey.OP_ACCEPT, ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取到该Channel 关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    byte[] array = byteBuffer.array();
                    System.out.println(new String(array));
                }
                //手动移除selectionKey
                keyIterator.remove();

            }
        }
    }


}
