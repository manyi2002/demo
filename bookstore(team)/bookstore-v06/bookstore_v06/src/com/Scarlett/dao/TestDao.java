package com.Scarlett.dao;

import com.Scarlett.Utils.JDBCUtil;
import com.Scarlett.bean.User;
import com.Scarlett.dao.Impl.UserDaoImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDao {
    @Test
    public void testGetConnection(){
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testInsertUser() throws SQLException {
        User user;
        user = new User(null, "aobama", "654321", "654321@qq.com");

        //new UserDaoImpl().addUser(user);
    }
    @Test
    public void testFindByUsername() throws Exception {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User user = userDaoImpl.findByUsername("jay");
        System.out.println(user);
    }
}