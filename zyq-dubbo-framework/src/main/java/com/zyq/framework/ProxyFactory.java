package com.zyq.framework;

import com.zyq.provider.api.DemoService;
import com.zyq.register.Register;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {

    public static <T> T getProxy(final Class interfaceClass) {
        Class[] clazz = new Class[]{interfaceClass};
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Protocol protocol = ProtocolFactory.getProtocol();
                // 调用哪个方法
                Invocation invocation = new Invocation(DemoService.class.getName(),
                        "demoApi",new Object[]{"12234"},new Class[]{String.class});
                // 模拟负载均衡，随机获取服务器
                URL url = Register.random(interfaceClass.getName());
                return protocol.send(url, invocation);
            }
        });
    }
}
