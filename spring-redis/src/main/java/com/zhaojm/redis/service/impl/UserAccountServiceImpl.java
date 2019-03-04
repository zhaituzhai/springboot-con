package com.zhaojm.redis.service.impl;

import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.mapper.IUserAccountMapper;
import com.zhaojm.redis.service.IUserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);

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

}
