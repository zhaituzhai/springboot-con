package com.zhaojm.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Api("hello")
public class HelloController {

    @GetMapping
    @ApiOperation("index")
    public String index(){
        return "index";
    }

    @GetMapping
    @ApiOperation("hello")
    public String hello(){
        return "hello";
    }

    @GetMapping
    @ApiOperation("login")
    public String login(@RequestParam String username, @RequestParam String password){
        return username + "login";
    }
}
