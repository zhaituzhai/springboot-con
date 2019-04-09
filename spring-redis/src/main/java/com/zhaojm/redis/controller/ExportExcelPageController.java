package com.zhaojm.redis.controller;

import com.zhaojm.redis.service.IUserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping
@Api(value = "分页查询下载", tags = "分页查询下载")
public class ExportExcelPageController {

    private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    IUserAccountService userAccountService;

    @GetMapping
    @ApiOperation("ETC下載Excel")
    public void exportUserInfoETC(HttpServletResponse response){

        long startTime = System.currentTimeMillis();
        Workbook workbook = userAccountService.getPageUserByETC();

        ServletOutputStream out = null;
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + java.net.URLEncoder.encode("ETCEXPORTUSer.xlsx", "UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
            response.flushBuffer();
        } catch (UnsupportedEncodingException e) {
            logger.error("字符编码转换错误:" + e.getMessage());
        } catch (IOException e) {
            logger.error("文件下载出错:" + e.getMessage());
        }

        logger.info("ETC下載Excel时间执行了『{}』",System.currentTimeMillis()-startTime);
    }



    @GetMapping
    @ApiOperation("EasyPOI下載Excel")
    public void ExportUserInfoBigDateTwo(HttpServletResponse response){
        long startTime = System.currentTimeMillis();
        Workbook workbook = userAccountService.getPageUserByEasyPoi();
        ServletOutputStream out = null;
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + java.net.URLEncoder.encode("EasyPoiPageHelper.xlsx", "UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
            response.flushBuffer();
        } catch (UnsupportedEncodingException e) {
            logger.error("字符编码转换错误:" + e.getMessage());
        } catch (IOException e) {
            logger.error("文件下载出错:" + e.getMessage());
        }
        logger.info("easyPoi big data 时间执行了『{}』",System.currentTimeMillis()-startTime);
    }

    @GetMapping
    @ApiOperation("EasyExcel下載Excel")
    public void exportUserInfoEasyExcel(@RequestParam int pagesize, HttpServletResponse response){
        long startTime = System.currentTimeMillis();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("easyExcel.xlsx", "UTF-8"));
            response.setHeader("content-Type", "application/vnd.ms-excel");
            ServletOutputStream os = response.getOutputStream();
            userAccountService.getPageUserByEasyExcel(pagesize, os);
        } catch (Exception e) {
            logger.debug("export error",e);
        }
        logger.info("EasyExcel下載Excel时间执行了『{}』步长『{}』",
                System.currentTimeMillis()-startTime, pagesize);
    }

    @GetMapping
    @ApiOperation("修改之后的EeayExcel 下载Excel")
    public void exportUserInfoExcelCon(HttpServletResponse response){
        long startTime = System.currentTimeMillis();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("easyExcel.xlsx", "UTF-8"));
            response.setHeader("content-Type", "application/vnd.ms-excel");
            ServletOutputStream os = response.getOutputStream();
            userAccountService.getPageUserByCon(os);
        } catch (Exception e) {
            logger.debug("export error",e);
        }
        logger.info("EasyExcel下載Excel时间执行了『{}』步长『{}』",
                System.currentTimeMillis()-startTime);
    }

}
