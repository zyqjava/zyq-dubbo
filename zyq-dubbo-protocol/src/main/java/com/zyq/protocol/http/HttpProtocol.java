package com.zyq.protocol.http;

import com.zyq.framework.InvocationHandler;
import com.zyq.framework.Protocol;
import com.zyq.framework.URL;

import java.io.IOException;

public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(), url.getPort());
    }

    @Override
    public String send(URL url, InvocationHandler invocation) throws IOException {
        HttpClient httpClient = new HttpClient();
        return httpClient.post(url.getHostName(), url.getPort(), invocation);
    }
}
