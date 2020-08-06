package org.coderead.netty.nio.channel.tcp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpServer {

    public static void main(String[] args) {
        try {
            // 1. 打开管道
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 2. 绑定端口
            channel.bind(new InetSocketAddress(8081));
            while (true) {
                // 3. 等待连接
                SocketChannel socketChannel = channel.accept();
                System.out.println("建立一个新的连接");
                // BIO 模型，交给线程去处理
                new Thread(new SocketChannelHandler(socketChannel)).start();
            }
        } catch (IOException ex) {
            System.out.println("TcpServer " + ex);
        }
    }
}
