package org.coderead.netty.nio.channel.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * 描述: 接收 UDP 报文 <br>
 *
 * @author: kendo ziyu <br>
 * @date: 2020/7/15 0015 <br>
 */
public class UserDataProtocolReceiver {

    public static void main(String[] args) throws IOException {
        // 1. 打开管道
        DatagramChannel channel = DatagramChannel.open();
        // 2. 绑定端口
        channel.bind(new InetSocketAddress(8080));
        // 3. 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4. 接收消息，如果没有接收到消息，会阻塞等待
        channel.receive(buffer);
        // 5. 准备读取数据
        buffer.flip();
        // 6. 分配 JVM 内存空间
        byte[] data = new byte[buffer.remaining()];
        // 7. 拷贝缓冲区数据到数组中
        buffer.get(data);
        System.out.println("接收到消息：" + new String(data));
    }
}
