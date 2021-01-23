package com.seecen.dao;

import com.seecen.entity.Goods;
import com.seecen.util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoodsDao {
    public ArrayList<Goods> select() throws SQLException, ClassNotFoundException {
        ArrayList<Goods> res = new ArrayList<>();
        Connection con = DButil.getCon();
        String sql = "select * from XM_GOODS";
        PreparedStatement prep = con.prepareStatement(sql);
        ResultSet rs = prep.executeQuery();
        while(rs.next())
        {
            Goods goods = new Goods();
            int id=rs.getInt("id");
            goods.setId(id);
            goods.setType(rs.getString("type"));
            goods.setTitle(rs.getString("title"));
            goods.setPrice(rs.getDouble("price"));
            goods.setImgurl(rs.getString("imgurl"));
            goods.setManufacturer(rs.getString("manufacturer"));
            goods.setEvaluate(rs.getInt("evaluate"));
            res.add(goods);
        }
        DButil.close(rs,prep,con);
        return res;
    }
    public ArrayList<Goods> getGoodsPage(int i,int pa) throws SQLException, ClassNotFoundException {
        ArrayList<Goods> listp = new ArrayList<>();
        Connection con = DButil.getCon();
        String sql = "SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY id ASC) AS Num FROM XM_GOODS )AS TempTable WHERE Num BETWEEN ? AND ?";
        PreparedStatement prep = con.prepareStatement(sql);
        int a = pa*i ;
        int b = pa*(i-1)+1;
        prep.setInt(1,b);
        prep.setInt(2,a);
        ResultSet rs = prep.executeQuery();


        while(rs.next())
        {
            Goods goods = new Goods();
            int id=rs.getInt("id");
            goods.setId(id);
            goods.setType(rs.getString("type"));
            goods.setTitle(rs.getString("title"));
            goods.setPrice(rs.getDouble("price"));
            goods.setImgurl(rs.getString("imgurl"));
            goods.setManufacturer(rs.getString("manufacturer"));
            goods.setEvaluate(rs.getInt("evaluate"));
            listp.add(goods);
        }
        DButil.close(rs,prep,con);
        return listp;
    }
}
