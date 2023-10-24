package com.sziit.Service;

import com.sziit.bean.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList(String keyword, Integer pageNo) throws Exception;

    void removeBook(Integer bookId) throws Exception;

    void saveOrUpdateBook(Book book) throws Exception;

    Book getBookById(Integer bookId) throws Exception;

    List<Book> selectBookByDoublePrice(Double price1, Double price2) throws Exception;

    List<Book> selectBookByBookName(String name) throws Exception;

    List<Book> selectBookByDoubleCategory(String cate1,String cate2) throws Exception;

    List<Book> selectBookByCategory(String cate) throws Exception;

}
