package com.zyq.protocol.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class HttpServer {

    public void start(String hostName, Integer port) throws LifecycleException {

        //通过server.xml的格式
        // 实例一个tomcat
        Tomcat tomcat = new Tomcat();

        // 构建server
        Server server = tomcat.getServer();

        /**
         * 在getServer的时候，就在方法内部执行了
         * Service service = new StandardService();
         * service.setName("Tomcat");
         * server.addService(service);
         */
        // 获取service
        Service service = server.findService("Tomcat");

        // 构建Connector
        Connector connector = new Connector();
        connector.setPort(port);

        // 构建Engine
        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        // 构建Host
        Host host = new StandardHost();
        host.setName(hostName);

        // 构建Context
        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener()); // 生命周期监听器

        // 然后按照server.xml，一层层把子节点添加到父节点
        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);
        // service在getServer时就被添加到server节点了

        // tomcat是一个servlet,设置路径与映射
        tomcat.addServlet(contextPath, "dispatcher", new DispathcerServlet());
        context.addServletMappingDecoded("/*","dispatcher");

        tomcat.start();
        tomcat.getServer().await();     // 接受请求

    }

}
