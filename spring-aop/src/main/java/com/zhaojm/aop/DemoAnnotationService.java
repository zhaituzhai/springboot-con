package com.zhaojm.aop;

import org.springframework.stereotype.Service;

/****
 * 注解的被拦截类
 * 
 * @author matte
 *
 */

@Service
public class DemoAnnotationService {
    @Action(name = "注解式拦截的add操作")
    public void add() {
    }
}
