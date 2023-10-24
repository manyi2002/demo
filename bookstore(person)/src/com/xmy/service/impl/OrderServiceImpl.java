package com.xmy.service.impl;

import com.xmy.bean.Cart;
import com.xmy.bean.CartItem;
import com.xmy.bean.Order;
import com.xmy.bean.User;
import com.xmy.constants.BookStoreConstants;
import com.xmy.dao.BookDao;
import com.xmy.dao.OrderDao;
import com.xmy.dao.OrderItemDao;
import com.xmy.dao.impl.BookDaoImpl;
import com.xmy.dao.impl.OrderDaoImpl;
import com.xmy.dao.impl.OrderItemDaoImpl;
import com.xmy.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String checkout(Cart cart, User user) throws Exception {

        //1. 往订单表插入一条数据  
        //1.1 生成一个唯一的订单号(使用UUID)  
        String orderSequence = UUID.randomUUID().toString();
        //1.2 生成当前时间createTime
        String createTime = new SimpleDateFormat("dd-MM-yy:HH:mm:ss").format(new Date());
        //1.3 订单的totalCount就是cart的totalCount  
        Integer totalCount = cart.getTotalCount();
        //1.4 订单的totalAmount就是购物车的totalAmount
        Double totalAmount = cart.getTotalAmount();
        //1.5 设置订单的状态为0
        Integer status = BookStoreConstants.ORDER_PAYED;
        //1.6 订单的userId就是user对象的id  
        Integer userId = user.getUserId();
        //将上述六个数据封装到一个Order对象中
        Order order = new Order(null,orderSequence,createTime,totalCount,totalAmount,status,userId);
        //1.7 调用持久层OrderDao的insertOrder方法添加订单数据，并且获取自增长的主键值
        Integer orderId = orderDao.insertOrder(order);

        //2. 往订单项表插入多条数据(采用批处理)
        //获取所有的购物项
        List<CartItem> cartItemList = cart.getCartItemList();
        //创建一个二维数组，用来做批量添加订单项的参数
        Object[][] orderItemArrParam = new Object[cartItemList.size()][6];

        //3. 更新t_book表中对应的书的sales和stock  
        //创建一个二维数组，用来做批量修改图书信息的参数
        Object[][] bookArrParam = new Object[cartItemList.size()][3];

        //遍历出每一个购物项
        for (int i=0;i<cartItemList.size();i++) {
            //封装批量添加订单项的二维数组参数
            //每一个购物项就对应一个订单项
            CartItem cartItem = cartItemList.get(i);
            //2.1 bookName就是CartItem的bookName
            //设置第i条SQL语句的第一个参数的值
            orderItemArrParam[i][0] = cartItem.getBookName();
            //2.2 price、imgPath、itemCount、itemAmount都是CartItem中对应的数据 
            //设置第i条SQL语句的第二个参数的值
            orderItemArrParam[i][1] = cartItem.getPrice();
            //设置第i条SQL语句的第三个参数的值
            orderItemArrParam[i][2] = cartItem.getImgPath();
            //设置第i条SQL语句的第四个参数的值
            orderItemArrParam[i][3] = cartItem.getCount();
            //设置第i条SQL语句的第五个参数的值
            orderItemArrParam[i][4] = cartItem.getAmount();
            //2.3 orderId就是第一步中保存的订单的id
            //设置第i条SQL语句的第六个参数的值
            orderItemArrParam[i][5] = orderId;

            //封装批量更新图书库存和销量的二维数组参数
            // 设置第i条SQL语句的第一个参数: 就是要增加的销量就是cartItem的count
            bookArrParam[i][0] = cartItem.getCount();
            // 设置第i条SQL语句的第二个参数: 就是要减少的库存就是cartItem的count
            bookArrParam[i][1] = cartItem.getCount();
            // 设置第i条SQL语句的第三个参数: 就是要修改的图书的bookId就是cartItem的bookId
            bookArrParam[i][2] = cartItem.getBookId();
        }
        //2.4 调用持久层OrderItemDao的insertOrderItemArr方法进行批量添加
        orderItemDao.insertOrderItemArr(orderItemArrParam);

        //3 调用持久层BookDao的updateBookArr方法进行批量更新
        bookDao.updateBookArr(bookArrParam);

        //4. 返回订单号
        return orderSequence;
    }
}