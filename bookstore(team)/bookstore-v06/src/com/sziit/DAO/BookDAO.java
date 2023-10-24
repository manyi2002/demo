package com.sziit.DAO;

import com.sziit.bean.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    List<Book> selectBookList(String keyword ,Integer pageNo) throws SQLException;

    void deleteBook(Integer bookId) throws SQLException;

    void insertBook(Book book) throws SQLException;

    Book selectBookByPrimaryKey(Integer bookId) throws SQLException;

    void updateBook(Book book) throws SQLException;

    void updateBookArr(Object[][] bookArrParam);

    int getBookCount(String keyword);

    List<Book> selectBookByPrice(Double price1,Double price2) throws Exception;

    int getBookCountOnSelectByPrice(Double price1, Double price2);

    List<Book> selectBookByName(String name) throws Exception;

    List<Book> selectBookByDoubleCategory(String cate1,String cate2) throws Exception;

    List<Book> selectBookByCategory(String cate) throws Exception;

}
