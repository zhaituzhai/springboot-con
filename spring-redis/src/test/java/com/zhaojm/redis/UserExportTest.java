package com.zhaojm.redis;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.mapper.IUserAccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserExportTest {

    private static Logger logger = LoggerFactory.getLogger(UserExportTest.class);

    @Autowired
    IUserAccountMapper userAccountMapper;


    @Test
    public void testInsertUser(){
        long startTime = System.currentTimeMillis();
//        UserAccountDTO user = null;
//        for (int i = 0; i<100000; i++){
//            user = new UserAccountDTO();
//            user.setUserName("ExcelTest"+i);
//            user.setPassword("1234");
//            user.setUserType("3");
//            user.setUserPhone("1234567890"+i);
//            userAccountMapper.insertSelective(user);
//        }
        logger.info("时间执行了『{}』",System.currentTimeMillis()-startTime);
    }

    @Test
    public void testEasyExcelTest(){
        try {
            long startTime = System.currentTimeMillis();
            OutputStream os = new FileOutputStream(new File("new.xlsx"));
            ExcelWriter writer = new ExcelWriter(os, ExcelTypeEnum.XLSX, true);

            // 2代表sheetNo,不可以重复,如果两个sheet的sheetNo相同则输出时只会有一个sheet
            Sheet sheet1 = new Sheet(1, 5, UserAccountDTO.class);
            sheet1.setSheetName("第一个sheet");

//            Sheet sheet2 = new Sheet(2, 1, UserAccountDTO.class);
//            sheet2.setSheetName("第二个sheet");

            List<UserAccountDTO> personList = userAccountMapper.getAllUser();
            writer.write(personList, sheet1);

            writer.finish();
            System.out.println("数据已写出");
            logger.info("时间执行了『{}』",System.currentTimeMillis()-startTime);
        } catch (FileNotFoundException e) {
            logger.debug("fileNotFound",e);
        }
    }

    public static void main(String[] args) {
        String filename = "！@#￥%……&*\\r/r:r*r#$%^&*?r\\r\"r<r>r|r";
        System.out.println(filename.replaceAll("[/|:|*|?|\"|<|>|\\\\]", "_"));
    }


}
