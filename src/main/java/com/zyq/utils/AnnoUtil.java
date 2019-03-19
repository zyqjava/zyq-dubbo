package com.zyq.utils;

import com.zyq.anno.RpcClazz;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnoUtil {

    //需要放入包名，以免重名
    public static Map<String, List<Map<String, List<String>>>> parseAnno(String packageName) throws ClassNotFoundException {
        Map<String, List<Map<String, List<String>>>> map = new HashMap<>();
        String baseResource = AnnoUtil.class.getResource("/").getPath();
        String packagePath = packageName.replace(".","/");
        File file = new File(baseResource + packagePath);
        //可以通过urlclassLoder
        String[] listNames = file.list();
        for (String listName : listNames) {
            listName = listName.replaceAll(".class","");
            Class<?> clazzs = Class.forName(packageName + "." + listName);
            if (clazzs.isAnnotationPresent(RpcClazz.class)) {   //是否有RpcClazz注解
                Method[] declaredMethods = clazzs.getDeclaredMethods();
                List<Map<String, List<String>>> rpcMethods = new ArrayList<>();
                for (Method declaredMethod : declaredMethods) {
                    if (declaredMethod.isAnnotationPresent(RpcClazz.class)) {
                        Map<String, List<String>> rpcMethod = new HashMap<>();
                        List<String> parames = new ArrayList<>();  //保存参数类型
                        //所有的参数类型
                        Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                        //解析参数类型
                        for (Class<?> parameterType : parameterTypes) {
                            parames.add(parameterType.getSimpleName());
                        }
                        rpcMethod.put(declaredMethod.getName(), parames);
                        rpcMethods.add(rpcMethod);
                    }
                }
                map.put(packageName + "." + listName ,rpcMethods);
            }
        }
        return map;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(parseAnno("com.zyq.provider.impl"));
    }
}
