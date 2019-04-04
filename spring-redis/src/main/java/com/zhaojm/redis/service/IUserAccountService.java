package com.zhaojm.redis.service;

import com.github.pagehelper.PageInfo;
import com.zhaojm.redis.dao.UserAccountDTO;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import java.util.List;

public interface IUserAccountService {
    List<UserAccountDTO> getAllUserAccount();

    PageInfo<UserAccountDTO> getpageUser();

    UserAccountDTO getUserByName(String username);

    UserAccountDTO loginUser(UserAccountDTO user);

    boolean delLoginUser(String username);

    Workbook getPageUserByEasyPoi();

    Workbook getPageUserByETC();

    void getPageUserByEasyExcel(int pagesize, ServletOutputStream os);
}
