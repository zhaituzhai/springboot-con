package com.zhaojm.redis.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.service.IUserAccountService;
import com.zhaojm.redis.utils.EasyPOIExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping
@Api(value = "一次性查询下载", tags = "一次性查询下载")
public class ExportExcelTotalController {

    private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    IUserAccountService userAccountService;


    @GetMapping
    @ApiOperation("EasyPOI下載Excel 10万会卡住")
    public void ExportUserInfo(HttpServletResponse response){
        long startTime = System.currentTimeMillis();
        List<UserAccountDTO> personList = userAccountService.getAllUserAccount();
        EasyPOIExcelUtil.exportExcel(personList,"花名册","草帽一伙",UserAccountDTO.class,"EasyPOI.xls",response);
        logger.info("easyPoi 时间执行了『{}』",System.currentTimeMillis()-startTime);
    }

    @GetMapping
    @ApiOperation("EasyExcel下載Excel")
    public void exportUserInfoEasyExcel(HttpServletResponse response){
        try {
            long startTime = System.currentTimeMillis();
            OutputStream os = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(os, ExcelTypeEnum.XLSX, true);

            // 2代表sheetNo,不可以重复,如果两个sheet的sheetNo相同则输出时只会有一个sheet
            Sheet sheet1 = new Sheet(1, 5, UserAccountDTO.class);
            sheet1.setSheetName("用户信息");
            List<UserAccountDTO> personList = userAccountService.getAllUserAccount();
            writer.write(personList, sheet1);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("easyExcel.xlsx", "UTF-8"));
            response.setHeader("content-Type", "application/vnd.ms-excel");

            writer.finish();
            logger.info("EasyExcel下載Excel时间执行了『{}』",System.currentTimeMillis()-startTime);
        } catch (FileNotFoundException e) {
            logger.debug("fileNotFound",e);
        } catch (UnsupportedEncodingException e) {
            logger.debug("UnsupportedEncodingException",e);
        } catch (IOException e) {
            logger.debug("IOException",e);
        }
    }

}
