package org.coderead.netty.nio.channel.udp;


import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class DatagramChannelClient {

    public static void main(String[] args) throws IOException {
        // 1.接收输入
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!(input = scanner.next()).equals("eof")) {
            sendData(input);
        }
    }

    private static void sendData(String message) throws IOException {
        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(10001));
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 8080);
        // 发送 UDP 数据报
        socket.send(packet);
        // 坑1：重复发送的时候，必须关闭socket
        socket.close();
    }
}
