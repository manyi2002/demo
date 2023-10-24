package com.Scarlett.service.Impl;

import com.Scarlett.bean.Book;
import com.Scarlett.bean.User;
import com.Scarlett.dao.BookDao;
import com.Scarlett.dao.Impl.BookDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> getBookList() throws Exception{
        return bookDao.selectBookList();
    }
    //删除图书
    @Override
    public void removeBook(Integer bookId) throws Exception {
        bookDao.deleteBook(bookId);
    }



    @Override
    public void saveOrUpdateBook(Book book) throws Exception {
        //1. 判断book中的bookId是否为空
        if (book.getBookId() == null || "".equals(book.getBookId()) || book.getBookId() == 0) {
            //说明bookId为空,那么就是添加图书信息
            //固定设置图书的图片路径
            book.setImgPath("static/uploads/jiaofu.jpg");
            //调用持久层的方法进行添加
            bookDao.insertBook(book);
        } else {
            //说明bookId不为空，那么就是修改图书信息
            //调用持久层的方法进行修改
            bookDao.updateBook(book);
        }
    }

    @Override
    public Book getBookById(Integer bookId) throws Exception{
        return bookDao.selectBookByPrimaryKey(bookId);
    }
}