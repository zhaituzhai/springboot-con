package com.zhaojm.data;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhaojm.data.service.IDealStatueService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportFileTest {

    @Autowired
    IDealStatueService dealStatueService;
    
    @Test
    public void testUpload() throws FileNotFoundException{
        FileInputStream file1 = new FileInputStream("E:\\Aisino\\ETCLOUD\\etc开发\\ETC3\\etc3.5\\test1.xlsx");
        FileInputStream file2 = new FileInputStream("E:\\Aisino\\ETCLOUD\\etc开发\\ETC3\\etc3.5\\test2.xlsx");
//        ResultDTO<Integer> result = medicineController.importMedicine((MultipartFile) file);
//        byte[] xmlByte = file.?
        int result = dealStatueService.toParse(file1,0);
        int result1 = dealStatueService.toParse(file2,1);
        
        assertTrue(result+result1 > 0);
    }
    
    @Test
    public void getString(){
        String str=dealStatueService.dataToMap();
        FileWriter writer;
        try {
            writer = new FileWriter("E:/testdata1.txt");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
