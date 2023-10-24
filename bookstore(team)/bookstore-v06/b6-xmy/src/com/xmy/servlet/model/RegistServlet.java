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

//@WebServlet(name = "RegistServlet")
public class RegistServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        /*
        * 不使用数据库的注册
        String username = request.getParameter("username");
        System.out.println(username);

        if("happy".equals(username)){
            response.sendRedirect(request.getContextPath()+"/pages/user/regist.html");
        } else {
            response.sendRedirect(request.getContextPath()+"/pages/user/regist_success.html");
        }
        */

        //使用数据库的注册
        //获取请求参数封装到User中
        Map<String, String[]> parameterMap = request.getParameterMap();
        User parameterUser = new User();
        try {
            BeanUtils.populate(parameterUser, parameterMap);
            //调用业务层方法处理注册功能
            userService.doRegister(parameterUser);
            //没有出现异常，说明注册成功，跳转到regist_success.html页面
            response.sendRedirect(request.getContextPath()+"/pages/user/regist_success.html");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            //注册失败，跳转回regist.html页面
            response.sendRedirect(request.getContextPath()+"/pages/user/regist.html");
        }
    }
}