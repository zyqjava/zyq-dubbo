package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import com.zyq.framework.Url;
import com.zyq.register.Register;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{

    private ChannelHandlerContext context;

    private Invocation invocation;

    private String result;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到数据" + msg);
        result = msg.toString();
        notify();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    public  Object call() throws InterruptedException {
        context.writeAndFlush(this.invocation);
        wait();
        return result;
    }
}
