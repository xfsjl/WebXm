package com.seecen.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


import java.util.Random;

/**
 * @site www.xxx.com （公司网址）
 * @company 某某网络科技有限公司  （公司名称）
 * @create 2019-08-19 16:49 （时间）
 * 发送短信验证的工具类
 */
public class SendMessageUtil {
    private static final String SMS_Url = "http://sms.webchinese.cn/web_api/";
    /**
     * @param Uid  SMS用户id:com.seecen
     * @param Key          接口秘钥：SMS登录可查（非登录密码）d41d8cd98f00b204e980
     * @param sendPhoneNum 短信发送目标号码
     * @param desc         短信内容
     * @return Integer(1：成功码，其他失败，具体参见注释)
     */
    //发送短信的方法
    public static Integer send(String Uid,String Key,String sendPhoneNum,String desc){
        //http通信库
        HttpClient client = new HttpClient();
        //用于设置调取接口的方法
        PostMethod post = new PostMethod(SMS_Url);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        //设置参数
        //一种传参的方式
        NameValuePair[] data = {
                new NameValuePair("Uid", Uid),
                new NameValuePair("Key", Key),//秘钥
                new NameValuePair("smsMob", sendPhoneNum),
                new NameValuePair("smsText", desc)
        };
        post.setRequestBody(data);
        try{
            client.executeMethod(post);
        }catch (Exception e){
            e.printStackTrace();
        }
        Header[]  headers = post.getRequestHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }

        String result = "";
        try {
            result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        post.releaseConnection();

        return Integer.parseInt(result);
    }
    //测试模拟成功发送
    public static int sendTest(String Uid,String Key,String sendPhoneNum,String desc){
        String result ="1";
        return Integer.parseInt(result);
    }

    //判断密钥状态
    /**
     * -1  没有该用户账户
     * -2 接口密钥不正确 [查看密钥]不是账户登陆密码
     * -21    MD5接口密钥加密不正确
     * -3 短信数量不足
     * -11    该用户被禁用
     * -14    短信内容出现非法字符
     * -4 手机号格式不正确
     * -41    手机号码为空
     * -42    短信内容为空
     * -51    短信签名格式不正确接口签名格式为：【签名内容】
     * -6 IP限制
     * 大于0    短信发送数量
     * 以上作为补充
     */
    public static String getMessage(Integer code){
        String message;
        if (code > 0) {
            message = "SMS-f发送成功！";
        } else if (code == -1) {
            message = "SMS-没有该用户账户";
        } else if (code == -2) {
            message = "SMS-接口密钥不正确";
        } else if (code == -21) {
            message = "SMS-MD5接口密钥加密不正确";
        } else if (code == -3) {
            message = "SMS-短信数量不足";
        } else if (code == -11) {
            message = "SMS-该用户被禁用";
        } else if (code == -14) {
            message = "SMS-短信内容出现非法字符";
        } else if (code == -4) {
            message = "SMS-手机号格式不正确";
        } else if (code == -41) {
            message = "SMS-手机号码为空";
        } else if (code == -42) {
            message = "SMS-短信内容为空";
        } else if (code == -51) {
            message = "SMS-短信签名格式不正确接口签名格式为：【签名内容】";
        } else if (code == -6) {
            message = "SMS-IP限制";
        } else {
            message = "其他错误";
        }
        return message;
    }
    //随机生成6位验证码
    public static String getRandomCode(Integer code){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<code;i++){
            sb.append(random.nextInt(code));
        }
        return sb.toString();
    }

}
