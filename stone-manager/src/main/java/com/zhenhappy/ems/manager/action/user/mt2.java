package com.zhenhappy.ems.manager.action.user;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/8.
 */
public class mt2 {
    /*
     * WebService
	 */
    //private String serviceURL = "http://113.106.91.228:9000/webservice.asmx";
    private String serviceURL = "http://113.106.91.228:9000/webservice.asmx";
    private String sn = "SDK100";// 用户名
    private String pwd = "123321";// 密码
    /**
     * http://113.106.91.228:9000/webservice.asmx/mt2?Sn=SDK100&Pwd=123321&mobile=13459298520&content=验证码11
     *
     * @param mobile
     * @param content
     * @return
     */

    //发送短信
    public String mt(String mobile, String content) {
        //设置返回值
        String resultok = "";

        //定义访问地址
        String toAction = "http://dome/mt";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";//xml 头标签

        //SOAP：简单对象访问协议，简单对象访问协议
        //（SOAP）是一种轻量的、简单的、基于 XML 的协议，
        //它被设计成在 WEB 上交换结构化的和固化的信息。
        //SOAP 可以和现存的许多因特网协议和格式结合使用，
        //包括超文本传输协议（ HTTP），简单邮件传输协议（SMTP），
        //多用途网际邮件扩充协议（MIME）。
        //它还支持从消息系统到远程过程调用（RPC）等大量的应用程序。


        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mt xmlns=\"http://tempuri.org/\">";
        xml += "<Sn>" + sn + "</Sn>"; //用户名
        xml += "<Pwd>" + pwd + "</Pwd>";//密码
        xml += "<mobile>" + mobile + "</mobile>";//手机号
        xml += "<content>" + content + "</content>";//内容
        xml += "</mt>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";
        //申明URL
        URL url;
        try {
            //发送的服务器地址
            url = new URL(serviceURL);
            //获取 connection对象
            URLConnection connection = url.openConnection();

            //走http传输协议 方法
            HttpURLConnection httpconnection = (HttpURLConnection) connection;
            //在网络传输中我们往往要传输很多变量，我们可以利用ByteArrayOutputStream把所有的变量收集到一起，然后一次性把数据发送出去。
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            //写入定义好的xml 文件
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            //key  value   存入数据

            httpconnection.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconnection.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconnection.setRequestProperty("toAction", toAction);
            //设置发送方式
            httpconnection.setRequestMethod("POST");
            //setDoInput：设置输入的内容setDoOutput：设置输出的内容
            httpconnection.setDoInput(true);
            httpconnection.setDoOutput(true);

            OutputStream out = httpconnection.getOutputStream();
            out.write(b);
            out.close();
            //InputStreamReader 是字节流通向字符流的桥梁：它使用指定的 charset 读取字节并将其解码为字符。
            //它使用的字符集可以由名称指定或显式给定，否则可能接受平台默认的字符集。
            InputStreamReader inputsr = new InputStreamReader(httpconnection.getInputStream());

            //这个类就是一个包装类，它可以包装字符流，将字符流放入缓存里，先把字符读到缓存里，
            //到缓存满了或者你flush的时候，再读入内存，就是为了提供读的效率而设计的。
            BufferedReader in = new BufferedReader(inputsr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                //正则表达式
                Pattern pattern = Pattern.compile("<mtResult>(.*)</mtResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    resultok = matcher.group(1);
                }
            }
            return resultok;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String mt2(String mobile, String content) {
        String result = "";
        String soapAction = "http://tempuri.org/mt2";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mt2 xmlns=\"http://tempuri.org/\">";
        xml += "<Sn>" + sn + "</Sn>";
        xml += "<Pwd>" + pwd + "</Pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "</mt2>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn
                    .getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<mt2Result>(.*)</mt2Result>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) throws Exception, IOException {
        mt2 client = new mt2();
        //String resultok = client.mt2("13459298520", "我本将心向明月");

        String result_balance = client.mt2("13696938112", "程序接口测试");
        System.out.println("result_balance:  " + result_balance);

        /*if (resultok.equals("0")) {
            System.out.println("发送短信成功");
        } else {
            System.out.print("发送失败");
        }*/
    }
}
