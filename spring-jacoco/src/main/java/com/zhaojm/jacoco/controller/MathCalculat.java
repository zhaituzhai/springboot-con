package com.zhaojm.jacoco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaojm.jacoco.service.MathService;

@Service
public class MathCalculat {
    
    @Autowired
    MathService mathService;
    
    public Integer mathPuls(Integer num1,Integer num2){
        return num1+num2;
    }
    
    public Integer mathMinus(Integer Subtraction, Integer Subtracted){
        return Subtraction-Subtracted;
    }
    
    public Integer mathMultiplication(Integer num1, Integer num2){
        return num1*num2;
    }
    
    public double mathDivision(Integer num1,Integer num2){
        return num1/(num2*1.0);
    }
    
    public Integer math (Integer num1,Integer num2,String method){
        if(method.equals("+")){
            mathService.logTest(""+num1+method+num2);
            return num1+num2;
        }else if(method.equals("-")){
            return num1-num2;
        }else{
            return -1;
        }
    }

}
