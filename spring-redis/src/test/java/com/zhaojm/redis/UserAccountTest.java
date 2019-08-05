package com.zhaojm.redis;

import com.github.pagehelper.PageInfo;
import com.zhaojm.redis.dao.UserAccountDTO;
import com.zhaojm.redis.service.IUserAccountService;
import com.zhaojm.redis.vo.UserQueryVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    @Test
    public void testPageUser(){
        PageInfo pageInfo = userAccountService.getPageUserList();
        System.out.println(pageInfo.getList());
    }

    @Test
    public void testPageOrder(){
        UserQueryVO userVO = new UserQueryVO();
        LinkedHashMap<String, String> orderMap = new LinkedHashMap<>();
        orderMap.put("user_name","asc");
        orderMap.put("creat_time","desc");
//        userVO.setOrderMap(orderMap);
        PageInfo pageInfo = userAccountService.getPageUser(userVO);
        System.out.println(pageInfo.getList());
    }

    public static void main(String[] args) {
        Map<String, String> orderMap = new HashMap<>();
        orderMap.put("1","1");
        orderMap.put("2","2");
        orderMap.put("3","3");
        orderMap.put("4","4");
        orderMap.put("5","5");
        orderMap.put("6","6");
        orderMap.put("7","7");
        orderMap.put("8","8");
        orderMap.put("9","9");
        orderMap.put("10","10");
        orderMap.put("11","11");
        orderMap.put("12","12");
        orderMap.put("13","13");
        orderMap.put("14","14");
        orderMap.put("15","15");
        orderMap.put("16","16");
        orderMap.put("17","17");
        orderMap.put("18","18");
        orderMap.put("19","19");
        orderMap.put("20","20");
        orderMap.put("21","21");
        orderMap.put("22","22");
        orderMap.put("23","23");
        orderMap.put("24","24");
        orderMap.put("25","24");
        orderMap.put("26","26");
        Set<Map.Entry<String, String>> entry = orderMap.entrySet();
        entry.forEach(k -> {
            System.out.println(k.getKey()+" "+k.getValue());
        });
    }

}
