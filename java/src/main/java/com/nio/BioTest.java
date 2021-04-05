package com.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTest {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9000);


        while (true) {
            System.out.println("等待链接。。。。。");
            Socket clientSocket = serverSocket.accept();
            System.out.println("有客户端连接了。。。。。");
            handler(clientSocket);
        }


    }

    private static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备接受数据。。。。。");
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("read 完毕。。。。。");
        if (read != -1) {
            System.out.println("接受到客户端的数据。。。。。" + new String(bytes, 0, read));
        }

    }
}
