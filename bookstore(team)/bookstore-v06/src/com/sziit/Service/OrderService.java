package com.sziit.Service;

import com.sziit.bean.*;

import java.util.List;

public interface OrderService {
    public String checkout(Cart cart, User user, Admin admin) throws Exception;

    public List<Order> getOrderList() throws Exception;

    public void removeOrder(Integer orderId) throws Exception;

    public Order getOrderById(Integer orderId) throws Exception;

    public void saveOrUpdateOrder(Order order) throws Exception;

    //根据用户id查找同一用户的所有订单
    public List<Order> findUserOrder(Integer userId, Integer currentPageNo, Integer pageSize) throws Exception;

    //（管理员有权限）查找所有用户订单
    public List<Order> findAllOrder(Integer currentPageNo, Integer pageSize) throws Exception;

    //根据订单id以获取详细订单
    public List<OrderItem> findOrderItem(Integer orderId) throws Exception;

    //获取订单的总数目
    public long getOrderCount(User user) throws Exception;
}
