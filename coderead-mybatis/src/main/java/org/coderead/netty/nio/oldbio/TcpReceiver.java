package org.coderead.netty.nio.oldbio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpReceiver {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            System.out.println("服务器等待连接...");
            Socket socket = serverSocket.accept();
            System.out.println("服务器接收到连接，输入任意字符结束连接");
            System.in.read();
            socket.close();
            System.out.println("服务器主动断开连接");
            System.in.read();
        }
    }
}
