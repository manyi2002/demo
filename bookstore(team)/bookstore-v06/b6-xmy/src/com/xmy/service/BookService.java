package com.xmy.service;

import com.xmy.bean.Book;

import java.util.List;

public interface BookService {
    public List<Book> getBookList() throws Exception;

    public void removeBook(Integer bookId) throws Exception;

    public void saveOrUpdateBook(Book book) throws Exception;

    public Book getBookById(Integer bookId) throws Exception;
}
