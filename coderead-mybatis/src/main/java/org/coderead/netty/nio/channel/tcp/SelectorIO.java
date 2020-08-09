package org.coderead.netty.nio.channel.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorIO implements Runnable {

    private Selector selector;

    public SelectorIO(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int count = selector.select();
                if (count == 0) continue;
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        handleAccept(selectionKey);
                    } else if (selectionKey.isReadable()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    handleRead(selectionKey);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } else if (selectionKey.isWritable()) {
                        handleWrite(selectionKey);
                    }
                    // 坑，如果不移除，同一个事件会重复处理
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            System.out.println("SelectorIO run error." + e);
            e.printStackTrace();
        }
    }

    private void handleWrite(SelectionKey selectionKey) throws IOException {
        System.out.println("Writing...");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.write(ByteBuffer.wrap("heartbeat\r\n".getBytes()));
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey selectionKey) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        System.out.print("Reading ");
        System.out.println(new String(data));
        SelectionKey write = socketChannel.register(selector, SelectionKey.OP_WRITE);
//        SelectionKey write = selectionKey.interestOps(SelectionKey.OP_WRITE);
        System.out.println(write.equals(selectionKey));
    }

    private void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = server.accept();
        // 坑：虽然设置好了 ServerSocketChannel 是非阻塞的，但是还是需要设置 SocketChannel 也是非阻塞的
        socketChannel.configureBlocking(false);
        SelectionKey read = socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
        System.out.println(read.equals(selectionKey));
    }
}
