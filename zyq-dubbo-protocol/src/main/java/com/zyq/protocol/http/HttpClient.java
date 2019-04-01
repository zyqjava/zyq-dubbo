package com.zyq.protocol.http;

import com.zyq.framework.InvocationHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpClient {
    public String post(String hostName, Integer port, InvocationHandler invocation) throws IOException {
        // 进行http连接
        URL url = new URL("http", hostName, port, "/");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);    // 必填项

        // 将对象写入输出流
        OutputStream outputStream = httpURLConnection.getOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(outputStream);
        ous.writeObject(invocation);
        ous.flush();
        ous.close();

        // 将输入流转为字符串（此处可是java对象）
        InputStream inputStream = httpURLConnection.getInputStream();
        return IOUtils.toString(inputStream);
    }
}
