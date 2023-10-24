package com.maxine.test;

import com.maxine.bean.User;
import com.maxine.service.UserService;
import com.maxine.service.impl.UserServiceImpl;
import org.junit.Test;

public class TestService {
    @Test
    public void testDoRegister(){
        User parameterUser = new User();
        parameterUser.setUserName("aobama");
        parameterUser.setUserPwd("123456");
        parameterUser.setEmail("123@qq.com");

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