package com.zhaojm.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRun {

    public static void main(String[] args) {
        // 使用AnnotationConfigApplicationContext作为spring容器，接受输入一个配置类作为参数
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        
        DemoAnnotationService demoAnnotationService = context.getBean(DemoAnnotationService.class);
        
        DemoMethodService demoMethodService = context.getBean(DemoMethodService.class);
        
        demoAnnotationService.add();
        
        demoMethodService.add();
        
        context.close();
    }
}
