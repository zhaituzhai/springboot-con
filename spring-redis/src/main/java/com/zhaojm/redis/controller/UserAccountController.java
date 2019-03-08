package com.zhaojm.redis.controller;

import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.service.IUserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Api(value = "用户信息", tags = "用户信息")
public class UserAccountController {

    @Autowired
    private IUserAccountService userAccountService;

    @GetMapping
    @ApiOperation("得到所有用户")
    public List<UserAccountDTO> getAllUser(){
        return userAccountService.getAllUserAccount();
    }
}
