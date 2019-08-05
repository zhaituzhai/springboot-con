package com.zhaojm.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRun {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
        DemoSingletonService singleDemo1 = context.getBean(DemoSingletonService.class);
        DemoSingletonService singleDemo2 = context.getBean(DemoSingletonService.class);
        
        DemoPrototypeService prototyDemo1 = context.getBean(DemoPrototypeService.class);
        DemoPrototypeService prototyDemo2 = context.getBean(DemoPrototypeService.class);
        
        System.out.println("Single Demo is "+singleDemo1.equals(singleDemo2));
        System.out.println("Prototy Demo is "+prototyDemo1.equals(prototyDemo2));
        
        context.close();
    }

}
