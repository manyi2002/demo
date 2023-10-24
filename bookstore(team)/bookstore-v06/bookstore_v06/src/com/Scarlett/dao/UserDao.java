package com.Scarlett.dao;

import com.Scarlett.bean.User;

public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username) throws Exception;

    /**
     * 保存用户信息到数据库
     * @param user
     */
    void insertUser(User user);


    //根据用户id查找用户
    public User findByUserId(Integer id) throws Exception;
}