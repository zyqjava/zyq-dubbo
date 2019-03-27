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
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServer {
    public void start(String hostName, Integer port) throws InterruptedException {

        final ServerBootstrap bootstrap = new ServerBootstrap();

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //eventLoopGroup就是parentGroup，是负责处理TCP/IP连接的
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //workerGroup就是childGroup,是负责处理Channel(通道)的I/O事件


        bootstrap.group(eventLoopGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("docoder",new ObjectDecoder(
                                ClassResolvers.weakCachingResolver(this.getClass().getClassLoader()
                        )));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("handler",new NettyServerHandler()); //添加处理的类
                    }
                }).option(ChannelOption.SO_KEEPALIVE,true);
        ChannelFuture channelFuture = bootstrap.bind(hostName ,port).sync();
        if (channelFuture.isSuccess()) {
            System.out.println("server:start to connect...");
        } else {
            eventLoopGroup.shutdownGracefully();
        }


        channelFuture.channel().closeFuture().sync();
    }
}
