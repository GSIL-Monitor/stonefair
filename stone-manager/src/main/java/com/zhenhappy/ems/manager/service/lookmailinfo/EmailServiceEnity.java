package com.zhenhappy.ems.manager.service.lookmailinfo;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Administrator on 2016/12/9.
 */
public class EmailServiceEnity extends EmailEntity {
    // java.util.propertis 类
    private transient Properties props;
    // 一个邮件会话
    private transient Session session;
    // 邮件消息
    private transient MimeMessage message;
    // 邮件传输对象
    private Transport transport;
    // 邮件发送内容
    private String content;
    // 邮件内容格式
    private final String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
    // 端口号
    private final static String MAIL_PORT = "25";
    // 邮箱协议常量
    public static final String MAIL_PROTOCOL_SMTP = "smtp";
    public static final String MAIL_PROTOCOL_POP3 = "pop3";
    // 邮箱所使用的协议
    private String mailProtocol;
    // 邮箱服务器列表
    private String hostPort;

//////////////////分//////////////////界/////////////////////线/////////////////////////////

    static class MyAuthenricator extends Authenticator {
        String user = null;
        String pass = "";

        public MyAuthenricator(String user, String pass) {
            this.user = user;
            this.pass = pass;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pass);
        }

    }

    /**
     * 实参构造
     *
     * @param mailToaddress 收件人地址，多个以逗号隔开
     * @param content       邮件内容
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public EmailServiceEnity(String mailToaddress, String content, String mailProtocol) throws UnsupportedEncodingException, MessagingException {
        super(username, password);
        this.setMailProtocol(mailProtocol);
        this.setHostPort(mailProtocol + "." + username.split("@")[1]);
        this.content = content;
        this.session = this.initSession();
        this.message = this.initMessage(this.getSession(), mailToaddress);
        // 这里需要对协议进行判断，SMTP:为发送协议（初始化Transport）    POP3：则为接收协议（只能初始化Store，在接收邮件章节用到）
        if (this.getMailProtocol().equals(MAIL_PROTOCOL_SMTP)) {
            this.transport = this.initTransport(this.getSession());
        }
    }

    /**
     * 初始化perps文件
     *
     * @return
     */
    public Properties initPrope() {
        // 初始化props文件
        props = new Properties();
        props.setProperty("mail.transport.protocol", this.getMailProtocol());//发送邮件协议
        props.put("mail.smtp.auth", "true");        //需要验证
        props.put("mail.smtp.host", this.getHostPort());    //服务器地址
        return props;
    }

    /**
     * 初始化session会话
     *
     * @return
     */
    public Session initSession() {
        session = Session.getInstance(this.initPrope(), new MyAuthenricator(username, password));
        session.setDebug(true);
        return session;
    }

    /**
     * 初始化Message消息
     *
     * @param session
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public MimeMessage initMessage(Session session, String mailToaddress) throws MessagingException, UnsupportedEncodingException {
        message = new MimeMessage(session);

        // 设置发件人地址
        message.setFrom(new InternetAddress(username, "要显示的发件人名称"));

        // 设置邮件主题
        message.setSubject("主题：默认主题");

        // 设置邮件发送内容和内容的content-type
        message.setContent(content.toString(), this.CONTENT_TYPE_HTML);

        // 设置邮件接收人地址
        if (mailToaddress.trim().length() > 0) {
            String[] address = mailToaddress.split(",");
            for (int i = 0; i < address.length; i++) {
                // addRecipient（该方法为添加收件人列表，参数一为类型：TO-收件人，CC-抄送，参数二为一个邮件地址）
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(address[i].trim()));
                // 下面方法为传递一个收件地址字符串 （二者方法任选其一即可）
                message.addRecipients(Message.RecipientType.CC, address[i].trim());
            }
        }
        return message;
    }

    /**
     * 初始化Transport
     *
     * @param session
     * @return
     * @throws NoSuchProviderException
     */
    public Transport initTransport(Session session) throws NoSuchProviderException {
        transport = session.getTransport();
        return transport;
    }


    /*****************
     * 提供必要的get set方法支持  ************飘逸的分割线
     ****************/
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public MimeMessage getMessage() {
        return message;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
    }

    public Properties getProps() {
        return props;
    }

    public String getContentTypeHtml() {
        return CONTENT_TYPE_HTML;
    }

    public static String getMailPort() {
        return MAIL_PORT;
    }

    public String getHostPort() {
        return hostPort;
    }

    public void setHostPort(String hostPort) {
        this.hostPort = hostPort;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCONTENT_TYPE_HTML() {
        return CONTENT_TYPE_HTML;
    }

    public static String getMailProtocolSmtp() {
        return MAIL_PROTOCOL_SMTP;
    }

    public static String getMailProtocolPop3() {
        return MAIL_PROTOCOL_POP3;
    }

    public String getMailProtocol() {
        return mailProtocol;
    }

    public void setMailProtocol(String mailProtocol) {
        this.mailProtocol = mailProtocol;
    }
}
