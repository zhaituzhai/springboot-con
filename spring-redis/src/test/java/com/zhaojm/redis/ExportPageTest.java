package com.zhaojm.redis;

import com.zhaojm.redis.controller.ExportExcelPageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExportPageTest {

    @Autowired
    ExportExcelPageController pageController;

    class DoWork implements Runnable {
        @Override
        public void run() {
            HttpServletResponse response = new MockHttpServletResponse();
            pageController.exportUserInfoEasyExcel(5000, response);
        }
    }

    @Test
    public void testExport0(){
        DoWork dw = new DoWork();
        DoWork dw0 = new DoWork();
        DoWork dw1 = new DoWork();
        DoWork dw2 = new DoWork();
        DoWork dw3 = new DoWork();
        DoWork dw4 = new DoWork();

        Thread th = new Thread(dw);
        Thread th0 = new Thread(dw0);
        Thread th1 = new Thread(dw1);
        Thread th2 = new Thread(dw2);
        Thread th3 = new Thread(dw3);
        Thread th4 = new Thread(dw4);

        th.start();
        th0.start();
        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }

}
