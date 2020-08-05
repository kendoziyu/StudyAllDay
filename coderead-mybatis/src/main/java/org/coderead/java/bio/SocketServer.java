package org.coderead.java.bio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080), 1);
        int acceptCount = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
            System.out.println(remote.getPort());
            ++acceptCount;
            System.out.println("当前客户端连接数：" + acceptCount);
            Thread.sleep(2000);
        }
    }
}
