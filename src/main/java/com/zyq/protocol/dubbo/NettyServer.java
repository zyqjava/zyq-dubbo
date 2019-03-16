package com.zyq.protocol.dubbo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

public class NettyServer {
    public void start(String hostName, Integer port) throws InterruptedException {
        final ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("docoder",new ObjectDecoder(
                                ClassResolvers.weakCachingResolver(this.getClass().getClassLoader()
                        )));
                        pipeline.addLast(new NettyServerHandler());

                    }
                }).option(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        System.out.println("server:start to connect...");
        channelFuture.channel().closeFuture();
        eventLoopGroup.shutdownGracefully();

    }
}
