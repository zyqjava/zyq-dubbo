package com.zyq.jedis.hack;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪装jedis服务端
 */
public class SocketHack {

    private static final int PORT = 6379;

    /**
     * *
     ===
     *3
     ===
     $3   数字表示多少或者说是长度
     SET
     ==
     $3
     zyq
     ===
     $3
     666
     这段代码就是redis自己所使用的协议，在specifivations
     * 代表数组，$表示字符串，：表示整形，-：表示错误，+：表示字符
     * @param args
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket accept = serverSocket.accept();
            byte[] b = new byte[1024];
            accept.getInputStream().read(b);
            System.out.println(new String(b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
