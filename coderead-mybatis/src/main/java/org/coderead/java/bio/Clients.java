package org.coderead.java.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Clients {
    static Socket[] clients = new Socket[5];
    public static void main(String[] args) throws IOException {

        for (int i = 1; i <= clients.length; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clients[index-1] = new Socket("127.0.0.1", 8080);
                        System.out.println("client connection:" + index);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, String.valueOf(i)).start();
        }
    }
}
