package com.zhaojm.el;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRun {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
        
        ElConfig resourceService = context.getBean(ElConfig.class);
        
        resourceService.outputResource();
        
        context.close();

    }

}
