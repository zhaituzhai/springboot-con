package com.zhaojm.rureka;

@SpringBootApplication
@EnableEurekaServer
public class StartEruekaApp {
    public static void main(String[] args) {
        SpringApplication.run(StartEruekaApp.class, args);
    }
}
