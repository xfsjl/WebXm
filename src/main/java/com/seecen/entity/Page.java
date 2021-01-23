package com.seecen.entity;

import java.util.ArrayList;

public class Page {
    int page; //当前页数
    int pa; //每页商品数量
    int totalPage; //总页数
    int countPa; //商品数量的总数
    ArrayList<Goods> list; // 挡商品页的集合
    public Page(){}

    public Page(int page, int pa, int totalPage, int countPa, ArrayList<Goods> list) {
        this.page = page;
        this.pa = pa;
        this.totalPage = totalPage;
        this.countPa = countPa;
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public int getPa() {
        return pa;
    }

    public int getTotalPage() {
        if(this.countPa%this.pa==0)
        {
            return this.countPa/this.pa;
        }
        else {
            return this.countPa / this.pa + 1;
        }
    }

    public int getCountPa() {
        return countPa;
    }

    public ArrayList<Goods> getList() {
        return list;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPa(int pa) {
        this.pa = pa;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCountPa(int countPa) {
        this.countPa = countPa;
    }

    public void setList(ArrayList<Goods> list) {
        this.list = list;
    }
}
