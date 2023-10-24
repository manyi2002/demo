package com.Scarlett.dao;

import com.Scarlett.bean.Order;
import com.Scarlett.bean.User;

import java.util.List;

public interface OrderDao {
    public Integer insertOrder(Order order) throws Exception;

    public List<Order> findUserOrder(Integer userId, Integer currentPageNo, Integer pageSize) throws Exception;
    //（管理员有权限）查找所有用户订单
    public List<Order> findAllOrder(Integer currentPageNo,Integer pageSize) throws Exception;
    //根据订单id删除订单
    public void removeOrder(Integer orderId) throws Exception;
    //根据订单id查找订单
    public Order findOrderById(Integer orderId) throws Exception;
    //修改订单
    public void updateOrder(Order order) throws Exception;
    //获取订单的总数目
    public long getOrderCount(User user) throws Exception;

}
