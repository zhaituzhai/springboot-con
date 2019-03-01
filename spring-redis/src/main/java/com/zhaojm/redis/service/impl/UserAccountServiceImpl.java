package com.zhaojm.redis.service.impl;

import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.mapper.IUserAccountMapper;
import com.zhaojm.redis.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements IUserAccountService {


    @Autowired
    private IUserAccountMapper userAccountMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public List<UserAccountDTO> getAllUserAccount(){
        return userAccountMapper.getAllUser();
    }

    @Override
    public UserAccountDTO getUserByName(String username) {
        UserAccountDTO userInfo = (UserAccountDTO)redisTemplate.opsForValue().get(username);
        if(userInfo != null){
            return  userInfo;
        }else {
            return userAccountMapper.getUserByName(username).get(0);
        }
    }

    @Override
    public UserAccountDTO loginUser(UserAccountDTO user){
        List<UserAccountDTO> userInfoList = userAccountMapper.getUserByName(user.getUserName());
        if (userInfoList.get(0) == null || !userInfoList.get(0).getPassword().equals(user.getPassword())){
            System.out.println("用戶不存在或密碼錯誤");
            return null;
        }
        UserAccountDTO loginUser = userInfoList.get(0);
        redisTemplate.opsForValue().set(user.getUserName(),loginUser);
        return user;
    }

    public boolean delLoginUser(String username){
        return redisTemplate.delete(username);
    }

}
