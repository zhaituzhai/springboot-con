package com.zhaojm.redis.controller;

import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAccountController {

    @Autowired
    private IUserAccountService userAccountService;

    @GetMapping(value = "/getUsers")
    public List<UserAccountDTO> getAllUser(){
        return userAccountService.getAllUserAccount();
    }
}
