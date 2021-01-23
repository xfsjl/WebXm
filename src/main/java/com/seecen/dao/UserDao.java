package com.seecen.dao;

import com.seecen.entity.User;
import com.seecen.util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public int existName(String name) throws SQLException, ClassNotFoundException {
        Connection con = DButil.getCon();
        String sql = "select username from table_users where username=?";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1,name);
        ResultSet rs = prep.executeQuery();
        int i=0;
        if(rs.next())
        {
            i=1;
        }
        DButil.close(rs,prep,con);
        return i;
    }
    public int addUser(User user) throws SQLException, ClassNotFoundException {
        Connection con = DButil.getCon();
        String sql = "insert into table_users "+" values(?,?,9)";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1,user.getUsername());
        prep.setString(2,user.getPassword());
        int i = prep.executeUpdate();
        DButil.close(prep,con);
        return i;
    }
    public boolean login(User user) throws SQLException, ClassNotFoundException {
        Connection con = DButil.getCon();
        String sql = "select * from table_users where username=? and userpassword=?";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1,user.getUsername());
        prep.setString(2,user.getPassword());
        ResultSet rs = prep.executeQuery();
        boolean flag = false;
        if(rs.next())
        {
            user.setId(rs.getInt("id"));
            flag=true;
        }
        else
            flag= false;
        DButil.close(rs,prep,con);
        return flag;

    }
}
