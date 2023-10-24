package com.sziit.servlet.model;

import com.sziit.servlet.base.ModelBaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/protected/admin")
public class AdminServlet extends ModelBaseServlet {
    //跳转到后台
    public void toManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("manager/manager",request,response);
    }
}
