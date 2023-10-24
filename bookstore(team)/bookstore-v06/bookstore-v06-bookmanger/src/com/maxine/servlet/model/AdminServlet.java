package com.maxine.servlet.model;

import com.maxine.servlet.base.ModelBaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends ModelBaseServlet {
    //跳转到后台
    public void toManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("manager/manager",request,response);
    }
}