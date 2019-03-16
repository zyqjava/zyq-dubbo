package com.zyq.provider.impl;

import com.zyq.provider.api.DemoService;

public class DemoServiceImpl implements DemoService{
    public String demoApi(String name) {
        return "success!!" + name;
    }
}
