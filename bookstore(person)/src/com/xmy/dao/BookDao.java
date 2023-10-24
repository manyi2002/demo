package com.xmy.dao;

import com.xmy.bean.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    List<Book> selectBookList() throws SQLException;

    public void deleteBook(Integer bookId) throws SQLException;

    public void insertBook(Book book) throws SQLException;

    public Book selectBookByPrimaryKey(Integer bookId) throws SQLException;

    public void updateBook(Book book) throws SQLException;

    public void updateBookArr(Object[][] bookArrParam);
}
