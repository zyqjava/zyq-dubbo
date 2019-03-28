package com.zyq.consumer;


import com.zyq.provider.api.DemoService;
import com.zyq.provider.proxy.ProxyFactory;

import java.io.IOException;


public class Consumer {

    public static void main(String[] args) throws IOException {
        DemoService demoService = ProxyFactory.getProxy(DemoService.class);
        String result = demoService.demoApi("123");
        System.out.println(result);
    }
}
