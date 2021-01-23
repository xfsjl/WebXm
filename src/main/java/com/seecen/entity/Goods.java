package com.seecen.entity;

public class Goods {
    private int id;
    private String type;
    private String title;
    private double price;
    private String imgurl;
    private String manufacturer;
    private int evaluate;

    public Goods(int id, String type, String title, double price, String imgurl, String manufacturer, int evaluate) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.price = price;
        this.imgurl = imgurl;
        this.manufacturer = manufacturer;
        this.evaluate = evaluate;
    }
    public Goods(){}

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", imgurl='" + imgurl + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", evaluate=" + evaluate +
                '}';
    }
}
