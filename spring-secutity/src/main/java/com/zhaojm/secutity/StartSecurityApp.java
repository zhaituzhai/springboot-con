package com.zhaojm.secutity;

import com.zhaojm.common.config.swagger.SwaggerConfig;
import com.zhaojm.common.config.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebConfig.class, SwaggerConfig.class})
public class StartSecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(StartSecurityApp.class, args);
    }
}
