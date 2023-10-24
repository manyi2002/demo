package com.sziit.servlet.model;

import com.sziit.DAO.BookDAO;
import com.sziit.DAO.Impl.BookDAOImpl;
import com.sziit.Service.BookService;
import com.sziit.Service.Impl.BookServiceImpl;
import com.sziit.Utils.StringUtil;
import com.sziit.bean.Book;
import com.sziit.servlet.base.ModelBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/query")
public class QueryServlet extends ModelBaseServlet {
    private BookService bookService = new BookServiceImpl();

     public void selectByPrice(HttpServletRequest request, HttpServletResponse response) throws IOException{
         BookDAO bookDAO = new BookDAOImpl();
         //int pNo = 1;
         //String pageNoStr = request.getParameter("pNo");

         //从客户端传入的price1,price2
         String price1str = request.getParameter("price1");
         String price2str = request.getParameter("price2");
         if(price1str == null || price2str == null){
             String script="<script>alert('请输入价格！');location.href='index'</script>";
             response.getWriter().println(script);
         }
         double price1 = Double.valueOf(price1str);
         double price2 = Double.valueOf(price2str);

         //总记录条数
         int tCount = bookDAO.getBookCountOnSelectByPrice(price1,price2);
         //总页数
         //int pCount = (tCount + 5 - 1) / 5;

         /*
         if (pNo == 0) {
             String script="<script>alert('当前已是第一页！');location.href='query?pNo=1'</script>";
             response.getWriter().println(script);
         }
         if (pNo == pCount+1) {
             String script="<script>alert('当前已是最后一页！');location.href='query?pNo=1'</script>";
             response.getWriter().println(script);
         }
         */
         HttpSession session = request.getSession();
         try{
             List<Book> typeList = bookService.selectBookByDoublePrice(price1,price2);
             request.setAttribute("typeList",typeList);
             session.setAttribute("tCount",tCount);
             //查询成功，跳转页面
             processTemplate("type/type",request,response);
         }catch (Exception e){
             e.printStackTrace();
         }
     }

     public void selectByName(HttpServletRequest request,HttpServletResponse response) throws Exception {
         String name = request.getParameter("name");
         List<Book> resultList = bookService.selectBookByBookName(name);
         int Count = resultList.size();
         //System.out.println(resultList);
         //System.out.println(Count);
         HttpSession session = request.getSession();
         request.setAttribute("resultList",resultList);
         session.setAttribute("Count",Count);
         processTemplate("type/result",request,response);
     }

     public void selectByCate(HttpServletRequest request,HttpServletResponse response) throws Exception {
         request.setCharacterEncoding("UTF-8");
         String cate1 = request.getParameter("cate1");
         String cate2 = request.getParameter("cate2");
         List<Book> resultList = null;
         if(cate2 == null) {
             resultList = bookService.selectBookByCategory(cate1);
         }
         else {
             resultList = bookService.selectBookByDoubleCategory(cate1,cate2);
         }

         int Count = resultList.size();
         System.out.println(resultList);
         System.out.println(Count);
         HttpSession session = request.getSession();
         request.setAttribute("resultList",resultList);
         session.setAttribute("Count",Count);
         processTemplate("type/result",request,response);

     }
}
