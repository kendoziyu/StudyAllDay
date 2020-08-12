package org.coderead.java.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 描述:  <br>
 *
 * @author: kendo ziyu <br>
 * @date: 2020/8/5 0005 <br>
 */
public class Server {

    public static void main(String[] args) throws IOException {
        long readTime = 0L;
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        System.out.println("客户端连接上了");
        System.in.read();
    }
}
