package com.sziit.Service;

import com.sziit.bean.User;

public interface UserService {
    void doRegister(User userForm) throws Exception;

    User doLogin(User parameterUser) throws Exception;

    void findByUsername(String username) throws Exception;

    //根据用户id判断当前是否为用户
    public User findByUserId(Integer UserId) throws Exception;
}
