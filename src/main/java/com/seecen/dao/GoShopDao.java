package com.seecen.dao;

import com.seecen.entity.GoShop;
import com.seecen.entity.Goods;
import com.seecen.util.DButil;

import java.awt.dnd.DnDConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoShopDao {
    public void addShop(String id,String userid) throws SQLException, ClassNotFoundException {
        Connection con = DButil.getCon();
        int count=0;
        String sql1 = "select * from XM_GOSHOP where id=? and userid=?";
        String sql2="insert into XM_GOSHOP values(?,?,1)";
        String sql3= "update XM_GOSHOP set countp=? where id=? and userid=?";
        PreparedStatement prep = con.prepareStatement(sql1);
        PreparedStatement prep2 = con.prepareStatement(sql2);
        PreparedStatement prep3 = con.prepareStatement(sql3);
        prep.setString(1,id);
        prep.setString(2,userid);
        ResultSet rs = prep.executeQuery();
        if(rs.next())
        {


                count=rs.getInt("countp");
                System.out.println(count);

            count++;
            prep3.setInt(1,count);
            prep3.setString(2,id);
            prep3.setString(3,userid);
            prep3.executeUpdate();
            con.commit();
        }
        else
        {

            prep2.setString(1,id);
            prep2.setString(2,userid);
            prep2.executeUpdate();
            con.commit();
        }
        DButil.close(rs,prep,prep2,prep3,con);
    }
    public ArrayList<GoShop> findGoodsById(int userid) throws SQLException, ClassNotFoundException {
        ArrayList<GoShop> list = new ArrayList<>();
        Connection con = DButil.getCon();
        String sql1 = "select * from XM_GOODS where id in (select id from XM_GOSHOP where userid=? )";
        String sql2 = "select * from XM_GOSHOP where id=? and userid=?";
       // String sql = "select id from XM_GOSHOP where userid = ?";
        PreparedStatement prep1 = con.prepareStatement(sql1);
        PreparedStatement prep2 = con.prepareStatement(sql2);
        prep1.setInt(1,userid);

        ResultSet rs = prep1.executeQuery();

        while(rs.next())
        {
            GoShop goShop = new GoShop();
            goShop.setId(rs.getInt("id"));
            goShop.setImgurl(rs.getString("imgurl"));
            goShop.setTitle(rs.getString("title"));
            goShop.setPrice(rs.getDouble("price"));
            goShop.setUserid(userid);
            prep2.setInt(1,goShop.getId());
            prep2.setInt(2, userid);
            ResultSet rs2 = prep2.executeQuery();
            while(rs2.next())
            {
                goShop.setCount(rs2.getInt("countp"));
            }
            System.out.println(goShop.getCount());
            list.add(goShop);
            /*int id= rs.getInt("id");
            Goods goods = getGoodsById(id);
            GoShop goShop = new GoShop(goods.getId(),userid,goods.getImgurl(),goods.getTitle(),goods.getPrice());
            list.add(goShop);*/
        }
        DButil.close(rs,prep1,prep2,con);
        return list;
    }
   /* public Goods getGoodsById(int id) throws SQLException, ClassNotFoundException {
        Goods goods = new Goods();
        Connection con = DButil.getCon();
        String sql="select * from XM_GOODS where id = ?";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setInt(1,id);
        ResultSet rs = prep.executeQuery();
        while (rs.next())
        {
            goods.setId(rs.getInt("id"));
            goods.setEvaluate(rs.getInt("evaluate"));
            goods.setTitle(rs.getString("title"));
            goods.setManufacturer(rs.getString("manufacturer"));
            goods.setImgurl(rs.getString("imgurl"));
            goods.setPrice(rs.getDouble("price"));
            goods.setType(rs.getString("type"));
        }
        DButil.close(rs,prep,con);
        return goods;
    }*/
    public int delGoShop(String id,String userid) throws SQLException, ClassNotFoundException {
        Connection con = DButil.getCon();
        String sql="delete from XM_GOSHOP where id=? and userid=?";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1,id);
        prep.setString(2,userid);
        int i=prep.executeUpdate();
        con.commit();
        DButil.close(prep,con);
        return i;
    }
    public int delGoShopone(String id,String userid) throws SQLException, ClassNotFoundException {
        Connection con = DButil.getCon();
        int i=0;
        String sql1="select * from XM_GOSHOP where id=? and userid=?";
        String sql2= "update XM_GOSHOP set countp=? where id=? and userid=?";
        PreparedStatement prep1 = con.prepareStatement(sql1);
        PreparedStatement prep2 = con.prepareStatement(sql2);
        prep1.setString(1,id);
        prep1.setString(2,userid);
        ResultSet rs=prep1.executeQuery();
        while(rs.next())
        {
            int count=0;
            count=rs.getInt("countp");
            count--;
            prep2.setInt(1,count);
            prep2.setString(2,id);
            prep2.setString(3,userid);
            i=prep2.executeUpdate();
            con.commit();
        }
        con.commit();
        DButil.close(rs,prep1,prep2,con);
        return i;
    }
}
