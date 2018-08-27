package com.zhaojm.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.zhaojm.aop")
@EnableAspectJAutoProxy // 开启spring 对 aspectJ 的支持
public class AopConfig {

}
