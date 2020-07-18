package org.coderead.netty.nio.channel.udp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelServer {

    public static void main(String[] args) throws IOException {
        // 1.打开管道
        DatagramChannel channel = DatagramChannel.open();
        // 2.绑定端口
        channel.bind(new InetSocketAddress(8080));
        // 3.创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(3);
        while (true) {
            // 坑2：需要清理buffer,不然无法读出新的数据
            buffer.clear();
            // 4.阻塞管道，直到接收到数据
            channel.receive(buffer);
            // 5.切换到读模式
            buffer.flip();
            // 6.申请JVM内存空间来存储数据
            byte[] data = new byte[buffer.remaining()];
            // 7.把管道中的数据读取到内存中
            buffer.get(data);
            String str = new String(data);
            System.out.println("接收到信息：" + str);
        }

    }
}
