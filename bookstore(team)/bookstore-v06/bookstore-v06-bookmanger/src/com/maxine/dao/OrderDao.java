package com.maxine.dao;

import com.maxine.bean.Order;

public interface OrderDao {
    Integer insertOrder(Order order) throws Exception;
}
