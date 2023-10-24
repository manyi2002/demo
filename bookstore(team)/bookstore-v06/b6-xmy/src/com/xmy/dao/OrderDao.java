package com.xmy.dao;

import com.xmy.bean.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    public Integer insertOrder(Order order) throws Exception;

    public List<Order> selectOrderList() throws SQLException;

    public void deleteOrder(Integer orderId) throws SQLException;

    public Order selectOrderByPrimaryKey(Integer orderId) throws SQLException;

    public void updateOrder(Order order) throws SQLException;
}
