package com.zhaojm.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //服务注册与发现
//@EnableFeignClients
public class RedisAppStart {
    public static void main(String[] args) {
        SpringApplication.run(RedisAppStart.class);
    }
}
