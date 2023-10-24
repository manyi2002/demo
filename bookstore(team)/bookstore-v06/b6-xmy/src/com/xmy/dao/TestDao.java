package com.xmy.dao;

import com.xmy.bean.User;
import com.xmy.dao.impl.UserDaoImpl;
import com.xmy.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class TestDao {
    @Test
    public void testGetConnection(){
        Connection connection = (Connection) JDBCUtil.getConnection();
        System.out.println(connection);
    }
    @Test
    public void testInsertUser() throws SQLException {
        User user = new User(null, "aobama", "654321", "654321@qq.com");

        new UserDaoImpl().addUser(user);
    }
    @Test
    public void testFindByUsername() throws Exception {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User user = userDaoImpl.findByUsername("jay");
        System.out.println(user);
    }
}
