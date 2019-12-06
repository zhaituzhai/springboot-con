package com.zhaojm.cal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created on 2019/6/10.
 */
public class MainClass {

    public static void main(String[] args) {

    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);

        Calculate calculate = (Calculate) ctx.getBean("calculate");

        //int retVal = calculate.mod(2,4);
		calculate.div(6,2);
    }
}
