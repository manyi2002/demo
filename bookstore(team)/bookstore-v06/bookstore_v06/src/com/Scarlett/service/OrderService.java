package com.Scarlett.service;

import com.Scarlett.bean.*;

import java.util.List;

public interface OrderService {
    public String checkout(Cart cart, User user, Admin admin) throws Exception;
    //根据用户id查找同一用户的所有订单
    public List<Order> findUserOrder(Integer userId, Integer currentPageNo, Integer pageSize) throws Exception;
    //（管理员有权限）查找所有用户订单
    public List<Order> findAllOrder(Integer currentPageNo, Integer pageSize) throws Exception;
    //根据订单id以获取详细订单
    public List<OrderItem> findOrderItem(Integer orderId) throws Exception;
    //根据订单id删除订单
    public void removeOrder(Integer orderId) throws Exception;
    //管理员通过订单id修改订单
    public Order getOrderById(Integer orderId) throws Exception;
    //修改订单
    public void saveOrUpdateOrder(Order order) throws Exception;
    //获取订单的总数目
    public long getOrderCount(User user) throws Exception;
}
