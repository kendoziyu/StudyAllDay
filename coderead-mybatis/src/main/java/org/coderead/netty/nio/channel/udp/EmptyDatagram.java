package org.coderead.netty.nio.channel.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class EmptyDatagram {

    public static void main(String[] args) throws IOException {
        int port = 10001;
        // 1. 创建UDP套接字连接
        DatagramSocket socket = new DatagramSocket(port);
        // 2. 创建主体数据(空数据)
        byte[] data = new byte[0];
        // 3. 创建数据报（指定目标 IP 和 端口地址）
        DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 8080);
        // 4. 发送数据报
        socket.send(packet);
        System.out.println("消息已发送");
        // 5. 关闭连接
        socket.close();
    }
}
