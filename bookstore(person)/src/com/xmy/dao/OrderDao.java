package com.xmy.dao;

import com.xmy.bean.Book;
import com.xmy.bean.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    public Integer insertOrder(Order order) throws Exception;

    public List<Book> selectBookList() throws SQLException;
}
