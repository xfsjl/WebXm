package com.seecen.entity;

public class GoShop {
    private int id; //id
    private int userid;
    private String imgurl; //图片路径
    private String title;
    private double price;
    private int count;

    public GoShop() {

    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public GoShop(int id, int userid, String imgurl, String title, double price, int count) {
        this.id = id;
        this.userid = userid;
        this.imgurl = imgurl;
        this.title = title;
        this.price = price;
        this.count=count;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
