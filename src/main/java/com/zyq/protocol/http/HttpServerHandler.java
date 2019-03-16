package com.zyq.protocol.http;

import com.zyq.framework.Invocation;
import com.zyq.framework.Url;
import com.zyq.register.Register;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServerHandler {

    public void handler(HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        InputStream inputStream = req.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Invocation invocation = (Invocation) ois.readObject();

        //找到实现类
        String interfaceName = invocation.getInterfaceName();
        Url url = new Url("localhost",8080);
        Class implClass = Register.get(url, interfaceName);

        Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamsTypes());
        //写框架的时候不能用string
        String result = (String) method.invoke(implClass.newInstance(), invocation.getParams());

        OutputStream outputStream = resp.getOutputStream();
        IOUtils.write("TOMCAT" + result, outputStream);

    }
}
