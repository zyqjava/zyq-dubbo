package com.zyq.jedis.transfer;

import com.zyq.jedis.protocol.RedisProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private String host;
    private int port;
    private OutputStream outputStream;
    private InputStream inputStream;

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connection(){
        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建连接
     * 发送数据
     * 已经实现
     * @param command
     * @param args
     * @return
     */
    public Connection sendCommand(RedisProtocol.Command command, byte[]... args) {
        connection();
        //redis 使用了自身的一个协议: redis Serializition protocol
        RedisProtocol.sendCommadn(outputStream, command, args);
        return this;
    }

    /**
     * 返回读取数据
     * @return
     */
    public String getStatusReply() {
        byte[] b = new byte[1024];
        try {
            socket.getInputStream().read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(b);
    }
}
