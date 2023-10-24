package com.maxine.dao.impl;

import com.maxine.bean.Book;
import com.maxine.dao.BookDao;
import com.maxine.dao.BaseDao;

import java.sql.SQLException;
import java.util.List;


public class BookDaoImpl extends BaseDao<Book> implements BookDao {

    //查询所有图书列表
    @Override
    public Book selectBookByPrimaryKey(Integer bookId) throws SQLException {
        String sql = "select book_id bookId,book_name bookName,author,classify,price,sales,stock,img_path imgPath,category from t_book where book_id=?";
        return getBean(Book.class,sql,bookId);
    }


    //删除图书
    @Override
    public void deleteBook(Integer bookId) throws SQLException {
        String sql = "delete from t_book where book_id=?";
        update(sql,bookId);
    }

    //添加图书信息
    @Override
    public void insertBook(Book book) throws SQLException {
        String sql = "insert into t_book (book_name,author,classify,price,sales,stock,img_path,category) values (?,?,?,?,?,?,?,?)";
        update(sql,book.getBookName(),book.getAuthor(),book.getClassify(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath());
    }


    //修改图书信息
    @Override
    public void updateBook(Book book) throws SQLException {
        //我们暂时不修改图片路径
        String sql = "update t_book set book_name=?,price=?,author=?,classify=?,sales=?,stock=? where book_id=?";

        update(sql,book.getBookName(),book.getPrice(),book.getAuthor(),book.getClassify(),book.getSales(),book.getStock(),book.getBookId());
    }
    @Override
    public List<Book> selectBookList() throws SQLException {
        String sql = "select book_id bookId,book_name bookName,author,classify,price,sales,stock,img_path imgPath from t_book";

        return getBeanList(Book.class,sql);
    }

    @Override
    public void updateBookArr(Object[][] bookArrParam) {
        String sql = "update t_book set sales=sales+?,stock=stock-? where book_id=?";
        batchUpdate(sql,bookArrParam);
    }

}
