package com.Scarlett.servlet.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.Scarlett.bean.*;
import com.Scarlett.constants.BookStoreConstants;
import com.Scarlett.dao.Impl.OrderDaoImpl;
import com.Scarlett.dao.Impl.UserDaoImpl;
import com.Scarlett.dao.OrderDao;
import com.Scarlett.dao.UserDao;
import com.Scarlett.service.Impl.UserServiceImpl;
import com.Scarlett.service.OrderService;
import com.Scarlett.service.Impl.OrderServiceImpl;
import com.Scarlett.service.UserService;
import com.Scarlett.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.http.HttpSession;

public class OrderClientServlet extends ModelBaseServlet {
    private OrderService orderService = new OrderServiceImpl();
    private OrderDao orderDao = new OrderDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private UserService userService = new UserServiceImpl();
    private User user1;
    private Integer userId;
    private String orderSequence;
    //订单结算
    public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1.从session中获取购物车信息
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //2.从session中获取普通用户信息
            User user = (User) session.getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
            User register = (User) session.getAttribute(BookStoreConstants.REGISTERUSER_SESSIONKEY);
            System.out.println("The register is:"+register);
            Admin admin = (Admin) session.getAttribute(BookStoreConstants.LOGINADMIN_SESSIONKEY);
            //如果是普通用户或刚注册上的或管理员就可以去结算
            if (user != null || register != null || admin != null) {
                System.out.println("this is a user:" + user);
                //3.调用业务层的方法，进行订单结算，并且获取订单的序列号
                if(user!=null){
                    orderSequence = orderService.checkout(cart, user,admin);
                }else if(register!=null){
                    orderSequence = orderService.checkout(cart, register,admin);

                }else if(admin!=null){
                    orderSequence = orderService.checkout(cart,user,admin);
                }

                //4.清空购物车
                session.removeAttribute(BookStoreConstants.CART_SESSIONKEY);
                //5.将订单序列号存储到请求域对象中，并且跳转到checkout.html页面

                request.setAttribute("orderSequence", orderSequence);
                processTemplate("cart/checkout", request, response);
            }
        } catch(Exception e){
            e.printStackTrace();
            processTemplate("error", request, response);
            throw new RuntimeException(e.getMessage());
        }

    }
    //订单管理
    public void orderManage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //第一次走这个请求,一定是第一页,且页面大小是固定的
            int pageSize = 10;//设置页面显示的数据记录条数为10
            int currentPageNo = 1;//设置当前页的序号 为1
            //获取前端参数
            String pageIndex = request.getParameter("pageIndex");//当前页号
            String inputPage = request.getParameter("inputPage");
            if (pageIndex == null) {
                pageIndex = "";

            }else{
                currentPageNo = Integer.parseInt(pageIndex);//给当前字符串形式的页号转换为Integer形式
            }
            if(inputPage == null){
                inputPage = "";
            }else if((!(inputPage.equals("")))){
                //意味着有输入跳转的页面
                currentPageNo = Integer.parseInt(inputPage);
            }

            //1.从session中获取购物车信息
            HttpSession session = request.getSession();
            //2.从session中获取用户信息
            User user = (User) session.getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
            User register = (User) session.getAttribute(BookStoreConstants.REGISTERUSER_SESSIONKEY);
            Admin admin = (Admin) session.getAttribute(BookStoreConstants.LOGINADMIN_SESSIONKEY);

            //3订单的userId就是user对象的id，订单所属用户
            if(user!=null){
                userId = user.getUserId();
                user1=userService.findByUserId(userId);
            }else if(register!=null){
                //根据用户名找用户id，因为刚注册要先存数据库才有用户id，所以要获取得先去数据库查找，而且因为用户名不能重复才可以这样做。
                register = userDao.findByUsername(register.getUserName());
                userId = register.getUserId();
                user1=userService.findByUserId(userId);
            }else if(admin!=null){
                user1=null;
            }


            //初始化订单列表
            List<Order> order = null;
            List<Order> orders = null;
            //当前用户不为空，说明是用户不是管理员
            if(user1!=null) {
                //查询列表记录总条数
                int orderCount = (int) orderService.getOrderCount(user1);
                //分页工具类的支持
                PageSupport pageSupport = new PageSupport();
                pageSupport.setCurrentPageNo(currentPageNo);//设置当前页号
                pageSupport.setPageSize(pageSize);//设置页面大小
                pageSupport.setTotalCount(orderCount);//设置图书数据记录的总条数
                //获取分成的 页面的 总页数
                int totalPageCount = pageSupport.getTotalPageCount();
                //将数据记录的总条数信息传递给前端
                request.setAttribute("totalCount",orderCount);
                //将分成的总页数信息通过请求域对象传递给前端
                request.setAttribute("totalPageCount",totalPageCount);
                //传递给前端
                request.setAttribute("currentPageNo",currentPageNo);
                request.setAttribute("inputPage",inputPage);
                //4根据用户的id从数据库的订单表查找相对于的订单
                order = orderService.findUserOrder(userId,currentPageNo,pageSize);
                //5.将订单序列号存储到请求域对象中，并且跳转到checkout.html页面
                request.setAttribute("list_order", order);
                processTemplate("order/order", request, response);
            }else if(user1==null){
                //查询列表记录总条数
                int orderCount = (int) orderService.getOrderCount(user1);
                //分页工具类的支持
                PageSupport pageSupport = new PageSupport();
                pageSupport.setCurrentPageNo(currentPageNo);//设置当前页号
                pageSupport.setPageSize(pageSize);//设置页面大小
                pageSupport.setTotalCount(orderCount);//设置图书数据记录的总条数
                //获取分成的 页面的 总页数
                int totalPageCount = pageSupport.getTotalPageCount();
                //将数据记录的总条数信息传递给前端
                request.setAttribute("totalCount",orderCount);
                //将分成的总页数信息通过请求域对象传递给前端
                request.setAttribute("totalPageCount",totalPageCount);
                //传递给前端
                request.setAttribute("currentPageNo",currentPageNo);
                request.setAttribute("inputPage",inputPage);
                //4根据用户的id从数据库的订单表查找相对于的订单
                order = orderService.findUserOrder(userId,currentPageNo,pageSize);
                //反之为管理员，能查看所有用户订单
                orders = orderService.findAllOrder(currentPageNo,pageSize);
                List<Order> orders1 = new ArrayList<Order>();
                for(Order order1: orders){
                    Integer orderId = order1.getOrderId();
                    order1 = orderService.getOrderById(orderId);
                    Integer orderuserId = order1.getUserId();
                    User user1 = userService.findByUserId(orderuserId);
                    String userName = user1.getUserName();
                    order1.setUserName(userName);
                    orders1.add(order1);
                }
                //5.将订单序列号存储到请求域对象中，并且跳转到checkout.html页面
                request.setAttribute("list_order", orders1);
                processTemplate("order/managerOrder", request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            processTemplate("error",request,response);
            throw new RuntimeException(e.getMessage());
        }

    }
    //订单详细信息
    public void detailInformation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1.获取订单id用于查看订单详细信息
            Integer orderId = Integer.valueOf(request.getParameter("orderId"));
            System.out.println(orderId);
            //2.用id去查询数据库
            List<OrderItem> orderItems = orderService.findOrderItem(orderId);
            System.out.println(orderItems);
            //3.将具体订单放在list_order用于前端获取
            request.setAttribute("list_orderItems", orderItems);
            processTemplate("order/orderdetail", request, response);

        } catch (Exception e) {
            e.printStackTrace();
            processTemplate("error",request,response);
            throw new RuntimeException(e.getMessage());
        }
    }

    //tao的部分
    //删除订单
    public void removeOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //获取要删除订单号的id
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        try {
            orderService.removeOrder(orderId);
            //删除成功，重新加载页面
            response.sendRedirect(request.getContextPath() + "/protected/orderClient?method=orderManage");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //用户点击修改跳转到修改页面，此处为跳转修改页面
    public void toEditPage1(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        try {
            System.out.println("前端："+orderId);
            Order order = orderService.getOrderById(orderId);
            Integer orderuserId = order.getUserId();
            System.out.println("用户id："+orderuserId);
            User user = userService.findByUserId(orderuserId);
            String userName = user.getUserName();
            order.setUserName(userName);
            System.out.println(order);
            //将订单信息存储在请求域
            request.setAttribute("order", order);
            processTemplate("order/order_edit1", request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //修改订单
    public void saveOrUpdateOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //请求获得参数
        Map<String,String[]> parameterMap = request.getParameterMap();
        //2、将参数封装到order对象中
        //2、将parameterMap中的数据封装到Book对象
        try{
            Order order = new Order();
            BeanUtils.populate(order,parameterMap);
            System.out.println("修改成功后的order："+order);
            orderService.saveOrUpdateOrder(order);
            //保存成功，则重新查询所有书籍
            response.sendRedirect(request.getContextPath()+"/protected/orderClient?method=orderManage");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
