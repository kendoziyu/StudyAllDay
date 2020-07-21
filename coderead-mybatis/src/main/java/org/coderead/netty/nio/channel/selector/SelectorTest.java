package org.coderead.netty.nio.channel.selector;

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

    @Test(expected = IllegalArgumentException.class)
    public void IllegalArgumentException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Test(expected = IllegalBlockingModeException.class)
    public void IllegalBlockingModeException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.register(selector, SelectionKey.OP_READ);
    }

    @Test(expected = ClosedChannelException.class)
    public void ClosedChannelException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.close();
        channel.register(selector, SelectionKey.OP_WRITE);
    }

    @Test(expected = ClosedSelectorException.class)
    public void ClosedSelectorException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        selector.close();
        channel.register(selector, SelectionKey.OP_READ);
    }

    @Test(expected = CancelledKeyException.class)
    public void CancelledKeyException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        key.cancel();
        Selector selector = Selector.open();
        selector.selectNow();
        channel.register(selector, SelectionKey.OP_WRITE);
    }
    @Test
    public void printDatagramChannelValidOps() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        printSupport(channel, SelectionKey.OP_ACCEPT, "OP_ACCEPT");
        printSupport(channel, SelectionKey.OP_READ, "OP_READ");
        printSupport(channel, SelectionKey.OP_CONNECT, "OP_CONNECT");
        printSupport(channel, SelectionKey.OP_WRITE, "OP_WRITE");
    }

    @Test
    public void printServerSocketChannelValidOps() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        printSupport(channel, SelectionKey.OP_ACCEPT, "OP_ACCEPT");
        printSupport(channel, SelectionKey.OP_READ, "OP_READ");
        printSupport(channel, SelectionKey.OP_CONNECT, "OP_CONNECT");
        printSupport(channel, SelectionKey.OP_WRITE, "OP_WRITE");
    }

    @Test
    public void printSocketChannelValidOps() throws IOException {
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
