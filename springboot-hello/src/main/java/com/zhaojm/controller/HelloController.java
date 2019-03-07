package com.zhaojm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    
    @GetMapping
    public Map<String, String> hello(Map<String, String> map){
        
        map = new HashMap<String, String>();
        map.put("name", "zhaojiamin");
        map.put("age", "34");
        
        return map;
    }

}
