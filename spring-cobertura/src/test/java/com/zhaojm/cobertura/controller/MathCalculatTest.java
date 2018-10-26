package com.zhaojm.cobertura.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
//        int w = 8/0;
        Integer i = mathCalculat.math(3, 3, "+");
        assertEquals(new Integer(6), i);
        i = mathCalculat.math(3, 3, "-");
        assertEquals(new Integer(0), i);
    }

    @Test
    @Ignore
    public void testMathDivision() {
        double ddd = mathCalculat.mathDivision(6, 2);
        assertTrue(ddd != 0.0);
    }
    
    @Test(expected = Exception.class)
    public void testThrowExe() throws Exception {
        String test = mathCalculat.throwExe("test");
        assertTrue(test != null);
        
        mathCalculat.throwExe(null);
    }

}
