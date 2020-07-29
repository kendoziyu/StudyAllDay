package org.coderead.netty.nio.oldbio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpSender {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
        System.out.println("客户端已连接到服务器，输入任意字符关闭连接。");
        System.in.read();
        socket.close();
        System.out.println("客户端主动关闭连接");
    }


}
