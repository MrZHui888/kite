package com.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NioServer {

    static List<SocketChannel> channelList = new ArrayList<>();

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9100));
        serverSocketChannel.configureBlocking(false);

        System.out.println("服务启动成功");
        while (true) {
            // 非阻塞模式accept 方法不会阻塞,否则会阻塞
            // nio的非阻塞是由操作系统内部实现的,底层调用了Linux的accept函数
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println("链接成功");
                socketChannel.configureBlocking(false);
                // 保存客户端连接再list中
                channelList.add(socketChannel);
            }

            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                // 非阻塞模式read 方法不会阻塞,否则会阻塞
                int len = sc.read(byteBuffer);
                if (len > 0) {
                    System.out.println("接收到消息" + new String(byteBuffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                    System.out.println("断开连接");
                }
            }

        }


    }
}
