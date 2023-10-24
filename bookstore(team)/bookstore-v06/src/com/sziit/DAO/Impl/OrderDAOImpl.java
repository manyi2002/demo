package com.sziit.DAO.Impl;

import com.sziit.DAO.BaseDAO;
import com.sziit.DAO.OrderDAO;
import com.sziit.Utils.JDBCUtil;
import com.sziit.bean.Order;
import com.sziit.bean.User;

import java.sql.*;
import java.util.List;

public class OrderDAOImpl extends BaseDAO<Order> implements OrderDAO {
    @Override
    public Integer insertOrder(Order order) throws Exception {
        try{
            String sql = "insert into t_order (order_sequence,create_time,total_count,total_amount,order_status,user_id) values (?,?,?,?,?,?)";
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
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from t_order";
        return getBeanList(Order.class,sql);
    }

    @Override
    public void deleteOrder(Integer orderId) throws SQLException {
        String sql = "delete from t_order where order_id=?";
        update(sql,orderId);
    }

    @Override
    public Order selectOrderByPrimaryKey(Integer orderId) throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from t_order where order_id=?";
        return getBean(Order.class,sql,orderId);
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        //我们暂时不修改图片路径
        String sql = "update t_order set order_sequence=?,total_count=?,total_amount=?,order_status=? where order_id=?";

        update(sql,order.getOrderSequence(),order.getTotalCount(),order.getTotalAmount(),order.getOrderStatus(),order.getOrderId());
    }
    //查找用户订单
    public List<Order> findUserOrder(Integer userId, Integer currentPageNo, Integer pageSize) throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus from t_order where user_id=? limit ?,?";
        //更新分页起始下标的参数,第一页对应limit起始下标0,假设每页显示5个记录,则第二行下标为5,以此类推,总结出下标的规律
        currentPageNo = (currentPageNo-1)*pageSize;
//        String sql = "select * from t_order where user_id=?";
        List<Order> orders=getBeanList(Order.class,sql,userId,currentPageNo,pageSize);

        return orders;
    }
    //（管理员有权限）查找所有用户订单
    public List<Order> findAllOrder(Integer currentPageNo,Integer pageSize) throws Exception{
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus from t_order limit ?,?";
        //更新分页起始下标的参数,第一页对应limit起始下标0,假设每页显示5个记录,则第二行下标为5,以此类推,总结出下标的规律
        currentPageNo = (currentPageNo-1)*pageSize;
        List<Order> orders=getBeanList(Order.class,sql,currentPageNo,pageSize);
        return orders;
    }
    //根据订单id删除订单
    @Override
    public void removeOrder(Integer orderId) throws SQLException {
        String sql = "delete from t_order where order_id=?" ;
        update(sql,orderId);
    }
    //根据订单id查找对应订单
    @Override
    public Order findOrderById(Integer orderId) throws SQLException {
        String sql = "select order_id orderId,order_sequence orderSequence,create_time createTime,total_count totalCount,total_amount totalAmount,order_status orderStatus,user_id userId from t_order where order_id = ?";
        return getBean(Order.class,sql,orderId);
    }

    //获取订单的总数目
    @Override
    public long getOrderCount(User user) throws Exception {
        String sql;
        Long quantity=null;
        //用户user是空,则对应管理员,查询所有订单
        if(user == null){
            //管理员
            sql = "select count(1) as count from t_order";
            quantity = count(sql);
        }else{
            //用户User不为空,则对应普通用户,查询特定用户的订单
            Integer userId = user.getUserId();
            sql = "select count(1) as count from t_order where user_id=?";
            quantity = count(sql,userId);
        }
        return quantity;//返回订单总数
    }

}

