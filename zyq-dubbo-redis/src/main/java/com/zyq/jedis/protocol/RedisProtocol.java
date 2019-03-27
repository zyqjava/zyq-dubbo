package com.zyq.jedis.protocol;

import java.io.IOException;
import java.io.OutputStream;

public class RedisProtocol {

    public static final String ASTERISK_STRING = "*";

    public static final String BLANK_STRING = "\r\n";

    public static final String DOLLAR_STRING = "$";

    /**
     * 根据redis的redis Serializition protocol协议来组装我们自己的数据
     * @param command
     * @param args
     */
    public static void sendCommadn(OutputStream outputStream, RedisProtocol.Command command, byte[]... args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ASTERISK_STRING).append(args.length + 1).append(BLANK_STRING); //*3
        stringBuilder.append(DOLLAR_STRING).append(command.name().length()).append(BLANK_STRING); //$3
        stringBuilder.append(command.name()).append(BLANK_STRING);  //SET/GET
        for (byte[] arg : args) {
            stringBuilder.append(DOLLAR_STRING).append(arg.length).append(BLANK_STRING); //$3,$3
            stringBuilder.append(new String(arg)).append(BLANK_STRING);     //zyq,666
        }
        try {
            outputStream.write(stringBuilder.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static enum Command {
        SET,GET
    }
}
