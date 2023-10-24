package com.sziit.DAO.Impl;

import com.sziit.DAO.BaseDAO;
import com.sziit.DAO.OrderItemDAO;
import com.sziit.Utils.JDBCUtil;
import com.sziit.bean.Order;
import com.sziit.bean.OrderItem;

import java.sql.*;
import java.util.List;

public class OrderItemDAOImpl extends BaseDAO<OrderItem> implements OrderItemDAO {
    @Override
    public void insertOrderItemArr(Object[][] orderItemArrParam) {
        String sql = "insert into t_order_item (book_name,price,img_path,item_count,item_amount,order_id) values (?,?,?,?,?,?)";
        batchUpdate(sql,orderItemArrParam);
    }

    @Override
    public Integer insertOrder(Order order) throws Exception{
        try{
            String sql= "insert into t_order(order_sequence,create_time,total_count,total_amount,order_status,user_id) values(?,?,?,?,?,?)";
            //因为使用DBUtils执行增删改的SQL语句没法获取自增长的id主键，所以我们只能使用原始的JDBC执行这个添加数据的SQL语句并且获取自增长的id
            //1.获取连接
            Connection conn = JDBCUtil.getConnection();
            //2.预编译SQL语句
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3.设置问好处的参数
            preparedStatement.setObject(1,order.getOrderSequence());
            preparedStatement.setObject(2,order.getCreateTime());
            preparedStatement.setObject(3,order.getTotalCount());
            preparedStatement.setObject(4,order.getTotalAmount());
            preparedStatement.setObject(5,order.getOrderStatus());
            preparedStatement.setObject(6,order.getUserId());
            //4.执行SQL语句
            preparedStatement.executeUpdate();
            //5.获取自增长的主键值
            ResultSet rst = preparedStatement.getGeneratedKeys();
            //因为自增长的主键只有一个值，所以不需要while遍历
            int orderId = 0;
            if(rst.next()){
                orderId = rst.getInt(1);
            }
            return orderId;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    //根据订单id以获取详细订单
    public List<OrderItem> findOrderItems(Integer orderId) throws Exception{
        String sql = "select item_id itemId,book_name bookName,price,img_path imgPath,item_count itemCount,item_amount itemAmount from t_order_item where order_id=?";
        List<OrderItem> orderItems= getBeanList(OrderItem.class,sql,orderId);
        return orderItems;
    }

}
