package com.seecen.entity;

public class User {
    int id;
    String username;
    String password;
    int shoppingcarid;
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getShoppingcarid() {
        return shoppingcarid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShoppingcarid(int shoppingcarid) {
        this.shoppingcarid = shoppingcarid;
    }
    public User(){}
    public User(int id, String username, String password, int shoppingcarid) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.shoppingcarid = shoppingcarid;
    }


}
