package com.xmy.service;

import com.xmy.bean.Cart;
import com.xmy.bean.User;

public interface OrderService {
    public String checkout(Cart cart, User user) throws Exception;
}
