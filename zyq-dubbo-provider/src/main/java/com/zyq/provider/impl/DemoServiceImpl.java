package com.zyq.provider.impl;

import com.zyq.provider.api.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService{

    public String demoApi(String name, String age) {
        return "success!!----name:" + name + " ---age :" + age;
    }
}
