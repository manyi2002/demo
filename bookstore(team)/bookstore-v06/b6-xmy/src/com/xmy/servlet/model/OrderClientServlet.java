package com.xmy.servlet.model;

import com.xmy.bean.Cart;
import com.xmy.bean.User;
import com.xmy.constants.BookStoreConstants;
import com.xmy.service.OrderService;
import com.xmy.service.impl.OrderServiceImpl;
import com.xmy.servlet.base.ModelBaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderClientServlet extends ModelBaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    //订单结算
    public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1. 从session中获取购物车信息
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //2. 从session中获取用户信息
            User user = (User) session.getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
            //3. 调用业务层的方法，进行订单结算，并且获取订单的序列号
            String orderSequence = orderService.checkout(cart,user);
            //4. 清空购物车
            session.removeAttribute(BookStoreConstants.CART_SESSIONKEY);
            //5. 将订单序列号存储到请求域对象中，并且跳转到checkout.html页面
            request.setAttribute("orderSequence",orderSequence);
            processTemplate("cart/checkout",request,response);
        } catch (Exception e) {
            e.printStackTrace();
            processTemplate("error", request, response);
            throw new RuntimeException(e.getMessage());
        }
    }
}