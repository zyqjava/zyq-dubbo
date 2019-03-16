package com.zyq.protocol.http;

import com.zyq.framework.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpClient {
    public String post(String hostName, Integer port, Invocation invocation) throws IOException {
        URL url = new URL("http", hostName, port, "/");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(outputStream);
        ous.writeObject(invocation);
        ous.flush();
        ous.close();

        InputStream inputStream = httpURLConnection.getInputStream();
        return IOUtils.toString(inputStream);
    }
}
