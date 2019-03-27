package com.zyq.register;

import com.zyq.framework.URL;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Register {
    private static Map<String,Map<URL,Class>> REGISTER = new HashMap<String, Map<URL, Class>>();

    /**
     * 注册服务（暴露接口）
     * @param url
     * @param interfaceName
     * @param implClass
     * @throws IOException
     */
    public static void register(URL url, String interfaceName, Class implClass) throws IOException {
        Map<URL, Class> map = new HashMap<URL, Class>();
        map.put(url, implClass);
        REGISTER.put(interfaceName, map);
        saveFile();
    }

    /**
     * 解决不同项目下，REGISTER无法拿到的问题,保存到本地
     * @param
     * @param
     * @return
     */
    private static void saveFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\dubbo\\info.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(REGISTER);
    }

    /**
     * 从注册中心获取实现类（发现服务）
     * @param url
     * @param interfaceName
     * @return
     */
    public static Class get(URL url, String interfaceName){
        return REGISTER.get(interfaceName).get(url);
    }

    /**
     * 模拟负载均衡,采用随机，也可采用平滑加权轮询算法
     * @param interfaceName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static URL random(String interfaceName) throws IOException, ClassNotFoundException {
        REGISTER = getFile();
        return  REGISTER.get(interfaceName).keySet().iterator().next();
    }




    /**
     * 从本地读取
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Map<String,Map<URL,Class>> getFile() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("D:\\dubbo\\info.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(file);
        return (Map<String, Map<URL, Class>>) objectInputStream.readObject();
    }
}
