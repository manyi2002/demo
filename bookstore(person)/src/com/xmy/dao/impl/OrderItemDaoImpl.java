package com.xmy.dao.impl;

import com.xmy.bean.Book;
import com.xmy.bean.Order;
import com.xmy.dao.BaseDao;
import com.xmy.dao.OrderItemDao;
import com.xmy.bean.Order;
import com.xmy.dao.BaseDao;

import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends BaseDao<Order> implements OrderItemDao {
    @Override
    public void insertOrderItemArr(Object[][] orderItemArrParam) {
        String sql = "insert into bs_order_item (book_name,price,img_path,item_count,item_amount,order_id) values (?,?,?,?,?,?)";
        batchUpdate(sql,orderItemArrParam);
    }

//    public List<Book> selectBookList() throws SQLException {
//        return null;
//    }
}