package com.zyq.jedis.utils;

import java.io.UnsupportedEncodingException;

public class SafeEncoder {

    public static byte[] encoder(String str) {
        if (str == null || "".equals(str)) {
            throw new NullPointerException();
        }
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
