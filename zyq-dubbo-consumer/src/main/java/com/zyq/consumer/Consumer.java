package com.zyq.consumer;


import com.zyq.framework.ProxyFactory;
import com.zyq.provider.api.DemoService;

import java.io.IOException;


public class Consumer {

    public static void main(String[] args) throws IOException {
        DemoService demoService = ProxyFactory.getProxy(DemoService.class);
        String result = demoService.demoApi("123");
        System.out.println(result);
    }
}
