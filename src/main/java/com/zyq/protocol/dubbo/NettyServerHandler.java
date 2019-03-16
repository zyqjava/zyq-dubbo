package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import com.zyq.framework.Url;
import com.zyq.register.Register;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().localAddress();
        Class serviceImpl = Register.get(new Url(insocket.getHostName(), insocket.getPort()),invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamsTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());
        // 写回给调用端
        ctx.writeAndFlush("Netty----" + result);
    }

}
