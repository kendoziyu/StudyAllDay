package org.coderead.netty.nio.channel.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.junit.Test;

/**
 * 描述:  <br>
 *
 * @author: kendo ziyu <br>
 * @date: 2020/7/29 0029 <br>
 */
public class TcpTest {

    public void server() {
        NioServerSocketChannel channel = new NioServerSocketChannel();
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        boss.register(channel);
        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println(msg);
            }
        });

    }
}
