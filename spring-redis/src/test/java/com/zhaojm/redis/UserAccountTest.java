package com.zhaojm.redis;

import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.service.IUserAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountTest {

    @Autowired
    IUserAccountService userAccountService;

    @Test
    public void getAllUserTest(){
        List<UserAccountDTO> userList = userAccountService.getAllUserAccount();
//        userList.forEach(System.out::println);
    }

    @Test
    public void getUserByName(){
        Long beginTime = new Date().getTime();
        userAccountService.getUserByName("matte");
        Long totalConsumedTime=new Date().getTime()-beginTime;
        System.out.println("數據查詢的時間："+totalConsumedTime);
    }

    @Test
    public void getUserByName1(){
        loginUser();
        Long beginTime = new Date().getTime();
        userAccountService.getUserByName("matte");
        Long totalConsumedTime=new Date().getTime()-beginTime;
        System.out.println("數據查詢的時間："+totalConsumedTime);
//        userAccountService.delLoginUser("matte");
    }

    private void loginUser() {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUserName("matte");
        userAccountDTO.setPassword("1234");
        userAccountService.loginUser(userAccountDTO);
    }

}
