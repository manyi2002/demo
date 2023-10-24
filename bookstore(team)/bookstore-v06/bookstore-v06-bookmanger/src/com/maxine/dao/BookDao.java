package com.maxine.dao;

import com.maxine.bean.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    //查询所有图书列表
    Book selectBookByPrimaryKey(Integer bookId) throws SQLException;

    //删除图书
    void deleteBook(Integer bookId) throws SQLException;

    //添加图书信息
    void insertBook(Book book) throws SQLException;

    //修改图书信息
    void updateBook(Book book) throws SQLException;

    List<Book> selectBookList() throws SQLException;

    void updateBookArr(Object[][] bookArrParam);
}
