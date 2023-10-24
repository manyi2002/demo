package com.sziit.DAO.Impl;

import com.sziit.DAO.BaseDAO;
import com.sziit.DAO.BookDAO;
import com.sziit.bean.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

    @Override
    public List<Book> selectBookList(String keyword, Integer pageNo) throws SQLException {
        return getBeanList(Book.class, "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath,category from t_book where book_name like ? or author like ? limit ?,5", "%" + keyword + "%", "%" + keyword + "%", (pageNo - 1) * 5);
    }

    @Override
    public void deleteBook(Integer bookId) throws SQLException {
        String sql = "delete from t_book where book_id=?";
        update(sql, bookId);
    }

    @Override
    public void insertBook(Book book) throws SQLException {
        String sql = "insert into t_book (book_name,author,price,sales,stock,img_path,category) values (?,?,?,?,?,?,?)";
        update(sql, book.getBookName(), book.getAuthor(), book.getPrice(), book.getSales(), book.getStock(), book.getImgPath(), book.getCategory());
    }

    @Override
    public Book selectBookByPrimaryKey(Integer bookId) throws SQLException {
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath,category from t_book where book_id=?";
        return getBean(Book.class, sql, bookId);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        String sql = "update t_book set book_name=?,price=?,author=?,sales=?,stock=?,category=? where book_id=?";

        update(sql, book.getBookName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getCategory(), book.getBookId());
    }

    @Override
    public void updateBookArr(Object[][] bookArrParam) {
        String sql = "update t_book set sales=sales+?,stock=stock-? where book_id=?";
        batchUpdate(sql, bookArrParam);
    }

    @Override
    public int getBookCount(String keyword) {
        return getBeanList(Book.class, "select book_id bookId,book_name bookName,author,price,sales,stock,img_path from t_book where book_name like ? or author like ?", "%" + keyword + "%", "%" + keyword + "%").size();
    }

    @Override
    public List<Book> selectBookByPrice(Double price1, Double price2) throws Exception {
        //select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath from t_book where price between ? and ?";
        return getBeanList(Book.class, sql, price1, price2);
    }

    @Override
    public int getBookCountOnSelectByPrice(Double price1, Double price2) {
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath from t_book where price between ? and ? ";
        List<Book> books = getBeanList(Book.class, sql, price1, price2);
        return books.size();
    }

    @Override
    public List<Book> selectBookByName(String name) throws Exception {
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath from t_book where book_name like ?";
        return getBeanList(Book.class, sql, "%" + name + "%");
    }

    @Override
    public List<Book> selectBookByDoubleCategory(String cate1, String cate2) throws Exception {
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath from t_book where category like ? or category like ?";
        return getBeanList(Book.class, sql, "%" + cate1 + "%", "%" + cate2 + "%");
    }

    @Override
    public List<Book> selectBookByCategory(String cate) throws Exception {
        String sql = "select book_id bookId,book_name bookName,author,price,sales,stock,img_path imgPath from t_book where category like ?";
        return getBeanList(Book.class, sql, "%" + cate + "%");
    }

}
