package com.zhaojm.rureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StartEruekaApp {
    public static void main(String[] args) {
        SpringApplication.run(StartEruekaApp.class, args);
    }
}
