package com.seecen.action;

import com.seecen.dao.GoShopDao;
import com.seecen.dao.GoodsDao;
import com.seecen.dao.UserDao;
import com.seecen.entity.GoShop;
import com.seecen.entity.Goods;
import com.seecen.entity.Page;
import com.seecen.entity.User;
import com.seecen.util.SendMessageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class XmAction extends HttpServlet {
    UserDao userDao = new UserDao();
    GoodsDao goodsDao = new GoodsDao();
    GoShopDao goShopDao = new GoShopDao();
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String uri = req.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/"),uri.lastIndexOf("."));
        //获取验证码
        if("/sendCode".equals(uri))
        {
            String tel = req.getParameter("tel");
            String code = SendMessageUtil.getRandomCode(6);
            Integer sendCode = SendMessageUtil.send("zgycsmb","d41d8cd98f00b204e98011",tel,code);
            String message =  SendMessageUtil.getMessage(sendCode);
            PrintWriter writer = resp.getWriter();
            writer.print(code);
        }
        else if("/regedit".equals(uri))
        {
            //使用req对象的getParameter方法取到注册的用户名以及密码
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            User user = new User(1,username,password,9);
            //判断用户名是否存在
            //默认用户就是不存在的
            int i = 0;
            try {
                i = userDao.existName(username);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //如果存在，则重新注册
            if (i==1){
                req.getRequestDispatcher("register.jsp").forward(req,resp);
            }else {//如果不存在，则将该条用户信息添加进数据库
                //添加用户
                try {
                    userDao.addUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //跳转到登录页面
                resp.sendRedirect("login.jsp");
            }



        }
        else if ("/login".equals(uri))
        {
            String name = req.getParameter("username");
            String password = req.getParameter("password");
            HttpSession se = req.getSession();
            User user = new User();
            user.setUsername(name);
            user.setPassword(password);
            boolean islogin = false;
            try {
                islogin = userDao.login(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(islogin)
            {
                se.setAttribute("resultUser",user);
                resp.sendRedirect("showShopping.do");
            }
            else
            {
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }

        }
        else if("/showShopping".equals(uri))
        {
            String page = req.getParameter("page");
            if(page == null)
            {
                page="1";
            }
            int i = Integer.parseInt(page);
            int pa=5;
            try {
                ArrayList<Goods>  list = goodsDao.select();
                int countPa = list.size();
                ArrayList<Goods> listp = goodsDao.getGoodsPage(i,pa);
                Page p = new Page();
                p.setCountPa(countPa);
                p.setList(listp);
                p.setPage(i);
                p.setPa(pa);
                req.setAttribute("ps",p);
                req.getRequestDispatcher("index.jsp").forward(req,resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if("/addShop".equals(uri))
        {
            String id = req.getParameter("id");
            String userid = req.getParameter("userid");
            System.out.println(id+"    "+userid);
            try {
                goShopDao.addShop(id,userid);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            resp.sendRedirect("getShops.do?userid="+userid);
        }
        else if("/getShops".equals(uri))
        {
            String userid= req.getParameter("userid");
            ArrayList<GoShop> list = new ArrayList<>();
            try {
                list =goShopDao.findGoodsById(Integer.parseInt(userid));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            req.setAttribute("Golist",list);
            System.out.println(list);
            req.getRequestDispatcher("gouwuche.jsp").forward(req,resp);

        }
        else if("/delGoShop".equals(uri))
        {
            String id=req.getParameter("id");
            String userid=req.getParameter("userid");
            int i = 0;
            try {
                i=goShopDao.delGoShop(id,userid);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(i>=1)
            {
                resp.sendRedirect("getShops.do?userid="+userid);
            }
            else
            {
                System.out.println("删除失败");
            }
        }
        else if("/delGoShopone".equals(uri))
        {
            String id=req.getParameter("id");
            String userid=req.getParameter("userid");
            int i = 0;
            try {
                i=goShopDao.delGoShopone(id,userid);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(i>=1)
            {
                resp.sendRedirect("getShops.do?userid="+userid);
            }
            else
            {
                System.out.println("删除失败");
            }
        }
        else if("/toPay".equals(uri))
        {
            String account = req.getParameter("account");
            req.setAttribute("account",account);
            req.getRequestDispatcher("pay.jsp").forward(req,resp);

        }

    }

}
