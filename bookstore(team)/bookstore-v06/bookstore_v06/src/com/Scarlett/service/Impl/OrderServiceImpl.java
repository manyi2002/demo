package com.Scarlett.service.Impl;


import com.Scarlett.bean.*;
import com.Scarlett.constants.BookStoreConstants;
import com.Scarlett.dao.BookDao;
import com.Scarlett.dao.Impl.UserDaoImpl;
import com.Scarlett.dao.OrderDao;
import com.Scarlett.dao.OrderItemDao;
import com.Scarlett.dao.Impl.BookDaoImpl;
import com.Scarlett.dao.Impl.OrderDaoImpl;
import com.Scarlett.dao.Impl.OrderItemDaoImpl;
import com.Scarlett.dao.UserDao;
import com.Scarlett.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private Integer userId;

    @Override
    public String checkout(Cart cart, User user, Admin admin) throws Exception{
        //1.往订单表里插入一条数据
        //1.1生成一个唯一的订单号（使用UUID）
        String orderSequence = UUID.randomUUID().toString();
        //1.2生成当前时间createTime
        String createTime = new SimpleDateFormat("dd-MM-yy:HH:mm:ss").format(new Date());
        //1.3订单的totalCount就是cart的totalCount,应该是购物车的总商品数目
        Integer totalCount = cart.getTotalCount();
        //1.4订单的totalAmount就是购物车的totalAmount，购物车的总金额
        Double totalAmount = cart.getTotalAmount();
        //1.5设置订单的状态为0
        Integer status = BookStoreConstants.ORDER_PAYED;
        //1.6订单的userId就是user对象的id，订单所属用户
        //根据用户名找用户id，因为刚注册要先存数据库才有用户id，所以要获取得先去数据库查找，而且因为用户名不能重复才可以这样做。
        user = userDao.findByUsername(user.getUserName());
        userId = user.getUserId();
        if(userId==null){
            userId = admin.getAdminId();
        }
        //将上述六个数据封装到一个Order对象中
        Order order = new Order(null,orderSequence,createTime,totalCount,totalAmount,status,userId);
        //1.7调用持久层OrderDao的insertOrder方法添加订单数据，并且获取自增长的主键值
        Integer orderId = orderDao.insertOrder(order);

        //2.往订单项表里插入多条数据（采用批量处理）
        //获取所有的购物项
        List<CartItem> cartItemList = cart.getCartItemList();
        //创建一个二维数组，用来做批量添加订单项的参数
        Object[][] orderItemArrParam = new Object[cartItemList.size()][6];

        //3.更新t_book表中对应的书的sales和stock
        //创建一个二维数组，用来做批量修改图书信息的参数
        Object[][] bookArrParam = new Object[cartItemList.size()][3];

        //遍历出每一个购物项
        for(int i=0;i<cartItemList.size();i++){
            //封装批量添加订单项的二维数组参数
            //每一个购物项就对应一个订单项
            CartItem cartItem = cartItemList.get(i);
            //2.1bookName就是CartItem的bookName
            //设置第i条SQL语句的第一个参数的值
            orderItemArrParam[i][0] = cartItem.getBookName();
            //2.2 price,imgPath,itemCount,itemAmount都是CartItem中对应的数据
            //设置第i条SQL语句的第二个参数的值
            orderItemArrParam[i][1] = cartItem.getPrice();
            //设置第i条SQL语句的第三个参数的值
            orderItemArrParam[i][2] = cartItem.getImgPath();
            //设置第i条SQL语句的第四个参数的值
            orderItemArrParam[i][3] = cartItem.getCount();
            //设置第i条SQL语句的第五个参数的值
            orderItemArrParam[i][4] = cartItem.getAmount();
            //2.3orderId就是第一步中保存的订单的id
            //设置第i条SQL语句的第六个参数的值
            orderItemArrParam[i][5] = orderId;
            //封装批量更新图书库存和销量的二维数组参数
            //设置第i条SQL语句的第一个参数：就是要增加的销量就是cartItem的count
            bookArrParam[i][0] = cartItem.getCount();
            //设置第i条SQL语句的第二个参数：就是要减少的库存就是cartItem的count
            bookArrParam[i][1] = cartItem.getCount();
            //设置第i条SQL语句的第三个参数：就是要修改的图书的bookId就是cartItem的bookId
            bookArrParam[i][2] = cartItem.getBookId();
        }
        //2.4调用持久层OrderItemDao的insertOrderItemArr方法进行批量添加
        orderItemDao.insertOrderItemArr(orderItemArrParam);

        //3.调用持久层BookDao的updateBookArr方法进行批量更新
        bookDao.updateBookArr(bookArrParam);

        //4.返回订单号
        return orderSequence;
    }

    //根据用户id查找用户订单
    public List<Order> findUserOrder(Integer userId,Integer currentPageNo,Integer pageSize) throws Exception{
        return orderDao.findUserOrder(userId,currentPageNo,pageSize);
    }
    //（管理员有权限）查找所有用户订单
    public List<Order> findAllOrder(Integer currentPageNo,Integer pageSize) throws Exception{
        return orderDao.findAllOrder(currentPageNo,pageSize);
    }
    //根据订单id以获取详细订单
    public List<OrderItem> findOrderItem(Integer orderId) throws Exception{
        return orderItemDao.findOrderItems(orderId);
    }
    //根据订单id删除订单
    @Override
    public void removeOrder(Integer orderId) throws Exception {
        orderDao.removeOrder(orderId);
    }
    //根据订单id查找对应订单
    @Override
    public Order getOrderById(Integer orderId) throws Exception {
        return orderDao.findOrderById(orderId);
    }

    //获取订单的总数目

    @Override
    public long getOrderCount(User user) throws Exception {
        return orderDao.getOrderCount(user);
    }
}
