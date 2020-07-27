package org.coderead.netty.nio.channel.selector;


import org.junit.Test;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class SelectTest {

    @Test
    public void register() throws IOException {
        DatagramChannel channel1 = DatagramChannel.open();
        DatagramChannel channel2 = DatagramChannel.open();
        channel1.configureBlocking(false);
        channel2.configureBlocking(false);
        Selector sel1 = Selector.open();
        Selector sel2 = Selector.open();
        channel1.register(sel1, SelectionKey.OP_READ);
        channel1.register(sel2, SelectionKey.OP_WRITE);
        channel2.register(sel1, SelectionKey.OP_READ);
        System.out.println();
    }
}
