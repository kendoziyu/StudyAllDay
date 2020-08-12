package org.coderead.netty.nio.channel.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelHandler implements Runnable {
    private SocketChannel channel;

    public SocketChannelHandler(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            // 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                //  坑：读取前先清空缓冲区，不然重复读取到相同的内容
                buffer.clear();
                // 读取数据
                channel.read(buffer);
                System.out.print("Reading ");
                // 切换到读模式
                buffer.flip();
                // 申请 JVM 字节数组
                byte[] data = new byte[buffer.remaining()];
                // 从缓冲区读取到数组中
                buffer.get(data);
                String message = new String(data);
                if (message.equals("Exit")) {
                    channel.close();
                    // 坑：如果不退出循环，下次 read 时会报错
                    break;
                } else {
                    System.out.println(new String(data));
                }
            }
        } catch (IOException e) {
            System.out.println("SocketChannelHandler run:" + e);
        }
    }
}
