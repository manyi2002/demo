package com.xmy.filter;

import com.xmy.bean.CommonResult;
import com.xmy.bean.User;
import com.xmy.constants.BookStoreConstants;
import com.xmy.utils.JsonUtils;

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
        //1. �ж��û��Ƿ��ѵ�¼:�����ж�session���Ƿ�洢��User����
        //��reqǿת��HttpServletRequest����
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User user = (User) request.getSession().getAttribute(BookStoreConstants.LOGINUSER_SESSIONKEY);
        if (user == null) {
            //��ǰδ��¼
            //��취���û���ת����¼ҳ��login.html
            //��ȡ�������method��ֵ
            String method = request.getParameter("method");
            //1. �����ͬ������:method=toCartPage��method=cleanCart
            if (method.equals("toCartPage") || method.equals("cleanCart") || method.equals("checkout")) {
                //ֱ���ض�����ת��UserServlet����toLoginPage����
                response.sendRedirect(request.getContextPath()+"/user?method=toLoginPage");
            }else {
                //2. �����Ķ����첽����
                CommonResult commonResult = CommonResult.error().setMessage("unLogin");
                JsonUtils.writeResult(response,commonResult);
            }
            return;
        }

        //���û���ߵ��Ǹ�if�жϣ�˵����ǰ�ѵ�¼����ô��ֱ�ӷ���
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}