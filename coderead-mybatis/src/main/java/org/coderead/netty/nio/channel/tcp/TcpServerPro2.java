package org.coderead.netty.nio.channel.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class TcpServerPro2 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(8081));
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            handle(selector);
        }
    }

    private static void handle(Selector selector) throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 坑：如果不移除这个键，那么事件就会反复被处理
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    read((SocketChannel) key.channel());
                }
            }
        }

    }

    private static void read(SocketChannel socketChannel) throws IOException {
        // 1. 建立缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(6);
        // 2. 读取数据
        while (socketChannel.read(buffer) > 0) {
            // 3. 切换到读模式
            buffer.flip();
            // 4. 申请 JVM 内存
            byte[] data = new byte[buffer.remaining()];
            // 5. 数据从缓冲区读取到 JVM 内存中
            buffer.get(data);
            // 6. 接收到信息
            String message = new String(data);
            System.out.println("接收到来自 " + Thread.currentThread().getName() + " 的信息：" + message);
            if ("exit".equals(message)) {
                // 关闭连接
                socketChannel.close();
                System.out.print("关闭连接：" + socketChannel);
                // 坑：如果不退出循环，下次 read 时会报错
                break;
            }
            //  坑：读取前先清空缓冲区，不然重复读取到相同的内容
            buffer.clear();
        }
    }
}
