package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    public ChannelHandlerContext context;

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

    public  Object call() throws InterruptedException {
        context.writeAndFlush(this.invocation);
        wait();
        return result;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

}
