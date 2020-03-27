package com.zhaojm.data;

import com.zhaojm.data.service.IDealStatueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ImportFileTest {

    @Autowired
    IDealStatueService dealStatueService;
    
    @Test
    public void testUpload() throws FileNotFoundException{
        FileInputStream file1 = new FileInputStream("E:\\test1.xlsx");
        FileInputStream file2 = new FileInputStream("E:\\test2.xlsx");
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
            writer = new FileWriter("E:/testdata122.txt");
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*@AfterClass
    public static void cleanData(){
        File file = new File("E:/testdata122.txt");
        if(file.exists() && file.isFile()){
            file.delete();
        }
    }*/
    
}
