package com.zhaojm.data;

import com.zhaojm.common.config.swagger.SwaggerConfig;
import com.zhaojm.common.config.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import({WebConfig.class,SwaggerConfig.class})
public class StartDataApp {

    public static void main(String[] args) {
        SpringApplication.run(StartDataApp.class, args);
    }
}
