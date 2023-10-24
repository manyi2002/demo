package com.sziit.DAO;

import com.sziit.bean.Order;
import com.sziit.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    Integer insertOrder(Order order) throws Exception;

    List<Order> selectOrderList() throws SQLException;

    void deleteOrder(Integer orderId) throws SQLException;

    Order selectOrderByPrimaryKey(Integer orderId) throws SQLException;

    void updateOrder(Order order) throws SQLException;

    List<Order> findUserOrder(Integer userId, Integer currentPageNo, Integer pageSize) throws Exception;

    //（管理员有权限）查找所有用户订单
    List<Order> findAllOrder(Integer currentPageNo, Integer pageSize) throws Exception;

    //根据订单id删除订单
    void removeOrder(Integer orderId) throws Exception;

    //根据订单id查找订单
    Order findOrderById(Integer orderId) throws Exception;

    //获取订单的总数目
    long getOrderCount(User user) throws Exception;
}
