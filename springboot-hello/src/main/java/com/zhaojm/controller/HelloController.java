package com.zhaojm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @RequestMapping("/hello")
    public Map<String, String> hello(Map<String, String> map){
        
        map = new HashMap<String, String>();
        map.put("name", "zhaojiamin");
        map.put("age", "34");
        
        return map;
    }

}
