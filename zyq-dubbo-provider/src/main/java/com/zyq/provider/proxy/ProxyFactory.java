package com.zyq.provider.proxy;


import com.zyq.framework.InvocationHandler;
import com.zyq.framework.Protocol;
import com.zyq.framework.ProtocolFactory;
import com.zyq.framework.URL;
import com.zyq.provider.api.DemoService;
import com.zyq.register.Register;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {

    public static <T> T getProxy(final Class interfaceClass) {
        Class[] clazz = new Class[]{interfaceClass};

        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), clazz, new java.lang.reflect.InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Protocol protocol = ProtocolFactory.getProtocol();
                // 调用哪个方法
                InvocationHandler invocation = new InvocationHandler(DemoService.class.getName(),
                        method.getName(), args, method.getParameterTypes());
                // 模拟负载均衡，随机获取服务器
                URL url = Register.random(interfaceClass.getName());
                return protocol.send(url, invocation);
            }
        });
    }
}
