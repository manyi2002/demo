package com.maxine.service;

import com.maxine.bean.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookService {

    //查询所有图书列表
    List<Book> getBookList() throws Exception;

    //删除图书
    void removeBook(Integer bookId) throws Exception;

    //添加图书信息
    void saveOrUpdateBook(Book book) throws SQLException;

    //修改图书信息
    Book getBookById(Integer bookId) throws Exception;
}
