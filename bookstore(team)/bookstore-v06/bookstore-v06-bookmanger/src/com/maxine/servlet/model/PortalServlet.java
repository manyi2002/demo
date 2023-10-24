package com.maxine.servlet.model;

import com.maxine.bean.Book;
import com.maxine.service.BookService;
import com.maxine.service.impl.BookServiceImpl;
import com.maxine.servlet.base.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PortalServlet extends ViewBaseServlet {
    private BookService bookService = (BookService) new BookServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询所有图书列表
        try{
            //查询所有图书列表
            List<Book> bookList = bookService.getBookList();
            //将图书列表存储到请求域对象
            request.setAttribute("list", bookList);
            //解析模板
            processTemplate("index", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}