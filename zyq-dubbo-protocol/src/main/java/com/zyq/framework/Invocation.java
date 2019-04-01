package com.zyq.framework;

import java.io.Serializable;

public class Invocation implements Serializable{

    private String interfaceName;  //接口名称

    private String methodName;      //方法名称

    private Object[] params;    //参数

    private Class[] paramsTypes; //参数类型

    public Invocation(String interfaceName, String methodName, Object[] params, Class[] paramsTypes) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.params = params;
        this.paramsTypes = paramsTypes;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }
}
