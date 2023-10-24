package com.sziit.servlet.model;

import com.sziit.DAO.BookDAO;
import com.sziit.DAO.Impl.BookDAOImpl;
import com.sziit.Service.BookService;
import com.sziit.Service.Impl.BookServiceImpl;
import com.sziit.Utils.JsonUtils;
import com.sziit.Utils.StringUtil;
import com.sziit.bean.Book;
import com.sziit.bean.CommonResult;
import com.sziit.servlet.base.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PortalServlet  extends ViewBaseServlet {
    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询所有图书列表
        try{
            response.setContentType("GBK");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();
            int pageNo = 1;
            String oper = request.getParameter("oper");
            //如果oper!=null 说明 通过表单的查询按钮点击过来的
            //如果oper是空的，说明 不是通过表单的查询按钮点击过来的
            String keyword = null ;
            if(StringUtil.isNotEmpty(oper) && "search".equals(oper)){
                //说明是点击表单查询发送过来的请求
                //此时，pageNo应该还原为1 ， keyword应该从请求参数中获取
                pageNo = 1 ;
                keyword = request.getParameter("keyword");
                if(StringUtil.isEmpty(keyword)){
                    keyword = "" ;
                }
                session.setAttribute("keyword",keyword);
            }else{
                //说明此处不是点击表单查询发送过来的请求（比如点击下面的上一页下一页或者直接在地址栏输入网址）
                //此时keyword应该从session作用域获取
                String pageNoStr = request.getParameter("pageNo");
                if(StringUtil.isNotEmpty(pageNoStr)){
                    pageNo = Integer.parseInt(pageNoStr);
                }
                Object keywordObj = session.getAttribute("keyword");
                if(keywordObj!=null){
                    keyword = (String)keywordObj ;
                }else{
                    keyword = "" ;
                }
            }
            if (pageNo == 0) {
                String script="<script>alert('当前已是第一页！');location.href='index?pageNo=1'</script>";
                response.getWriter().println(script);
            }
            BookDAO bookDAO = new BookDAOImpl();
            //总记录条数
            int totalCount = bookDAO.getBookCount(keyword);
            //总页数
            int pageCount = (totalCount + 5 - 1) / 5;
            if (pageNo == pageCount+1) {
                String script="<script>alert('当前已是最后一页！');location.href='index?pageNo=1'</script>";
                response.getWriter().println(script);
            }

            //查询所有图书列表
            List<Book> bookList = bookService.getBookList(keyword,pageNo);
            //将图书列表存储到请求域对象

            request.setAttribute("list", bookList);
            session.setAttribute("pageCount", pageCount);
            session.setAttribute("pageNo", pageNo);
            session.setAttribute("totalCount",totalCount);
            //解析模板
            processTemplate("index", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

