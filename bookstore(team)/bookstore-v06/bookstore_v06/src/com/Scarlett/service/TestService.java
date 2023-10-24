package com.Scarlett.service;

import com.Scarlett.bean.User;
import com.Scarlett.service.Impl.UserServiceImpl;
import org.junit.Test;

public class TestService {
    @Test
    public void testDoRegister(){
        User parameterUser = new User();
        parameterUser.setUserName("max12345");
        parameterUser.setUserPwd("max12345");
        parameterUser.setEmail("24885@qq.com");

        UserService userService = new UserServiceImpl();
        try {
            //说明注册成功
            userService.doRegister(parameterUser);
        } catch (Exception e) {
            //说明注册失败
            e.printStackTrace();
        }
    }
}