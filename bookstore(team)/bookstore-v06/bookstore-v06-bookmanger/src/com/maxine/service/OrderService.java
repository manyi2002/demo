package com.maxine.service;

import com.maxine.bean.Cart;
import com.maxine.bean.User;

public interface OrderService {
    String checkout(Cart cart, User user) throws Exception;
}
