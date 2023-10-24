package com.xmy.dao.impl;

import com.xmy.bean.Book;
import com.xmy.bean.User;
import com.xmy.dao.BaseDao;
import com.xmy.dao.UserDao;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public User findByUsername(String username) throws Exception{
        String sql = "select user_id userId,user_name userName,user_pwd userPwd,email from bs_user where user_name=?";
        User user = getBean(User.class, sql, username);
        return user;
    }

    @Override
    public void insertUser(User user) {
        String sql = "insert into bs_user (user_name,user_pwd,email) values (?,?,?)";
        update(sql,user.getUserName(),user.getUserPwd(),user.getEmail());
    }

    public void addUser(User user) {
    }

    @Override
    public List<Book> selectBookList() throws SQLException {
        return null;
    }
}