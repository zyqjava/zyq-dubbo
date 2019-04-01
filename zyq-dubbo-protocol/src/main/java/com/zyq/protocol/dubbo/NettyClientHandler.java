package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable{

    public ChannelHandlerContext context;

    private Invocation invocation;

    private String result;


    /**
     * 和服务器进行通信
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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

    /**
     * 为什么要加synchronized?
     * 因为wait和notify都需要和synchronized一起使用，否则会报IllegalMonitorStateException
     * 就是说wait和notify都需要和对象锁一起来使用
     * 为什么wait和notify要放在object中？
     * 因为object是所有类的父类，所以为了可以任何类使用放在object
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(this.invocation);
        wait();
        return result;
    }
}
