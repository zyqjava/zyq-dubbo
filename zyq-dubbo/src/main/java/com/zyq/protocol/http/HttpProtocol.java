package com.zyq.protocol.http;

import com.zyq.framework.Invocation;
import com.zyq.framework.Protocol;
import com.zyq.framework.Url;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;

public class HttpProtocol implements Protocol {
    @Override
    public void start(Url url) throws InterruptedException, LifecycleException {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public String send(Url url, Invocation invocation) throws InterruptedException, IOException {
        HttpClient httpClient = new HttpClient();
        return httpClient.post(url.getHostName(), url.getPort(), invocation);
    }
}
