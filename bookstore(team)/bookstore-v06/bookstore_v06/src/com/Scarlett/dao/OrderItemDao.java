package com.Scarlett.dao;

import com.Scarlett.bean.Order;
import com.Scarlett.bean.OrderItem;

import java.util.List;

public interface OrderItemDao {
    public void insertOrderItemArr(Object[][] orderItemArrParam);
    public Integer insertOrder(Order order) throws Exception;
    //根据订单id以获取详细订单
    public List<OrderItem> findOrderItems(Integer orderId) throws Exception;
}

