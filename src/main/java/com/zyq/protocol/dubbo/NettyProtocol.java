package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import com.zyq.framework.Protocol;
import com.zyq.framework.Url;

public class NettyProtocol implements Protocol{

    @Override
    public void start(Url url) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public String send(Url url, Invocation invocation) throws InterruptedException {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.post(url.getHostName(), url.getPort(),invocation);
    }
}
