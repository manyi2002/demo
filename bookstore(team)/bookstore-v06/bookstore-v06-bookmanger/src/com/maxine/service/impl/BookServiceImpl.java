package com.maxine.service.impl;


import com.maxine.bean.Book;
import com.maxine.dao.BookDao;
import com.maxine.dao.impl.BookDaoImpl;
import com.maxine.service.BookService;

import java.sql.SQLException;
import java.util.List;


public class BookServiceImpl implements BookService {
    private BookDao bookDao= new BookDaoImpl();
    @Override
    //查询所有图书列表
    public List<Book> getBookList() throws Exception{
        return bookDao.selectBookList();
    }
    @Override
    //删除图书
    public void removeBook(Integer bookId) throws Exception {
        bookDao.deleteBook(bookId);
    }
    @Override
    //添加图书信息
    public void saveOrUpdateBook(Book book) throws SQLException {
        //判断book中的bookId是否为空
        if (book.getBookId() == null || "".equals(book.getBookId()) || book.getBookId() == 0) {
            //说明bookId为空,那么就是添加图书信息
            //固定设置图书的图片路径
            book.setImgPath("static/uploads/jiaofu.jpg");
            //调用持久层的方法进行添加
            bookDao.insertBook(book);
        }else {
            //说明bookId不为空，那么就是修改图书信息
            //调用持久层的方法进行修改
            bookDao.updateBook(book);
        }
    }

    //修改图书信息
    @Override
    public Book getBookById(Integer bookId) throws Exception{
        return bookDao.selectBookByPrimaryKey(bookId);
    }

}
