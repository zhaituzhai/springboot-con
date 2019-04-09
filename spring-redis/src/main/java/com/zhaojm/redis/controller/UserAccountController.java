package com.zhaojm.redis.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.service.IUserAccountService;
import com.zhaojm.redis.vo.UserQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Api(value = "用户信息", tags = "用户信息")
public class UserAccountController {

    private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    private IUserAccountService userAccountService;

    @GetMapping
    @ApiOperation("得到所有用户")
    public List<UserAccountDTO> getAllUser(){
        return userAccountService.getAllUserAccount();
    }
    @PostMapping
    @ApiOperation("得到所有用户")
    public PageInfo<UserAccountDTO> getPageUser(@RequestBody @ApiParam("查询对象") UserQueryVO userVO){
        return userAccountService.getPageUser(userVO);
    }




    @GetMapping
    @ApiOperation("EasyPOI big data 下載Excel")
    public void ExportUserInfoBigDate(HttpServletResponse response){
        long startTime = System.currentTimeMillis();
        Map<String, Object> params = new HashMap();
        Page page = new Page();
        Workbook workbook = bigExcel(1, params, null, new ExportParams("海贼王", "海贼王"), page);
        ExcelExportUtil.closeExportBigExcel();
        downLoadExcel("EasyPOIbigdata.xls", response, workbook);
        logger.info("easyPoi big data 时间执行了『{}』",System.currentTimeMillis()-startTime);
    }

    private Workbook bigExcel(int pageNum, Map<String, Object> params, Workbook workbook, ExportParams exportParams, Page<UserAccountDTO> page) {
        //分页查询数据
        page.setStartRow(pageNum);
        page.setPageSize(1000);
        List<UserAccountDTO> users = userAccountService.getAllUserAccount();

        workbook = ExcelExportUtil.exportBigExcel(exportParams, UserAccountDTO.class, users);

        //如果不是最后一页，递归查询
        if (page.getPages() > pageNum) {
            bigExcel(pageNum + 1, params, workbook, exportParams, page);
        }
        return workbook;
    }

    private void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("content-Type", "application/vnd.ms-excel");
            workbook.write(out);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }




    @PostMapping
    @ApiOperation("数组信息传递")
    public String returnString(@RequestBody String userIdList){
        return userIdList;
    }



}
