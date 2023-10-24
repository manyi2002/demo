package com.sziit.Service.Impl;

import com.sziit.DAO.BookDAO;
import com.sziit.DAO.Impl.BookDAOImpl;
import com.sziit.Service.BookService;
import com.sziit.bean.Book;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDAO bookDao = new BookDAOImpl();

    @Override
    public List<Book> getBookList(String keyword,Integer pageNo) throws Exception{
        return bookDao.selectBookList(keyword,pageNo);
    }

    @Override
    public void removeBook(Integer bookId) throws Exception {
        bookDao.deleteBook(bookId);
    }

    @Override
    public void saveOrUpdateBook(Book book) throws Exception {
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
    @Override
    public Book getBookById(Integer bookId) throws Exception{
        return bookDao.selectBookByPrimaryKey(bookId);
    }

    @Override
    public List<Book> selectBookByDoublePrice(Double price1, Double price2) throws Exception {
        return bookDao.selectBookByPrice(price1,price2);
    }

    @Override
    public List<Book> selectBookByBookName(String name) throws Exception {
        return bookDao.selectBookByName(name);
    }

    @Override
    public List<Book> selectBookByDoubleCategory(String cate1,String cate2) throws Exception {
        return bookDao.selectBookByDoubleCategory(cate1, cate2);
    }

    @Override
    public List<Book> selectBookByCategory(String cate) throws Exception {
        return bookDao.selectBookByCategory(cate);
    }



}
