package com.cimu.usercenter.service;
import java.util.Date;

import com.cimu.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @description: 用户服务测试
        * @param: null
        * @return:
        * @author 14109
        * @date: 4/7/2024 上午11:38
 */
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){
        User user=new User();
        user.setUserName("dasd");
        user.setUserAccount("dsadasd");
        user.setAvatarUrl("ddsadq");
        user.setGender(0);
        user.setUserPassword("1234");
        user.setPhone("xxx");
        user.setEmial("aaaa");

        boolean save = userService.save(user);
        System.out.println(save);
        System.out.println(user.getId());
        assertTrue(save);
    }

    @Test
    void userRegister() {
        String userAccount="cimu";
        String userPassword="";
        String checkPassword="123456";
        String planetCode="666";
        long result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount="ci";
        result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount="dsadasd";
        result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount="ci  mu";
        result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount="cimu";
        userPassword="123456";
        result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userPassword="12345678";
        checkPassword="15646546656";
        result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        checkPassword="12345678";
        result=userService.UserRegister(userAccount,userPassword,checkPassword,planetCode);
        Assertions.assertTrue(result>0);
    }
}