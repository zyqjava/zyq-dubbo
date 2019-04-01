package com.zyq.protocol.dubbo;

import com.zyq.framework.InvocationHandler;
import com.zyq.framework.Protocol;
import com.zyq.framework.URL;

public class NettyProtocol implements Protocol{

    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public String send(URL url, InvocationHandler invocation) {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.post(url.getHostName(), url.getPort(),invocation);
    }
}
