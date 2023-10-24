package com.xmy.bean;

import com.xmy.constants.BookStoreConstants;
import com.xmy.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartItem {
    /**
     * 购物项存储的那本书的id
     */
    private Integer bookId;
    /**
     * 购物项存储的那本书的书名
     */
    private String bookName;
    /**
     * 购物项存储的那本书的图片路径
     */
    private String imgPath;
    /**
     * 购物项存储的那本书的单价
     */
    private Double price;
    /**
     * 购物项的书的数量
     */
    private Integer count = 0;
    /**
     * 购物项的金额
     */
    private Double amount = 0d;

    public CartItem(Integer bookId, String bookName, String imgPath, Double price, Integer count, Double amount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.imgPath = imgPath;
        this.price = price;
        this.count = count;
        this.amount = amount;
    }

    public CartItem() {
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    //这个方法获取总价:要通过计算才能获取
    public Double getAmount() {
        //1. 将price和count封装成BigDecimal类型
        BigDecimal bigDecimalPrice = new BigDecimal(price + "");
        BigDecimal bigDecimalCount = new BigDecimal(count + "");

        //2. 使用bigDecimal的方法进行乘法
        this.amount = bigDecimalCount.multiply(bigDecimalPrice).doubleValue();
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }

    /**
     * 将count自增1
     */
    public void countIncrease(){
        this.count ++;
    }

    /**
     * 将当前购物项的数量进行 -1
     */
    public void countDecrease(){
        if (this.count > 0) {
            this.count --;
        }
    }

    //获取购物车的数据
    public void getCartJSON(HttpServletRequest request, HttpServletResponse response) {
        CommonResult commonResult = null;
        try {
            //1. 获取购物车信息
            Cart cart = (Cart) request.getSession().getAttribute(BookStoreConstants.CART_SESSIONKEY);
            //2. 创建一个Map用于封装客户端需要的数据
            Map responseMap = new HashMap();
            if (cart != null){
                responseMap.put("totalCount",cart.getTotalCount());
                responseMap.put("totalAmount",cart.getTotalAmount());
                //3. 获取cart中的所有的购物项:返回一个List<CartItem>
                responseMap.put("cartItemList",cart.getCartItemList());
            }
            //4. 将responseMap存储到CommonResult对象中
            commonResult = CommonResult.success().setResultData(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage(e.getMessage());
        }
        //将commonResult对象转成Json字符串输出到客户端
        JsonUtils.writeResult(response,commonResult);
    }
}