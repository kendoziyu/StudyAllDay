package org.coderead.netty.nio.channel.tcp;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;

public class SelectorTest {

    Selector selector;
    @Before
    public void init() throws IOException {
        selector = Selector.open();
    }
    @Test
    public void tcpOps() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        printSupport(channel, SelectionKey.OP_ACCEPT, "OP_ACCEPT");
        printSupport(channel, SelectionKey.OP_READ, "OP_READ");
        printSupport(channel, SelectionKey.OP_CONNECT, "OP_CONNECT");
        printSupport(channel, SelectionKey.OP_WRITE, "OP_WRITE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerUnsupported() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Test(expected = IllegalBlockingModeException.class)
    public void registerSupported() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.register(selector, SelectionKey.OP_READ);
    }

    @Test
    public void registerCancelled() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        key.cancel();
        key = channel.register(selector, SelectionKey.OP_READ);
    }

    @Test(expected = ClosedSelectorException.class)
    public void registerWhenSelectorColsed() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_WRITE);
        selector.close();
        key = channel.register(selector, SelectionKey.OP_WRITE);
    }

    @Test(expected = ClosedChannelException.class)
    public void registerWhenChannelClosed() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        channel.close();
        key = channel.register(selector, SelectionKey.OP_WRITE);
    }
    @Test
    public void udpOps() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        printSupport(channel, SelectionKey.OP_ACCEPT, "OP_ACCEPT");
        printSupport(channel, SelectionKey.OP_READ, "OP_READ");
        printSupport(channel, SelectionKey.OP_CONNECT, "OP_CONNECT");
        printSupport(channel, SelectionKey.OP_WRITE, "OP_WRITE");
    }

    @Test
    public void serverSocketChannelTest() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        printSupport(channel, SelectionKey.OP_ACCEPT, "OP_ACCEPT");
        printSupport(channel, SelectionKey.OP_READ, "OP_READ");
        printSupport(channel, SelectionKey.OP_CONNECT, "OP_CONNECT");
        printSupport(channel, SelectionKey.OP_WRITE, "OP_WRITE");
    }

    @Test
    public void socketChannelTest() throws IOException {
        SocketChannel channel = SocketChannel.open();
        printSupport(channel, SelectionKey.OP_ACCEPT, "OP_ACCEPT");
        printSupport(channel, SelectionKey.OP_READ, "OP_READ");
        printSupport(channel, SelectionKey.OP_CONNECT, "OP_CONNECT");
        printSupport(channel, SelectionKey.OP_WRITE, "OP_WRITE");
    }

    private void printSupport(SelectableChannel channel, int op, String opName) {
        System.out.println(String.format("%s %s支持", opName, (channel.validOps() & op) > 0 ? "" : "不"));
    }
}
