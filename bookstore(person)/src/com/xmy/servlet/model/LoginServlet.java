package com.xmy.servlet.model;

import com.xmy.bean.User;
import com.xmy.service.UserService;
import com.xmy.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        /*
        * 不带数据库的登录校验
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if("tom".equals(username) && "12345".equals(password)){
            response.sendRedirect(request.getContextPath()+"/pages/user/login_success.html");
        } else {
            //response.getWriter().write("登录失败，请重试。");
            response.sendRedirect(request.getContextPath()+"/pages/user/login.html");
        }
        */

        //带数据库的登录校验
        //1. 获取客户端登录时的用户名和密码，并封装到user对象中
        Map<String, String[]> parameterMap = request.getParameterMap();
        User parameterUser = new User();
        try {
            BeanUtils.populate(parameterUser, parameterMap);
            //调用业务层的方法处理登录
            User loginUser = userService.doLogin(parameterUser);
            //说明登录成功，跳转到login_success.html页面
            response.sendRedirect(request.getContextPath()+"/pages/user/login_success.html");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            //说明登录失败，跳转回到login.html页面
            response.sendRedirect(request.getContextPath()+"/pages/user/login.html");
        }
    }
}
