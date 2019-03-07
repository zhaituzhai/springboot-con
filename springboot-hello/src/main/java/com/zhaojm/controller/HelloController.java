package com.zhaojm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "hello",value = "first")
@RestController
public class HelloController {
    
    @GetMapping
    @ApiOperation("hello")
    public Map<String, String> hello(@ApiParam("map") Map<String, String> map){
        
        map = new HashMap<String, String>();
        map.put("name", "zhaojiamin");
        map.put("age", "34");
        
        return map;
    }

    @GetMapping
    public Map<String, String> getUser(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("zhaojm", "m");
        map.put("joy", "m");
        return map;
    }

}
