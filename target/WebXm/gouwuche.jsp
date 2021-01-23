<%@ page import="java.util.ArrayList" %>
<%@ page import="com.seecen.entity.GoShop" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="author" content="order by dede58.com"/>
    <title>我的购物车-小米商城</title>
    <link rel="stylesheet" type="text/css" href="css/Untitled-3.css">
    <script>
        var total;
        //进行全选操作
        function selectAll() {
            var onInput = document.getElementsByName("carcheckbox");
            for (var i = 0; i < onInput.length; i++) {
                onInput[i].checked = document.getElementById("allCheckbox").checked;
            }
            productPic()
        }

        //根据单个复选框的情况判断全选复选框是否被选中
        function selectSingleon() {
            var k = 0;
            //获取复选框的值
            var oInput = document.getElementsByName("carcheckbox");
            for (var i = 0; i < oInput.length; i++) {
                if (oInput[i].checked == false) {
                    k = 1;
                    break;
                }
            }
            if (k == 0) {  //下面的复选框全部选中了
                //让全选勾中
                document.getElementById("allCheckbox").checked = true;
            } else {
                document.getElementById("allCheckbox").checked = false;
            }
            productPic()
        }
        //改变所购商品的数量
        //numId表示对应的商品数量文本框Id，flag表示增加还是减少的商品数量
        function changeNum(numId,flag,id,userid) {
            var numIdp = document.getElementById(numId);
           // alert(numId.value);
            if(flag=="minus"){ //减少商品数量
                if(numIdp.value<=1){
                    window.location.href="delGoShop.do?id="+id+"&userid="+userid;
                    return false;
                }else{
                    //不断的递减
                    numIdp.value = parseInt(numIdp.value)-1;
                    document.getElementById(numId).innerHTML = numIdp.value;
                    productPic();//当商品增加和减少时计算总金额
                    window.location.href="delGoShopone.do?id="+id+"&userid="+userid;
                }
            }else{
                //数量不断的递增
                numIdp.value = parseInt(numIdp.value)+1;
                document.getElementById(numId).innerHTML = numIdp.value;
                productPic();
                window.location.href="addShop.do?id="+id+"&userid="+userid;
            }
        }
        //自动计算商品金额
        function productPic() {


            //商品金额总计:
            total =0;
            var pic; //每行商品的单价
            var num; //每行商品的数量
            var onInput = document.getElementsByName("carcheckbox");
            //获取所有的行数
            var linenum = document.getElementById("shopping").getElementsByTagName("tr");
            if(linenum.length>0){
                //排除 第一行和最后一行的标题
                for(var i=1;i<linenum.length;i++){
                    if(linenum[i].getElementsByTagName("td").length>2){
                        pic = linenum[i].getElementsByTagName("td")[3].innerHTML;
                        num = linenum[i].getElementsByTagName("td")[4].getElementsByTagName("input")[0].value;
                        if(onInput[i-1].checked == true)
                            total += pic*num;
                        linenum[i].getElementsByTagName("td")[5].innerHTML=pic*num;  //小计
                    }
                }
                document.getElementById("total").innerHTML = total;

            }
        }
        window.onload = productPic;

    function clerk() {
       window.location.href="toPay.do?account="+total;
    }
    </script>
</head>
<body>
<!-- start header -->
<!--end header -->

<!-- start banner_x -->
<div class="banner_x center">
    <a href="showShopping.do" target="_blank">
        <div class="logo fl"></div>
    </a>

    <div class="wdgwc fl ml40">我的购物车</div>
    <div class="wxts fl ml20">温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</div>
    <div class="dlzc fr">
        <ul>
            <li><a href="login.jsp" target="_blank">登录</a></li>
            <li>|</li>
            <li><a href="register.jsp" target="_blank">注册</a></li>
        </ul>

    </div>
    <div class="clear"></div>
</div>
<div class="xiantiao"></div>
<div class="gwcxqbj" id="gwc">
    <div class="gwcxd center">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="shopping">
<%--            <form action="" method="" name="myform">--%>
                <tr class="top2 center">
                    <td class="sub_top fl">
                        <input type="checkbox" value="quanxuan" class="quanxuan" style="vertical-align:text-bottom;"
                               id="allCheckbox" onclick="selectAll()"/>全选
                    </td>
                    <td class="sub_top fl">商品名称</td>
                    <td class="sub_top fl" >单价</td>
                    <td class="sub_top fl">数量</td>
                    <td class="sub_top fl">小计</td>
                    <td class="sub_top fr">操作</td>
                    <td class="clear"></td>
                </tr>
                <%
                    int num = 0;
                    ArrayList<GoShop> golist = (ArrayList) request.getAttribute("Golist");
                    for(GoShop goShop : golist){
                        num++;
                %>
                    <tr class="content2 center" id="father">
                        <td class="sub_content fl ">
                            <input type="checkbox" value="quanxuan" name="carcheckbox" class="quanxuan"
                                   style="position: relative;top:50px" onclick="selectSingleon()"/>
                        </td>
                        <td class="sub_content fl"><img src="<%=goShop.getImgurl()%>"
                                                        style="position: relative;top:38px;width: 50px;height: 50px;">
                        </td>
                        <td class="sub_content fl ft20"><%=goShop.getTitle()%></td>
                        <td class="sub_content fl "><%=goShop.getPrice()%></td>
                        <td class="sub_content fl" >
                            <img src="image1/3_7.jpg" onclick="changeNum(<%=num%>,'minus',<%=goShop.getId()%>,<%=goShop.getUserid()%>)" style="cursor: pointer;"/>
                           <%-- <input class="shuliang" type="number" value="1" step="1" min="1">--%>
                            <input id=<%= num%> type="text" value="<%=goShop.getCount()%>" class="num_input" readonly  style="width: 25px;height:15px;text-align: center;"/>
                            <img src="image1/3_6.jpg" onclick="changeNum(<%=num%>,'add',<%=goShop.getId()%>,<%=goShop.getUserid()%>)"
                                 style="cursor: pointer;position: relative;left:45px;top:-63px;"/>
                        </td>
                        <td class="sub_content fl"></td>
                        <td class="sub_content fl"><a href="#"
                                                      onclick="window.location.href='delGoShop.do?id=<%=goShop.getId()%> &userid=<%=goShop.getUserid()%>'"
                                                      id="del">×</a></td>
                        <td class="clear"></td>
                    </tr>
                    <%
                        }
                    %>
                <%--计总--%>
                <tr class="jiesuandan mt20 center">
                    <td class="tishi fl ml20">
                        <ul>
                            <li><a href="showShopping.do">继续购物</a></li>
                            <div class="clear"></div>
                        </ul>
                    </td>
                    <td class="jiesuan fr">
                        <div class="jiesuanjiage fl">合计（不含运费）：<span id="total"></span>元</div>
                        <div class="jsanniu fr"><input class="jsan" type="submit" name="jiesuan" value="去结算" onclick="clerk()"/></div>
                        <div class="clear"></div>
                    </td>
                    <div class="clear"></div>
                </tr>
        </table>
    </div>
</div>


<!-- footer -->
<footer class="center">
    <div class="mt20">小米商城|MIUI|米聊|多看书城|小米路由器|视频电话|小米天猫店|小米淘宝直营店|小米网盟|小米移动|隐私政策|Select Region</div>
    <div>©mi.com 京ICP证110507号 京ICP备10046444号 京公网安备11010802020134号 京网文[2014]0059-0009号</div>
    <div>违法和不良信息举报电话：185-0130-1238，本网站所列数据，除特殊说明，所有数据均出自我司实验室测试</div>
</footer>
<script>
    var arr=document.getElementsByClassName("content2 center");//得到商品的tr数组
    var a=arr.length;//得到商品的tr数量
    var he=a*120+180;
    document.getElementById("gwc").style.height=he+"px";

</script>
</body>
</html>
