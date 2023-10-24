package com.Scarlett.service;

import com.Scarlett.bean.User;

public interface UserService {
    //处理登录
    User doLogin(User parameterUser) throws Exception;
    //处理注册
    void doRegister(User parameterUser) throws Exception;
    //调用持久层的方法根据username查询user
    public void findByUsername(String username) throws Exception;
    //根据用户id判断当前是否为用户
    public User findByUserId(Integer UserId) throws Exception;
}
