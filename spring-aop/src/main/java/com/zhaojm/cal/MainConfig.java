package com.zhaojm.cal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created on 2019/6/10.
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class MainConfig {

    @Bean
    public Calculate calculate() {
        return new MyCalculate();
    }

    @Bean
    public MyLogAspect tulingLogAspect() {
        return new MyLogAspect();
    }
}
