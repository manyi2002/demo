package com.Scarlett.service;

import com.Scarlett.bean.Book;
import com.Scarlett.bean.Cart;
import com.Scarlett.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BookService {

    public List<Book> getBookList() throws Exception;

    public void removeBook(Integer bookId) throws Exception;

    public Book getBookById(Integer bookId) throws Exception;

    public void saveOrUpdateBook(Book book) throws Exception;



}
