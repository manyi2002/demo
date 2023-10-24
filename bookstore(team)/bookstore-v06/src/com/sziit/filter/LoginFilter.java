package com.sziit.filter;
import com.sziit.Utils.JsonUtils;
import com.sziit.bean.CommonResult;
import com.sziit.bean.User;
import com.sziit.constants.BookStoreConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1. 判断用户是否已登录:就是判断session中是否存储了User对象
        //将req强转成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
        if (user == null) {
            //当前未登录
            //想办法让用户跳转到登录页面login.html
            //获取请求参数method的值
            String method = request.getParameter("method");
            //1. 如果是同步请求:method=toCartPage、method=cleanCart
            if (method.equals("toCartPage") || method.equals("cleanCart") || method.equals("checkout") || method.equals("toManagerPage")) {
                //直接重定向跳转到UserServlet调用toLoginPage方法
                response.sendRedirect(request.getContextPath()+"/user?method=toLoginPage");
            }else {
                //2. 其它的都是异步请求
                CommonResult commonResult = CommonResult.error().setMessage("unLogin");
                JsonUtils.writeResult(response,commonResult);
            }
            return;
        }

        //如果没有走到那个if判断，说明当前已登录，那么就直接放行
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
