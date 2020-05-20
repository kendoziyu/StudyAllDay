package tcp;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOSocketService {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(10086));
        //设置serverSocketChannel为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        //将serverSocketChannel注册到selector，并设置对连接事件感兴趣
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //非阻塞式查询selector是否有准备好的读、写、连接事件
            int select = selector.selectNow();
            //如果返回大于0，则有准备好的读、写、连接事件，然后处理
            if (select > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    //如果事件为准备好可读
                    if (selectionKey.isReadable()) {
                        System.out.println("readable");
                        //处理读事件的handler
                        new ReadHandler().readHandler(selectionKey);
                    }
                    //如果事件为准备好连接
                    else if (selectionKey.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                        //同意连接，返回一个SocketChannel
                        SocketChannel accept = ssc.accept();
                        System.out.println("有连接进来！" + accept.getRemoteAddress());
                        //将SocketChannel也设置为非阻塞模式
                        accept.configureBlocking(false);
                        //注册到selector，并设置对读感兴趣
                        accept.register(selector, SelectionKey.OP_READ);
                    }
                    iterator.remove();
                }
            }
        }
    }
}
