package com.zhaojm.cobertura.controller;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MathCalculatTest {
    
    @Autowired
    MathCalculat mathCalculat;

    @Test
    public void testMathPuls() {
        Integer puls = mathCalculat.mathPuls(2, 3);
        assertEquals(new Integer(5), puls);
    }

    @Test
    public void testMathMinus() {
        Integer minus = mathCalculat.mathMinus(9, 4);
        assertEquals(new Integer(5), minus);
    }

    @Test
    public void testMathMultiplication() {
        Integer mult = mathCalculat.mathMultiplication(2, 3);
        assertEquals(new Integer(6), mult);
    }
    
    @Test
    public void testMath(){
        Integer i = mathCalculat.math(3, 3, "+");
        assertEquals(new Integer(6), i);
    }

    @Test
    @Ignore
    public void testMathDivision() {
        fail("Not yet implemented");
    }

}
