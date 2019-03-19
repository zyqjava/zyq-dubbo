package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import com.zyq.framework.URL;
import com.zyq.register.Register;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private Channel channel;

    public NettyClientHandler nettyClientHandler = new NettyClientHandler();

    public void start(String hostName, Integer port, Invocation invocation) throws InterruptedException {
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
        channel = channelFuture.channel();
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

    public String post(String hostName, Integer port, Invocation invocation) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        start(hostName, port, invocation);
        channel.writeAndFlush(invocation);

       /* InetSocketAddress insocket = (InetSocketAddress) nettyClientHandler.context.channel().localAddress();*/
        Class serviceImpl = Register.get(new URL(hostName, port),invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamsTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());
        return (String) result;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
