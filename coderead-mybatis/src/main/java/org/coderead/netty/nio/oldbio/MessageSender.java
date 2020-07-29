package org.coderead.netty.nio.oldbio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MessageSender implements Runnable {

    private Socket socket;

    public MessageSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String message = scanner.nextLine();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                scanner.close();
                break;
            }
        }
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
