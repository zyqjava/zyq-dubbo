package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NettyClient {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public  NettyClientHandler nettyClientHandler = null;

    public  void start(String hostName, Integer port) throws InterruptedException {
        nettyClientHandler = new NettyClientHandler();

        final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                System.out.println("netty connecting");
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("docoder",new ObjectDecoder(
                        ClassResolvers.weakCachingResolver(this.getClass().getClassLoader()
                        )));
                pipeline.addLast("encoder", new ObjectEncoder());
                pipeline.addLast(nettyClientHandler);
            }
        });
        //连接服务端
        ChannelFuture channelFuture = bootstrap.connect(hostName, port).sync();
        channelFuture.addListener((new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("connect success");
                } else {
                    System.out.println("connect fail");
                    channelFuture.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();  //关闭线程组
                }
            }
        }));

        /*channelFuture.channel().closeFuture().sync();
        eventLoopGroup.shutdownGracefully();*/
    }

    public String post(String hostName, Integer port, Invocation invocation) {
        if (nettyClientHandler == null) {
            try {
                start(hostName, port);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nettyClientHandler.setInvocation(invocation);
        try {
            try {
                Future future = executorService.submit(nettyClientHandler);
                return (String) future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /* InetSocketAddress insocket = (InetSocketAddress) nettyClientHandler.context.channel().localAddress();*//*
        Class serviceImpl = Register.get(new URL(hostName, port),invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamsTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());*/
        System.out.println(123);
        return null;
    }

}
