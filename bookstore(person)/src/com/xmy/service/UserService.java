package com.xmy.service;

import com.xmy.bean.User;

public interface UserService {
    //处理登录
    User doLogin(User parameterUser) throws Exception;
    //处理注册
    void doRegister(User parameterUser) throws Exception;

    public void findByUsername(String username) throws Exception;
}
