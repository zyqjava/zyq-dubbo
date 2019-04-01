package com.zyq.provider;

import com.zyq.framework.Protocol;
import com.zyq.framework.ProtocolFactory;
import com.zyq.framework.URL;
import com.zyq.provider.api.DemoService;
import com.zyq.provider.impl.DemoServiceImpl;
import com.zyq.register.Register;
import org.apache.catalina.LifecycleException;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws LifecycleException, IOException, InterruptedException {
        //注册服务
        URL url = new URL("127.0.0.1",8080);
        Register.register(url, DemoService.class.getName(), DemoServiceImpl.class);

        //启动Tomcat
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);
    }
}
