package com.xmy.dao;

import com.xmy.bean.Book;
import com.xmy.bean.User;

import java.sql.SQLException;
import java.util.List;

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

    public List<Book> selectBookList() throws SQLException;
}
