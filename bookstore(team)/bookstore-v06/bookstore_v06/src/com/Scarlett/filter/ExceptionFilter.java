package com.Scarlett.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ExceptionFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            //跳转到异常页面
            request.getRequestDispatcher("/WEB-INF/pages/error.html").forward(request, resp);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
