package org.coderead.netty.nio.channel.tcp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpServerPro {

    public static void main(String[] args) throws IOException {
        // 1. 打开管道
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 2. 绑定端口
        channel.bind(new InetSocketAddress(8081));
        while (true) {
            // 3. 等待连接
            SocketChannel socketChannel = channel.accept();
            System.out.println("建立一个新的连接:" + socketChannel);
            new Thread(() -> {
                try {
                    handle(socketChannel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, socketChannel.getRemoteAddress().toString()).start();
        }
    }

    private static void handle(SocketChannel socketChannel) throws IOException {
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
