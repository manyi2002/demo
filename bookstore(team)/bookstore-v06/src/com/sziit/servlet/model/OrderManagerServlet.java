package com.sziit.servlet.model;

import com.sziit.Service.Impl.OrderServiceImpl;
import com.sziit.Service.OrderService;
import com.sziit.bean.Order;
import com.sziit.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/orderManager")
public class OrderManagerServlet extends ModelBaseServlet {
    private OrderService orderService = new OrderServiceImpl();
    //查询所有的图书列表，并且展示到order_manager.html页面
    public void showOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //response.getWriter().write("showOrderList...");
        //查询所有订单列表
        try{
            List<Order> orderlist = orderService.getOrderList();
            int oCount = orderlist.size();
            HttpSession session = request.getSession();
            session.setAttribute("cCount",oCount);
            request.setAttribute("orderList", orderlist);
            processTemplate("manager/order_manager", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到修改页面
    public void toEditOrderPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //获取客户端传入的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        try {
            //根据id查询图书详情
            Order order = orderService.getOrderById(id);
            //将图书信息存储到请求域
            request.setAttribute("order",order);
            processTemplate("manager/order_edit",request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除图书
    public void removeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取要删除的图书的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        //2. 调用业务层的方法根据id删除图书
        try {
            orderService.removeOrder(id);
            //3. 删除成功，重新查询所有图书信息
            response.sendRedirect(request.getContextPath()+"/orderManager?method=showOrderList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //跳转到添加图书页面
    public void toAddOrderPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        processTemplate("manager/order_edit",request,response);
    }
    //添加或者图书信息
    /**
     * 添加或者图书信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void saveOrUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //1. 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //2. 将parameterMap中的数据封装到Order对象
        try {
            Order order = new Order();
            BeanUtils.populate(order,parameterMap);
            orderService.saveOrUpdateOrder(order);
            //保存成功，则重新查询所有图书
            response.sendRedirect(request.getContextPath()+"/orderManager?method=showOrderList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

