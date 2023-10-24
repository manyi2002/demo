package com.maxine.test;

import com.maxine.bean.User;
import com.maxine.dao.impl.UserDaoImpl;
import com.maxine.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;

public class TestDao {
    @Test
    public void testGetConnection(){
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }
//    @Test
//    public void testInsertUser() throws SQLException {
//        User user = new User(null, "aobama", "654321", "654321@qq.com");
//
//        new UserDaoImpl().addUser(user);
//    }
    @Test
    public void testFindByUsername() throws Exception {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User user = userDaoImpl.findByUsername("jay");
        System.out.println(user);
    }
}