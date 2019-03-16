package com.zyq.framework;

import com.zyq.protocol.dubbo.NettyProtocol;
import com.zyq.protocol.http.HttpProtocol;

public class ProtocolFactory {

    public static Protocol getProtocol() {
        String name = System.getProperty("protocolName");
        if (name == null || name.equals("")) name = "http";
        switch (name) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new NettyProtocol();
            default:
                break;
        }
        return new HttpProtocol();
    }
}
