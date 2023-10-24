package com.sziit.servlet.model;

import com.sziit.DAO.BookDAO;
import com.sziit.DAO.Impl.BookDAOImpl;
import com.sziit.Service.BookService;
import com.sziit.Service.Impl.BookServiceImpl;
import com.sziit.Utils.StringUtil;
import com.sziit.bean.Book;
import com.sziit.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/protected/bookManager")
public class BookManagerServlet extends ModelBaseServlet {
    private BookService bookService = new BookServiceImpl();
    //查询所有的图书列表，并且展示到book_manager.html页面
    public void showBookList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //response.getWriter().write("showBookList...");
        //查询所有图书列表
        try{
            int pageNo = 1;
            HttpSession session = request.getSession();
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
                String script="<script>alert('当前已是第一页！');location.href='bookManager?method=showBookList&pageNo=1'</script>";
                response.getWriter().println(script);
            }
            BookDAO bookDAO = new BookDAOImpl();
            //总记录条数
            int totalCount = bookDAO.getBookCount(keyword);
            //总页数
            int pageCount = (totalCount + 5 - 1) / 5;
            if (pageNo == pageCount+1) {
                String script="<script>alert('当前已是最后一页！');location.href='bookManager?method=showBookList&pageNo=1'</script>";
                response.getWriter().println(script);
            }
            List<Book> booklist = bookService.getBookList(keyword,pageNo);
            request.setAttribute("bookList", booklist);
            session.setAttribute("pageCount", pageCount);
            session.setAttribute("pageNo", pageNo);
            session.setAttribute("totalCount",totalCount);
            processTemplate("manager/book_manager", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除图书
    public void removeBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取要删除的图书的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        //2. 调用业务层的方法根据id删除图书
        try {
            bookService.removeBook(id);
            //3. 删除成功，重新查询所有图书信息
            response.sendRedirect(request.getContextPath()+"/protected/bookManager?method=showBookList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //跳转到添加图书页面
    public void toAddPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        processTemplate("manager/book_edit",request,response);
    }

    //添加或者修改图书信息
    public void saveOrUpdateBook(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //1. 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //2. 将parameterMap中的数据封装到Book对象
        try {
            Book book = new Book();
            BeanUtils.populate(book,parameterMap);
            bookService.saveOrUpdateBook(book);
            //保存成功，则重新查询所有图书
            response.sendRedirect(request.getContextPath()+"/protected/bookManager?method=showBookList");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //跳转到修改页面
    public void toEditPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //获取客户端传入的id
        Integer id = Integer.valueOf(request.getParameter("id"));
        try {
            //根据id查询图书详情
            Book book = bookService.getBookById(id);
            //将图书信息存储到请求域
            request.setAttribute("book",book);
            processTemplate("manager/book_edit",request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
