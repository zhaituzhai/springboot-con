package com.zhaojm.ribbon.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateCountService {
    @GetMapping(value = "ribbon/hello")
    public String hello(){
        return "hello world!";
    }
}
