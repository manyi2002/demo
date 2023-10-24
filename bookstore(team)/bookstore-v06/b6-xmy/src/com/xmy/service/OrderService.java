package com.xmy.service;

import com.xmy.bean.Cart;
import com.xmy.bean.Order;
import com.xmy.bean.User;

import java.util.List;

public interface OrderService {
    public String checkout(Cart cart, User user) throws Exception;

    public List<Order> getOrderList() throws Exception;

    public void removeOrder(Integer orderId) throws Exception;

    public Order getOrderkById(Integer orderId) throws Exception;

    public void saveOrUpdateOrder(Order order) throws Exception;
}
