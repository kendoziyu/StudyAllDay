package org.coderead.netty.nio.channel.selector;


import org.junit.Test;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class SelectTest {

    @Test
    public void register() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        Selector sel = Selector.open();
        channel.register(sel, SelectionKey.OP_READ);
        channel.register(sel, SelectionKey.OP_WRITE);
    }
}
