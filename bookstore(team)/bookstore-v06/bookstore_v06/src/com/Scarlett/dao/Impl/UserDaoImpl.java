package com.Scarlett.dao.Impl;

import com.Scarlett.bean.User;
import com.Scarlett.dao.BaseDao;
import com.Scarlett.dao.UserDao;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    //根据用户名找用户
    @Override
    public User findByUsername(String username) throws Exception{
        String sql="select user_id userId,user_name userName,user_pwd userPwd,email from t_user where user_name=?";
        User user =getBean(User.class,sql,username);
        return user;
    }
    //用户注册
    @Override
    public void insertUser(User user){
        String sql="insert into t_user(user_name,user_pwd,email) values (?,?,?)";
        update(sql,user.getUserName(),user.getUserPwd(),user.getEmail());
    }
    //根据id判断是否为用户
    @Override
    public User findByUserId(Integer id) throws Exception{
        String sql="select user_id userId,user_name userName,user_pwd userPwd,email from t_user where user_id=?";
        User user =getBean(User.class,sql,id);
        return user;
    }
}

