package com.zyq.framework;

import org.apache.catalina.LifecycleException;

import java.io.IOException;

public interface Protocol {
    void start(Url url) throws InterruptedException, LifecycleException;
    String send(Url url, Invocation invocation) throws InterruptedException, IOException;
}
