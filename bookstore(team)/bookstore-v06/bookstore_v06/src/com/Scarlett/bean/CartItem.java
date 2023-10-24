package com.Scarlett.bean;

import java.math.BigDecimal;

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

    /**
     * 获取当前购物项的金额
     * @return
     */
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
}