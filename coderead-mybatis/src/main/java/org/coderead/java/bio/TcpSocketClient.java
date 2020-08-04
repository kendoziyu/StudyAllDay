package org.coderead.java.bio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpSocketClient {

    public static void main(String[] args) throws IOException {
        // 创建一个还未被连接的套接字
        Socket socket = new Socket();
        System.out.println("客户机的套接字是否已被连接？" + socket.isConnected());
        // 为本地主机创建一个 InetAddress 对象
        InetAddress localHost = InetAddress.getLocalHost();
        // 主动连接服务器
        socket.connect(new InetSocketAddress(localHost, 8080));
        System.out.println("客户机的套接字是否已被连接？" + socket.isConnected());
        OutputStream outStream = socket.getOutputStream();
        InputStream inStream = socket.getInputStream();
        // 用 PrintWriter 装饰输出流
        PrintWriter out = new PrintWriter(outStream, true /*autoFlush*/);
        // 用 Scanner 装饰输入流
        Scanner in = new Scanner(inStream);
        Scanner console = new Scanner(System.in);
        long sendTime;
        long returnTime;
        // 阻塞，等待控制台输入数据
        while (console.hasNextLine()) {
            // 将输入台数据传输给服务器
            out.println(console.nextLine());
            sendTime = System.currentTimeMillis();
            // 阻塞，等待读取服务器回写数据
            while (in.hasNextLine()) {
                returnTime = System.currentTimeMillis();
                System.out.println("共计等待" + (returnTime-sendTime) +" ms 获取服务器数据");
                // 将服务器回传数据输出到控制台
                System.out.println(in.nextLine());
            }
        }
        console.close();
        in.close();
        out.close();
    }
}
