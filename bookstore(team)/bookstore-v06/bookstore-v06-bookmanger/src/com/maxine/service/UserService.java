package com.maxine.service;

import com.maxine.bean.User;

public interface UserService {
    //处理登录
    User doLogin(User parameterUser) throws Exception;
    //处理注册
    void doRegister(User parameterUser) throws Exception;

    //调用持久层的方法根据username查询user
    void findByUsername(String username) throws Exception;
}