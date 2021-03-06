package com.zyq.jedis.client;

import com.zyq.jedis.protocol.RedisProtocol;
import com.zyq.jedis.transfer.Connection;
import com.zyq.jedis.utils.SafeEncoder;

public class ZyqClient {

    private Connection connection;

    public ZyqClient(String host, int port) {
        this.connection = new Connection(host, port);
    }

    /**
     * 他的底层还是在于操作redis
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, String value) {
        connection.sendCommand(RedisProtocol.Command.SET, SafeEncoder.encoder(key), SafeEncoder.encoder(value));
        return connection.getStatusReply();
    }

    public String get(final String key) {
        connection.sendCommand(RedisProtocol.Command.GET, SafeEncoder.encoder(key));
        return connection.getStatusReply();
    }
}
