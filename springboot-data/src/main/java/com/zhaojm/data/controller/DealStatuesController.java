package com.zhaojm.data.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zhaojm.data.service.IDealStatueService;

@Controller
public class DealStatuesController {

    @Autowired
    IDealStatueService dealStatueService;
    
    @RequestMapping(value = "/data/importFile", method = RequestMethod.POST)
    public int importMedicine(@RequestParam(value = "excelFile", required = true) MultipartFile xmlFile){
        
        File file = null;
        try {
            file=File.createTempFile("tmp", null);
            xmlFile.transferTo(file);
            file.deleteOnExit();         
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //得到上传文件 并变成二进制文件
        FileInputStream xmlByte = null;
        try {
            xmlByte = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //解析二进制文件
        int isParse = dealStatueService.toParse(xmlByte,1);
        return isParse;
    }
    
}
