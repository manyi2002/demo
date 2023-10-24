package com.sziit.Utils;

//将分页相关代码封装成工具类
public class PageSupport {
    //当前页码来自于用户的输入
    private int currentPageNo = 1; //用户输入的当前页码
    //数据表中记录的总数量
    private int totalCount = 0;
    //一个页面容纳的记录数
    private int pageSize = 0;
    //展示的总页数
    private int totalPageCount = 1;

    //返回用户当前输入的页码
    public int getCurrentPageNo() {
        return currentPageNo;
    }

    //将用户输入的页码作为当前页码
    public void setCurrentPageNo(int currentPageNo) {
        //用户输入的页码数 需>0才行
        if(currentPageNo>0){
            this.currentPageNo = currentPageNo;
        }
    }

    //返回数据的总条数
    public int getTotalCount() {
        return totalCount;
    }

    //设置记录的总条数
    public void setTotalCount(int totalCount) {
        //输入的记录的总条数需要大于0才能设置
        if(totalCount>0){
            this.totalCount = totalCount;
            //调用相关函数, 设置总共的页数
            this.setTotalPageCountByRs();
        }
    }

    //返回一个页面显示的记录数目
    public int getPageSize() {
        return pageSize;
    }

    //设置一个页面显示的记录数目
    public void setPageSize(int pageSize) {
        //输入的一个页面显示的记录条数需>0,才能设置
        if(pageSize>0){
            this.pageSize = pageSize;
        }
    }

    //返回页面的总页数
    public int getTotalPageCount() {
        return totalPageCount;
    }

    //设置页面的总页数
    public void setTotalPageCountByRs(){
        //计算各种情况下的页面总页数
        if(this.totalCount%this.pageSize==0){
            this.totalPageCount = this.totalCount/this.pageSize;
        }else if(this.totalCount%this.pageSize>0){
            this.totalPageCount = this.totalCount/this.pageSize+1;
        }else{
            this.totalCount = 0;
        }
    }
}
