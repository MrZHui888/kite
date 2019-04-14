package com.gzh.socket;


import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的socket 編程
 */
public class TraditionalSocketDemo {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端启动");

        while (true) {
            //獲取socket 客戶端套接字
            Socket socket = serverSocket.accept();
            System.out.println("有新的客户端上来了。。。");

            InputStream ism = socket.getInputStream();


            byte[] b = new byte[1024];

            while (true) {
                int data = ism.read(b);
                if (data != -1) {
                    String info = new String(b, 0, data, "GBK");
                    System.out.println(info);
                } else {
                    break;
                }
            }
        }

    }
}
