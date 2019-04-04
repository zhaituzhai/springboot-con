package com.zhaojm.redis.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.mapper.IUserAccountMapper;
import com.zhaojm.redis.service.IUserAccountService;
import com.zhaojm.redis.utils.ExcelExportHelp;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);
    
    private final int exportPageSize = 10000;

    @Autowired
    private IUserAccountMapper userAccountMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ExcelExportHelp excelExportHelp;

    @Override
    public List<UserAccountDTO> getAllUserAccount(){
        return userAccountMapper.getAllUser();
    }

    @Override
    public PageInfo<UserAccountDTO> getpageUser(){
        int pageSize = 1000;
        PageHelper.startPage(1, pageSize);
        List<UserAccountDTO> userlist = userAccountMapper.getAllUser();
        PageInfo<UserAccountDTO> pageInfo = new PageInfo(userlist);
        return pageInfo;
    }


    @Override
    public UserAccountDTO getUserByName(String username) {
        UserAccountDTO userInfo = (UserAccountDTO)redisTemplate.opsForValue().get(username);
        if(userInfo != null){
            LOGGER.info("在redis中查询到信息！");
            return  userInfo;
        }else {
            LOGGER.info("数据库中查询数据");
            return userAccountMapper.getUserByName(username).get(0);
        }
    }

    @Override
    public UserAccountDTO loginUser(UserAccountDTO user){
        List<UserAccountDTO> userInfoList = userAccountMapper.getUserByName(user.getUserName());
        if (userInfoList.get(0) == null || !userInfoList.get(0).getPassword().equals(user.getPassword())){
            LOGGER.info("用戶不存在或密碼錯誤");
            return null;
        }
        UserAccountDTO loginUser = userInfoList.get(0);
        redisTemplate.opsForValue().set(user.getUserName(),loginUser);
        redisTemplate.expire(user.getUserName(),15,TimeUnit.MINUTES);
        return user;
    }

    public boolean delLoginUser(String username){
//        return redisTemplate.delete(username);
        return true;
    }

    @Override
    public Workbook getPageUserByEasyPoi() {
        PageHelper.startPage(1, exportPageSize);
        List<UserAccountDTO> userList = userAccountMapper.getAllUser();
        PageInfo pageInfo = new PageInfo(userList);
        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams(), UserAccountDTO.class, pageInfo.getList());
        int pages = pageInfo.getPages();
        int nowPageNum = 2;
        while(nowPageNum <= pages){
            PageHelper.startPage(nowPageNum++, exportPageSize);
            List<UserAccountDTO> appendUserList = userAccountMapper.getAllUser();
            workbook = ExcelExportUtil.exportBigExcel(new ExportParams(), UserAccountDTO.class, appendUserList);
        }
        return workbook;
    }

    @Override
    public Workbook getPageUserByETC() {
        PageHelper.startPage(1, exportPageSize);
        List<UserAccountDTO> userList = userAccountMapper.getAllUser();
        PageInfo pageInfo = new PageInfo(userList);
        Workbook workbook = excelExportHelp.init(UserAccountDTO.class);
        excelExportHelp.append(pageInfo.getList());
        int pages = pageInfo.getPages();
        int nowPageNum = 2;
        while(nowPageNum <= pages){
            PageHelper.startPage(nowPageNum++, exportPageSize);
            List<UserAccountDTO> appendUserList = userAccountMapper.getAllUser();
            excelExportHelp.append(appendUserList);
        }
        excelExportHelp.close();
        return workbook;
    }

    @Override
    public void getPageUserByEasyExcel(int pagesize, ServletOutputStream os) {
//        PageHelper.startPage(1, pagesize, false);
//        List<UserAccountDTO> userList = userAccountMapper.getAllUser();
//        PageInfo pageInfo = new PageInfo(userList);

        ExcelWriter writer = new ExcelWriter(os, ExcelTypeEnum.XLSX);
        // 2代表sheetNo,不可以重复,如果两个sheet的sheetNo相同则输出时只会有一个sheet
        Sheet sheet = new Sheet(1, 0 , UserAccountDTO.class);
        sheet.setSheetName("用户信息");
//        writer.write(pageInfo.getList(), sheet);
//        int pages = pageInfo.getPages();
        int nowPageNum = 1;
        while(true){
            PageHelper.startPage(nowPageNum++, pagesize, false);
            List<UserAccountDTO> appendUserList = userAccountMapper.getAllUser();
            writer.write(appendUserList, sheet);
            if(appendUserList.size() < pagesize){
                break;
            }
        }
        writer.finish();
    }

}
