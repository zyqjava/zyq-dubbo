package com.zyq.register;

import com.zyq.framework.Url;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Register {
    private static Map<String,Map<Url,Class>> REGISTER = new HashMap<String, Map<Url, Class>>();

    public static void register(Url url, String interfaceName, Class implClass) throws IOException {
        Map<Url, Class> map = new HashMap<Url, Class>();
        map.put(url, implClass);
        REGISTER.put(interfaceName, map);
        saveFile();
    }

    private static void saveFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\dubbo\\info.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(REGISTER);
    }

    public static Class get(Url url, String interfaceName){
        return REGISTER.get(interfaceName).get(url);
    }

    public static Url random(String interfaceName) throws IOException, ClassNotFoundException {
        REGISTER = getFile();
        return  REGISTER.get(interfaceName).keySet().iterator().next();
    }

    public static Map<String,Map<Url,Class>> getFile() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("D:\\dubbo\\info.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(file);
        return (Map<String, Map<Url, Class>>) objectInputStream.readObject();
    }
}
