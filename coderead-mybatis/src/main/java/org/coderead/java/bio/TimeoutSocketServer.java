package org.coderead.java.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TimeoutSocketServer {

    public static void main(String[] args) throws IOException {
        long readTime = 0L;
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            Socket socket = serverSocket.accept();
                System.out.println("客户端连接上了");
                InputStream inStream = socket.getInputStream();
                byte[] bytes = new byte[1024];

                while (true) {
                    try {
                        if (inStream.read(bytes) != 0) {
                            System.out.println(Arrays.toString(bytes));
                        }
                    } catch (IOException e) {
                        long timeout = System.currentTimeMillis() - readTime;
                        System.out.println(timeout);
                        e.printStackTrace();
                    }
                }
        }
    }
}
