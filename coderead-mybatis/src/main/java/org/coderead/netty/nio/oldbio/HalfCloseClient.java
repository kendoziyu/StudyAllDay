package org.coderead.netty.nio.oldbio;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class HalfCloseClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8080));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());
        out.print("nice to meet you");
        out.flush();
        socket.shutdownOutput();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            System.out.println(line);
        }
        socket.close();

    }
}
