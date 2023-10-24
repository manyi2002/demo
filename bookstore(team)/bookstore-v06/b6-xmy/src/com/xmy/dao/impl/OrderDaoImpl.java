package com.xmy.dao.impl;

import com.xmy.bean.Book;
import com.xmy.bean.Order;
import com.xmy.dao.BaseDao;
import com.xmy.dao.OrderDao;
import com.xmy.utils.JDBCUtil;

import java.sql.*;
import java.util.List;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    @Override
    public Integer insertOrder(Order order) throws Exception {
        try{
            String sql = "insert into bs_order (order_sequence,create_time,total_count,total_amount,order_status,user_id) values (?,?,?,?,?,?)";
            //因为使用DBUtils执行增删改的SQL语句没法获取自增长的id主键，所以我们只能使用原始的JDBC执行这个添加数据的SQL语句并且获取自增长的id
            //1. 获取连接
            Connection conn = JDBCUtil.getConnection();
            //2. 预编译SQL语句
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3. 设置问号处的参数
            preparedStatement.setObject(1, order.getOrderSequence());
            preparedStatement.setObject(2, order.getCreateTime());
            preparedStatement.setObject(3, order.getTotalCount());
            preparedStatement.setObject(4, order.getTotalAmount());
            preparedStatement.setObject(5, order.getOrderStatus());
            preparedStatement.setObject(6, order.getUserId());
            //4. 执行SQL语句
            preparedStatement.executeUpdate();
            //5. 获取自增长的主键值
            ResultSet rst = preparedStatement.getGeneratedKeys();
            //因为自增长的主键只有一个值，所以不需要while循环遍历
            int orderId = 0;
            if (rst.next()) {
                orderId = rst.getInt(1);
            }
            return orderId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Order> selectOrderList() throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from bs_order";
        return getBeanList(Order.class,sql);
    }

    @Override
    public void deleteOrder(Integer orderId) throws SQLException {
        String sql = "delete from bs_order where order_id=?";
        update(sql,orderId);
    }

    @Override
    public Order selectOrderByPrimaryKey(Integer orderId) throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from bs_order where order_id=?";
        return getBean(Order.class,sql,orderId);
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        //我们暂时不修改图片路径
        String sql = "update bs_order set order_sequence=?,total_count=?,total_amount=?,order_status=? where order_id=?";

        update(sql,order.getOrderSequence(),order.getTotalCount(),order.getTotalAmount(),order.getOrderStatus(),order.getOrderId());
    }
}
