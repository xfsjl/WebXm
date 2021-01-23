<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="author" content="order by dede58.com"/>
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="./css/login.css">
    <script src="js/jquery-3.3.1.min.js"></script>

</head>
<body>
<form  id="myForm" method="post" action="${pageContext.request.contextPath}/regedit.do">
    <!--隐藏域-->
    <input type="hidden" id="codeHidden" value="">
    <div class="regist">
        <div class="regist_center">
            <div class="regist_top">
                <div class="left fl">会员注册</div>
                <div class="right fr"><a href="./index.html" target="_self">小米商城</a></div>
                <div class="clear"></div>
                <div class="xian center"></div>
            </div>
            <div class="regist_main center">
                <div class="username">用&nbsp;&nbsp;户&nbsp;&nbsp;名:&nbsp;&nbsp;<input class="shurukuang" type="text" name="username" required="required" placeholder="请输入你的用户名"/><span>请不要输入汉字</span></div>
                <div class="username">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码:&nbsp;&nbsp;<input class="shurukuang" type="password" name="password" id="ps1" required placeholder="请输入你的密码"/><span>请输入6位以上字符</span></div>

                <div class="username">确认密码:&nbsp;&nbsp;<input class="shurukuang" type="password" name="repassword" id="ps2" required placeholder="请确认你的密码"/><span>两次密码要输入一致哦</span></div>
                <div class="username">手&nbsp;&nbsp;机&nbsp;&nbsp;号:&nbsp;&nbsp;<input class="shurukuang" type="text" name="tel" id="tel" required placeholder="请填写正确的手机号"/><span>填写下手机号吧，方便我们联系您！</span></div>
                <div class="username">
                    <div style="float: left">验&nbsp;&nbsp;证&nbsp;&nbsp;码:&nbsp;&nbsp;<input class="yanzhengma" type="text" name="inCode"  id="inCodes" class="tel" required placeholder="请输入验证码" style="width:105px"/></div>
                    <div  id="telCode" style="float: left">获取验证码</div>
                </div>
            </div>
            <div class="regist_submit">
                <input class="submit" type="submit" name="submit" required value="立即注册" >
            </div>

        </div>
    </div>
</form>

</body>
<script>
    //通过ajax访问后台，向用户手机发送验证码
    $(function ()
        {
            $("#telCode").bind('click',show);
            function show()
            {
                //手机位数是11位数字，通过ajax向后台发送手机号码的信息
                if($("#tel").val().length == 11)
                {
                    //取到电话号码
                    var telNum = $("#tel").val();
                    //使用ajax向后台传值
                    var ha=$.ajax({
                        type:"POST",
                        url:"sendCode.do",
                        data:"tel="+telNum,
                        async:false

                    }).responseText;
                    $("#codeHidden").val(ha);

                    //点击了验证码之后，一段时间之后才能再点
                    $("#telCode").unbind('click');
                    //将验证码设置为销毁状态
                    $("#telCode").addClass('ui-state-disabled');
                    //每一次可以点击的时间间隔为60秒
                    var count = 60;
                    //定时器
                    var timer = setInterval(function ()
                    {
                        $("#telCode").html(count+"s后重发");
                        count--;
                        if(count==0)
                        {
                            //清除定时器
                             clearInterval(timer);
                             //恢复为验证码
                             $("#telCode").html("获取验证码");
                             //清除销毁样式
                            $("#telCode").removeClass('ui-state-disabled');
                            //可以点击
                            $("#telCode").bind('click',show);
                        }
                    },1000);

                }
                else
                {
                    alert("请输入正确的手机位数！");
                }
            }
        });

    $("#myForm").submit(function () {
        if($("#inCodes").val() == "")
        {
            alert("请输入验证码！");
            return false;
        }
        if($("#ps1").val()!=$("#ps2").val())
        {
            alert("两次输入的密码不同！");
            $("#ps1").val("");
            $("#ps2").val("");
            return false;
        }

        if($("#inCodes").val()==$("#codeHidden").val())
        {
            return true;
        }
        else
        {
            alert("验证码错误！")
            return false;
        }


    })

</script>

</html>