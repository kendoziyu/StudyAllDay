package org.coderead.java.bio;

import org.apache.log4j.net.SocketServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpSocketServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        // 绑定端口
        serverSocket.bind(new InetSocketAddress(8080));
        System.out.println("服务器等待连接...  127.0.0.1:8080");
        // 阻塞，等待客户机连接
        Socket clientSocket = serverSocket.accept();
        InetSocketAddress clientAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        System.out.println("接收到客户端连接" + clientAddress.getHostString() + ":" + clientAddress.getPort());

        OutputStream out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();
        byte[] readBytes = new byte[1024];
        // 阻塞，等待读取客户机数据
        while (in.read(readBytes) != -1) {
            System.out.println(new String(readBytes));
            // 阻塞，代表服务器业务处理
            Thread.sleep(200);
            // 服务器向客户端回写数据
            out.write(String.valueOf(System.currentTimeMillis()).getBytes());
            // 结尾回写回车+换行
            out.write("\r\n".getBytes());
            // 手动刷新输出流
            out.flush();
        }
        // 关闭连接
        clientSocket.close();
        serverSocket.close();
    }
}
