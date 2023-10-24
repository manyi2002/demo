package com.sziit.filter;

import com.sziit.Utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;


public class CloseConnectionFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            JDBCUtil.releaseConnection();
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
