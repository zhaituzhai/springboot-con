package com.zhaojm.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HelloController {

    @GetMapping
    public String hello(){
        return "Hello";
    }

    @PostMapping
    public String login(String username, String password){
        return username + "login";
    }
}
