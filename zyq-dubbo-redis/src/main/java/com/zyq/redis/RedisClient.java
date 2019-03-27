package com.zyq.redis;

import redis.clients.jedis.Jedis;

public class RedisClient {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("203.195.157.208",6379);
        jedis.set("zyq","niubi");
        System.out.println(jedis.get("zyq"));
    }
}
