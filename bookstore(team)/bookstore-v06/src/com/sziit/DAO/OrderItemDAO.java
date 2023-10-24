package com.sziit.DAO;

import com.sziit.bean.Order;
import com.sziit.bean.OrderItem;

import java.util.List;

public interface OrderItemDAO {
    void insertOrderItemArr(Object[][] orderItemArrParam);

    public Integer insertOrder(Order order) throws Exception;

    //根据订单id以获取详细订单
    public List<OrderItem> findOrderItems(Integer orderId) throws Exception;
}
