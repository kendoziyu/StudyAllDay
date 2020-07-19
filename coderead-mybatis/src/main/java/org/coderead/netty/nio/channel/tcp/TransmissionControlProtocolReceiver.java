package org.coderead.netty.nio.channel.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TransmissionControlProtocolReceiver {

    public static void main(String[] args) throws IOException {
        // 1. 打开管道
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 2. 绑定端口
        channel.bind(new InetSocketAddress(8081));
        // 3. 等待连接
        SocketChannel socketChannel = channel.accept();
        // 4. 建立缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(3);
        // 5. 阻塞管道，等待管道中传来数据，写入到缓冲区中
        socketChannel.read(buffer);
        // 6. 切换到读模式
        buffer.flip();
        // 7. 申请 JVM 内存
        byte[] data = new byte[buffer.remaining()];
        // 8. 数据从缓冲区读取到 JVM 内存中
        buffer.get(data);
        // 9. 接收到信息
        String message = new String(data);
        System.out.println("接收到信息：" + message);
        // 10.关闭连接
        socketChannel.close();
        channel.close();
    }
}
