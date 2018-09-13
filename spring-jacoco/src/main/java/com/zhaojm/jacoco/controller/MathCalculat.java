package com.zhaojm.jacoco.controller;

import org.springframework.stereotype.Service;

@Service
public class MathCalculat {
    
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

}
