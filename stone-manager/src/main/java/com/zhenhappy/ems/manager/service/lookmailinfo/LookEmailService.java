package com.zhenhappy.ems.manager.service.lookmailinfo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * Created by Administrator on 2016/12/9.
 */
public class LookEmailService {
    private transient EmailServiceEnity emailService;

    public void lookEmail() throws MessagingException, IOException, InterruptedException {

        /**
         * 接收邮件时，邮箱的协议为POP3，SMTP为邮件传输协议，这里别搞混了
         * 并且检查你的邮箱设置POP3功能是否开启
         */
        emailService = new EmailServiceEnity("", "", EmailServiceEnity.MAIL_PROTOCOL_POP3);

        /**
         *  javax.mail.Store类用于连接邮件接收服务器，并访问邮件接收服务器上的各个邮箱夹
         *  javax.mail.Folder类表示邮件夹
         *  通过一个Session我们可以拿到一个邮箱对应的Store
         */
        Store store = emailService.getSession().getStore(emailService.getMailProtocol());
        store.connect(emailService.getHostPort(), EmailServiceEnity.username, EmailServiceEnity.password);

        /**
         *     通过Store拿到收件箱文件夹
         *  INBOX 标识获取到邮件夹里的收件箱  （对于POP3协议仅INBOX可用--蛋疼哦）
         *  并以只读方式打开收件箱
         */
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        /**
         * 读取到的邮件是存在Message里面，有不同的获取方法
         * 方法一：获取的是全部邮件，返回一个Message数组
         * 方法二：获取指定条目的邮件（1表示邮箱里面的第一封邮件，也就是最早的一封）
         * 还有很多方法，如起始位置和结束位置......不一一列举
         */
        Message[] messages = folder.getMessages();    // 方法一，得到全部邮件数组
        Message message = folder.getMessage(messages.length);    // 方法二

        System.out.println("邮件接收时间：" + message.getSentDate());
        System.out.println("邮件发送者：" + message.getFrom()[0]);
        System.out.println("邮件主题：" + message.getSubject());
        System.out.println("邮件内容：" + message.getContent()); // 内存地址
        System.out.println("***********************飘逸的分割线*****************************");

        // 得到邮件的Multipart（内容总部件--【包涵附件】）
        Multipart multipart = (Multipart) message.getContent();
        int count = multipart.getCount();    // 部件个数
        for (int i = 0; i < count; i++) {
            // 单个部件     注意：单个部件有可能又为一个Multipart，层层嵌套
            BodyPart part = multipart.getBodyPart(i);
            // 单个部件类型
            String type = part.getContentType().split(";")[0];
            /**
             * 类型众多，逐一判断，其中TEXT、HTML类型可以直接用字符串接收，其余接收为内存地址
             * 可能不全，如有没判断住的，请自己打印查看类型，在新增判断
             */
            if (type.equals("multipart/alternative")) {        // HTML （文本和超文本组合）
                System.out.println("超文本:" + part.getContent().toString());
            } else if (type.equals("text/plain")) {    // 纯文本
                System.out.println("纯文本:" + part.getContent().toString());
            } else if (type.equals("text/html")) {    // HTML标签元素
                System.out.println("HTML元素:" + part.getContent().toString());
            } else if (type.equals("multipart/related")) {    // 内嵌资源 (包涵文本和超文本组合)
                System.out.println("内嵌资源:" + part.getContent().toString());
            } else if (type.contains("application/")) {        // 应用附件 （zip、xls、docx等）
                System.out.println("应用文件：" + part.getContent().toString());
            } else if (type.contains("image/")) {            // 图片附件 （jpg、gpeg、gif等）
                System.out.println("图片文件：" + part.getContent().toString());
            }

            /*****************************************获取邮件内容方法***************************************************/
            /**
             * 附件下载
             * 这里针对image图片类型附件做下载操作，其他类型附件同理
             */
            if (type.contains("image/")) {
                // 打开附件的输入流
                DataInputStream in = new DataInputStream(part.getInputStream());
                // 一个文件输出流
                FileOutputStream out = null;
                // 获取附件名
                String fileName = part.getFileName();
                // 文件名解码
                fileName = MimeUtility.decodeText(fileName);
                // 根据附件名创建一个File文件
                File file = new File("d:/data/" + fileName);
                // 查看是否有当前文件
                Boolean b = file.exists();
                if (!b) {
                    out = new FileOutputStream(file);
                    int data;
                    // 循环读写
                    while ((data = in.read()) != -1) {
                        out.write(data);
                    }
                    System.out.println("附件：【" + fileName + "】下载完毕，保存路径为：" + file.getPath());
                }

                // 关流
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

            /**
             * 获取超文本复合内容
             * 他本是又是一个Multipart容器
             * 此时邮件会分为TEXT（纯文本）正文和HTML正文（HTML标签元素）
             */
            if (type.equals("multipart/alternative")) {
                Multipart m = (Multipart) part.getContent();
                for (int k = 0; k < m.getCount(); k++) {
                    if (m.getBodyPart(k).getContentType().startsWith("text/plain")) {
                        // 处理文本正文
                        System.out.println("TEXT文本内容：" + "\n" + m.getBodyPart(k).getContent().toString().trim() + "\n");
                    } else {
                        // 处理 HTML 正文
                        System.out.println("HTML文本内容：" + "\n" + m.getBodyPart(k).getContent() + "\n");
                    }
                }
            }
        }

        /**
         * 最后千万别忘记了关闭
         */
        folder.close(false); // false为不更新邮件，true为更新，一般在删除邮件后使用
        store.close();
    }

    static class MyAuthenricator extends Authenticator{
        String user=null;
        String pass="";
        public MyAuthenricator(String user,String pass){
            this.user=user;
            this.pass=pass;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user,pass);
        }

    }

    public void receiveMail() throws MessagingException, IOException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");       // 协议
        props.setProperty("mail.pop3.port", "110");             // 端口
        props.setProperty("mail.pop3.host", "pop.exmail.qq.com");    // pop.exmail.qq.com服务器

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        try{
            Store store = session.getStore("pop3");
            store.connect("exhibitor@stonefair.org.cn", "Jhxsf2015~");

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message msg[] = folder.getMessages();

            //Integer msgCount = msg.length;
            for(int i = 0, msgCount = msg.length; i < msgCount; i++){
                System.out.println("第" + i + "封邮件主题：" + msg[i].getSubject() + ", 邮件地址：" + msg[i].getFileName());
            }

            folder.close(true);
            store.close();

            System.out.println("Email received successfully!");
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

    public static void receiveEmail() throws Exception {
        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "pop3");       // 协议
        props.setProperty("mail.pop3.port", "110");             // 端口
        props.setProperty("mail.pop3.host", "pop.exmail.qq.com");    // pop.exmail.qq.com服务器

        // 创建Session实例对象
        Session session = Session.getInstance(props);
        Store store = session.getStore("pop3");
        store.connect("exhibitor@stonefair.org.cn", "Jhxsf2015~");

        // 获得收件箱
        Folder folder = store.getFolder("INBOX");
        /* Folder.READ_ONLY：只读权限
         * Folder.READ_WRITE：可读可写（可以修改邮件的状态）
         */
        folder.open(Folder.READ_WRITE); //打开收件箱

        // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
        System.out.println("未读邮件数: " + folder.getUnreadMessageCount());

        // 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0
        System.out.println("删除邮件数: " + folder.getDeletedMessageCount());
        System.out.println("新邮件: " + folder.getNewMessageCount());

        // 获得收件箱中的邮件总数
        System.out.println("邮件总数: " + folder.getMessageCount());

        // 得到收件箱中的所有邮件,并解析
        Message[] messages = folder.getMessages();

        //释放资源
        folder.close(true);
        store.close();
    }

    // main 方法简单测试
    public static void main(String[] args) {
        try {
            //new LookEmailService().receiveEmail();
            new LookEmailService().receiveMail();
            //new LookEmailService().lookEmail();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
