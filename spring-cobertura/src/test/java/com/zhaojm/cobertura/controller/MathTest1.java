package com.zhaojm.cobertura.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;

@RunWith(Parameterized.class)
@SpringBootTest
public class MathTest1 {
    
    private int num1;
    private int num2;
    private int num3;
    
    @Autowired
    MathCalculat mathCalculat;
    
    public MathTest1(int num1,int num2,int num3){
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
    }
    
    @Parameters
    public static Collection initParam(){
        Object [][] params = new Object[][]{
            {1,2,3},
            {2,2,4},
            {5,5,10}
        };
        return Arrays.asList(params);
    }
    
    @Before
    public void setUp() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }

    @Test
    public void testAdd(){
        int i = mathCalculat.mathPuls(num1, num2);
        assertEquals(num3, i);
    }
}
