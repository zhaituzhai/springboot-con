package com.zhaojm.data.controller;

import com.zhaojm.data.service.IDealStatueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping
@Api(value = "data", tags = "data")
public class DealStatuesController {

    @Autowired
    IDealStatueService dealStatueService;
    
//    @RequestMapping(value = "/data/importFile", method = RequestMethod.POST)
    @PostMapping
    @ApiOperation("导入文件")
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
