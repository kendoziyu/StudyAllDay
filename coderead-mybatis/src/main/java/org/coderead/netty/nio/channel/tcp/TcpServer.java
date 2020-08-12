package org.coderead.netty.nio.channel.tcp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class TcpServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 坑：如果不设置非阻塞，还是阻塞式模型
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(8081));

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(new SelectorIO(selector), "Selector-IO").start();
            System.in.read(); // 阻塞主线程
        } catch (IOException ex) {
            System.out.println("TcpServer " + ex);
        }
    }
}
