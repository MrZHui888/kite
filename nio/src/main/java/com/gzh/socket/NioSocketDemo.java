package com.gzh.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioSocketDemo {
    //通道選擇器
    private Selector selector;

    public static void main(String[] args) throws Exception {
        NioSocketDemo demo = new NioSocketDemo();
        demo.initServer(8888);
        demo.listenSelector();
    }

    public void initServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        /**
         * 事件有四種
         */
        System.out.println("服务已启动。。。。");
    }

    /**
     * @param selectionKey
     */
    public void listenSelector() throws Exception {
//輪訓監聽selector
        while (true) {
            // 等待客戶lianjie
            this.selector.select();

            Iterator<?> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                // 處理請求
                handler(key);
            }
        }
    }

    //處理客戶端請求
    private void handler(SelectionKey key) throws Exception {
        if (key.isAcceptable()) {
            //處理客戶端連接請求事件
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = channel.accept();
            socketChannel.configureBlocking(false);
            //接受客戶端法送的信息，需要給通道設置讀的權限
            socketChannel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            //處理讀的事件
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int readData = socketChannel.read(buffer);
            if (readData > 0) {
                String info = new String(buffer.array(), "GBK");
                System.out.println("服务端收到请求" + "-----" + info);
            } else {
                System.out.println("客戶端关闭");
                //客戶端  關閉
                key.cancel();
            }
        }
    }
}
