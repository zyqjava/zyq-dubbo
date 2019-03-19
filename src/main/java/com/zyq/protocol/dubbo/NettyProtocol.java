package com.zyq.protocol.dubbo;

import com.zyq.framework.Invocation;
import com.zyq.framework.Protocol;
import com.zyq.framework.URL;

import java.lang.reflect.InvocationTargetException;

public class NettyProtocol implements Protocol{

    @Override
    public void start(URL url) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) throws InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.post(url.getHostName(), url.getPort(),invocation);
    }
}
