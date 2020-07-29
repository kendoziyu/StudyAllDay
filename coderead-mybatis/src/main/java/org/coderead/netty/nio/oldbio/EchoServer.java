package org.coderead.netty.nio.oldbio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器等待连接...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("服务端正在接收信息...");
        InputStream inStream = clientSocket.getInputStream();
        OutputStream outStream = clientSocket.getOutputStream();
        Scanner in = new Scanner(inStream);
        PrintWriter out = new PrintWriter(outStream, true /*autoFlush*/);
        out.println("Hello! Enter BYE to exit");
        System.out.println("服务端正在读取信息...");
        boolean done = false;
        while (!done && in.hasNextLine()) {
            String line = in.nextLine();
            // 回声
            String echo = "Echo:" + line;
            out.println(echo);
            if (line.trim().equals("BYE")) done = true;
        }
        System.out.println("服务器关闭连接...");
        inStream.close();
        serverSocket.close();
    }
}
