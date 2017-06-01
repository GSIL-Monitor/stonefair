package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.entity.Email;
import com.zhenhappy.ems.manager.action.BaseAction;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.*;

import javax.activation.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/17.
 */
public class ExhibitorInvisitorMailUtil extends BaseAction {

    @Autowired
    static
    FreeMarkerConfigurer freeMarker;// 注入FreeMarker模版封装框架

    public static String emailUserName = "exhibitor@stonefair.org.cn";
    public static String emailPassword = "Jhxsf2015~";

    public static String emailCustomerHost = "imap.exmail.qq.com";
    public static String emailCustomerUserName = "do-not-reply@stonefair.org.cn";
    public static String emailCustomerPassword = "Jhxsf2015~";

    String to = "";// 收件人
    String cc = "";//抄送者
    String from = "";// 发件人
    String host = "";// smtp主机
    String username = "";
    String password = "";
    String filename = "";// 附件文件名
    String subject = "";// 邮件主题
    String content = "";// 邮件正文
    Vector file = new Vector();// 附件文件集合

    /**
     * <br>
     * 方法说明：默认构造器 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public ExhibitorInvisitorMailUtil() {
    }

    /**
     * <br>
     * 方法说明：构造器，提供直接的参数传入 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public ExhibitorInvisitorMailUtil(String to, String cc, String from, String smtpServer,
                                      String username, String password, String subject, String content) {
        this.to = to;
        this.cc = cc;
        this.from = from;
        this.host = smtpServer;
        this.username = username;
        this.password = password;
        this.subject = subject;
        this.content = content;
    }

    /**
     * <br>
     * 方法说明：设置邮件服务器地址 <br>
     * 输入参数：String host 邮件服务器地址名称 <br>
     * 返回类型：
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * <br>
     * 方法说明：设置登录服务器校验密码 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setPassWord(String pwd) {
        this.password = pwd;
    }

    /**
     * <br>
     * 方法说明：设置登录服务器校验用户 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setUserName(String usn) {
        this.username = usn;
    }

    /**
     * <br>
     * 方法说明：设置邮件发送目的邮箱 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * <br>
     * 方法说明：设置邮件抄送者的邮箱 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * <br>
     * 方法说明：设置邮件发送源邮箱 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * <br>
     * 方法说明：设置邮件主题 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * <br>
     * 方法说明：设置邮件内容 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <br>
     * 方法说明：把主题转换为中文 <br>
     * 输入参数：String strText <br>
     * 返回类型：
     */
    public String transferChinese(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "GB2312"), "GB2312", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }

    /**
     * <br>
     * 方法说明：往附件组合中添加附件 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void attachfile(String fname) {
        file.addElement(fname);
    }

    /**
     * <br>
     * 方法说明：发送邮件 <br>
     * 输入参数： <br>
     * 返回类型：boolean 成功为true，反之为false
     */
    public boolean sendMail() {
        // 构造mail session
        Properties props = new Properties() ;
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // 构造MimeMessage 并设定基本的值
            MimeMessage msg = new MimeMessage(session);
            //MimeMessage msg = new MimeMessage();
            msg.setFrom(new InternetAddress(from));


            //msg.addRecipients(Message.RecipientType.TO, address); //这个只能是给一个人发送email
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to)) ;
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            subject = transferChinese(subject);
            msg.setSubject(subject);

            // 构造Multipart
            Multipart mp = new MimeMultipart();

            // 向Multipart添加正文
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setContent(content, "text/html;charset=gb2312");

            // 向MimeMessage添加（Multipart代表正文）
            mp.addBodyPart(mbpContent);

            // 向Multipart添加附件
            Enumeration efile = file.elements();
            while (efile.hasMoreElements()) {

                MimeBodyPart mbpFile = new MimeBodyPart();
                filename = efile.nextElement().toString();
                FileDataSource fds = new FileDataSource(filename);
                mbpFile.setDataHandler(new DataHandler(fds));
                //<span style="color: #ff0000;">//这个方法可以解决附件乱码问题。</span>
                String filename= new String(fds.getName().getBytes(),"ISO-8859-1");

                mbpFile.setFileName(filename);
                // 向MimeMessage添加（Multipart代表附件）
                mp.addBodyPart(mbpFile);

            }

            file.removeAllElements();
            // 向Multipart添加MimeMessage
            msg.setContent(mp);
            msg.setSentDate(new Date());
            msg.saveChanges() ;
            // 发送邮件

            //final DeliveredStateFuture future = new DeliveredStateFuture();
            Transport transport = session.getTransport("smtp");
            /*transport.addTransportListener(new TransportListener() {
                @Override
                public void messageDelivered(TransportEvent transportEvent) {
                    future.setState(DeliveredState.MESSAGE_DELIVERED);
                    System.out.println("---messageDelivered: " + transportEvent.toString());
                }

                @Override
                public void messageNotDelivered(TransportEvent transportEvent) {
                    future.setState(DeliveredState.MESSAGE_NOT_DELIVERED);
                    System.out.println("---messageNotDelivered: " + transportEvent.toString());
                }

                @Override
                public void messagePartiallyDelivered(TransportEvent transportEvent) {
                    future.setState(DeliveredState.MESSAGE_PARTIALLY_DELIVERED);
                    System.out.println("---messagePartiallyDelivered: " + transportEvent.toString());
                }
            });*/
            transport.connect(host, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception ex) {
            ex.printStackTrace();
//          Exception ex = null;
//          if ((ex = mex.getNextException()) != null) {
//              ex.printStackTrace();
//          }
            return false;
        }
        return true;
    }

    /*private enum DeliveredState {
        INITIAL, MESSAGE_DELIVERED, MESSAGE_NOT_DELIVERED, MESSAGE_PARTIALLY_DELIVERED,
    }
    private static class DeliveredStateFuture {
        private DeliveredState state = DeliveredState.INITIAL;
        synchronized void waitForReady() throws InterruptedException {
            if (state == DeliveredState.INITIAL) {
                wait();
            }
        }
        synchronized DeliveredState getState() {
            return state;
        }
        synchronized void setState(DeliveredState newState) {
            state = newState;
            notifyAll();
        }
    }*/

    public static String getMailText(Email email) throws Exception {
        // 通过指定模板名获取FreeMarker模板实例
        Template template = freeMarker.getConfiguration().getTemplate("mail/VisitorReplayTemp.html");

        // FreeMarker通过Map传递动态数据
        Map<Object, Object> model = new HashMap<Object, Object>();
        model.put("email", email); // 注意动态数据的key和模板标签中指定的属性相匹配

        // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
        String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return htmlText;
    }
}
