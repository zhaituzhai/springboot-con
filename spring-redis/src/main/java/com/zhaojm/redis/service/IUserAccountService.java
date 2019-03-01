package com.zhaojm.redis.service;

import com.zhaojm.redis.dao.UserAccountDTO;

import java.util.List;

public interface IUserAccountService {
    List<UserAccountDTO> getAllUserAccount();

    UserAccountDTO getUserByName(String username);

    UserAccountDTO loginUser(UserAccountDTO user);

    boolean delLoginUser(String username);
}
