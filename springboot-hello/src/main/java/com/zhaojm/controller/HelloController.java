package com.zhaojm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "hello",value = "first")
@RestController
@RequestMapping
public class HelloController {
    
    @PostMapping
    @ApiOperation("hello")
    public Map<String, String> hello(@ApiParam("map") @RequestBody Map<String, String> map){
        
        Map<String, String> mapResult = new HashMap<String, String>();
        mapResult.putAll(map);
        mapResult.put("name", "zhaojiamin");
        mapResult.put("age", "34");
        
        return mapResult;
    }

    @GetMapping
    public Map<String, String> getUser(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("zhaojm", "m");
        map.put("joy", "m");
        return map;
    }

}
