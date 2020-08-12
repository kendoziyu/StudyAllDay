package org.coderead.java.bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TimeoutClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(3000);
        socket.connect(new InetSocketAddress("127.0.0.1", 8080));
        long startTime = 0;
        try {
            InputStream inStream = socket.getInputStream();
            startTime = System.currentTimeMillis();
            while (inStream.read() != 0) {
                System.out.println("接收到了信息");
            }
        } catch (IOException e) {
            long timeout = System.currentTimeMillis() - startTime;
            System.out.println(timeout);
            e.printStackTrace();
        }
    }
}
