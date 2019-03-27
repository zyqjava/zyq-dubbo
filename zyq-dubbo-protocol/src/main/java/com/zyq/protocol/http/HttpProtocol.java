package com.zyq.protocol.http;

import com.zyq.framework.Invocation;
import com.zyq.framework.Protocol;
import com.zyq.framework.URL;
import org.apache.catalina.LifecycleException;

import java.io.IOException;

public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) throws InterruptedException, LifecycleException {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) throws InterruptedException, IOException {
        HttpClient httpClient = new HttpClient();
        return httpClient.post(url.getHostName(), url.getPort(), invocation);
    }
}
