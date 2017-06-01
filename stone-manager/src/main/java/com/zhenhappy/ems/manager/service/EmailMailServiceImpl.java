package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.dao.SendMailDetailDao;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.manager.action.user.ExhibitorInvisitorMailUtil;
import com.zhenhappy.ems.manager.action.user.PDFReaderUtil;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.service.EmailMailService;
import com.zhenhappy.ems.service.VisitorLogMailService;
import com.zhenhappy.util.Page;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * 封装 Spring 集成的邮件发送服务实现类
 * <p/>
 * 支持异步发送 利用Spring框架封装的JavaMail现实同步或异步邮件发送
 * spring 会负责每次发送后正确关闭 transport
 *
 * @author 吴剑斌
 * @project spring-mail
 * @create 2012-3-24 上午12:29:07
 */
@Service("emailMailService")
public class EmailMailServiceImpl implements EmailMailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailMailServiceImpl.class);

    @Autowired
    JavaMailSender mailSender;// 注入Spring封装的javamail，Spring的xml中已让框架装配
    @Autowired
    TaskExecutor taskExecutor;// 注入Spring封装的异步执行器
    @Autowired
    FreeMarkerConfigurer freeMarker;// 注入FreeMarker模版封装框架

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendMailDetailDao sendMailDetailDao;
    @Autowired
    VisitorLogMailService visitorLogMailService;
    @Autowired
    private TagManagerService tagManagerService;

    /**
     * 同步发送
     */
    public void sendMailBySynchronizationMode(Email email) throws Exception {
        if (email.getReceivers() == null) {
            throw new IllegalArgumentException("收件人不能为空");
        }
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "utf-8");
        TVisitorMailLog visitorMailLog = new TVisitorMailLog();
        StringBuffer emailBuffer = new StringBuffer();
        emailBuffer.append(email.getReceivers());
        if(StringUtil.isNotEmpty(email.getBackupReceivers())){
            emailBuffer.append(";" + email.getBackupReceivers());
        }
        try {
            //String receivers = email.getReceivers().replaceAll("\\;", ",");
            String receivers = emailBuffer.toString().replaceAll("\\;", ",");
            // 设置收件人，寄件人
            InternetAddress[] toAddress = InternetAddress.parse(receivers);
            mailMessage.setRecipients(Message.RecipientType.TO, toAddress); // 发送给多个账号
            //messageHelper.setFrom("hxscsd@163.com"); // 发件人
            if(email.getUseTemplate()) {
                messageHelper.setFrom("info@buddhafair.com");
                visitorMailLog.setMailFrom("info@buddhafair.com");
            } else {
                messageHelper.setFrom("do-not-reply@stonefair.org.cn");
                visitorMailLog.setMailFrom("do-not-reply@stonefair.org.cn");
            }
            if(email.getEmailType() ==0){
                if(email.getCountry() == 0){
                    visitorMailLog.setMailSubject(email.getMail_register_subject_cn_unpro());
                    messageHelper.setSubject(email.getMail_register_subject_cn_unpro()); // 主题
                }else {
                    visitorMailLog.setMailSubject(email.getMail_register_subject_en_unpro());
                    messageHelper.setSubject(email.getMail_register_subject_en_unpro()); // 主题
                }
            } else {
                if(email.getCountry() == 0){
                    visitorMailLog.setMailSubject(email.getRegister_subject_cn());
                    messageHelper.setSubject(email.getRegister_subject_cn()); // 主题
                }else {
                    visitorMailLog.setMailSubject(email.getRegister_subject_en());
                    messageHelper.setSubject(email.getRegister_subject_en()); // 主题
                }
            }
            // true 表示启动HTML格式的邮件
            if(email.getUseTemplate()) {
                visitorMailLog.setMailContent(getMailText(email));
                messageHelper.setText(getMailText(email), true); // 邮件内容，注意加参数true，表示启用html格式
            } else {
                if(email.getEmailType() ==0){
                    if(email.getCountry() == 0){
                        email.setMail_register_content_cn_unpro(email.getMail_register_content_cn_unpro().replace("@@_NAME_@@",email.getName()));
                        visitorMailLog.setMailContent(email.getMail_register_content_cn_unpro());
                        messageHelper.setText(email.getMail_register_content_cn_unpro(), true); // 邮件内容，注意加参数true，表示启用html格式
                    } else {
                        email.setMail_register_content_en_unpro(email.getMail_register_content_en_unpro().replace("@@_NAME_@@",email.getName()));
                        visitorMailLog.setMailContent(email.getMail_register_content_en_unpro());
                        messageHelper.setText(email.getMail_register_content_en_unpro(), true); // 邮件内容，注意加参数true，表示启用html格式
                    }
                } else {
                    if(email.getCountry() == 0){
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_NAME_@@",email.getName()));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_FAIRNAME_@@","第十七届中国厦门国际石材展览会"));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_COMPANY_@@",email.getCompany()));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_NAME_@@",email.getName()));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_POSITION_@@",email.getPosition()));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_QRCODEURL_@@","http://www.stonefair.org.cn/UploadFile/QRCode/" + email.getCheckingNo() + ".jpg"));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_CHECKINGNUMBER_@@",email.getRegID()));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_YEAR_@@","2017"));
                        email.setRegister_content_cn(email.getRegister_content_cn().replace("@@_POLICY_DECLARE_@@",email.getPoliceDecareCn()));
                        visitorMailLog.setMailContent(email.getRegister_content_cn());
                        messageHelper.setText(email.getRegister_content_cn(), true); // 邮件内容，注意加参数true，表示启用html格式
                    } else {
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_NAME_@@",email.getName()));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_FAIRNAME_@@","Xiamen Stone Fair 2017"));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_COMPANY_@@",email.getCompany()));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_NAME_@@",email.getName()));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_POSITION_@@",email.getPosition()));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_QRCODEURL_@@","http://www.stonefair.org.cn/UploadFile/QRCode/" + email.getCheckingNo() + ".jpg"));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_CHECKINGNUMBER_@@",email.getRegID()));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_YEAR_@@","2017"));
                        email.setRegister_content_en(email.getRegister_content_en().replace("@@_POLICY_DECLARE_@@",email.getPoliceDecareEn()));
                        visitorMailLog.setMailContent(email.getRegister_content_en());
                        messageHelper.setText(email.getRegister_content_en(), true); // 邮件内容，注意加参数true，表示启用html格式
                    }
                }
            }

            visitorMailLog.setCustomerID(email.getCustomerId());
            visitorMailLog.setCreateIP("");
            visitorMailLog.setReply(0);
            visitorMailLog.setStatus(0);
            visitorMailLog.setGUID("");
            visitorMailLog.setCreateTime(new Date());
            visitorMailLog.setLogContent("");
            visitorMailLog.setLogSubject("");

            for(int i = 0; i < toAddress.length; ++i) {
                Address address = toAddress[i];
                visitorMailLog.setMailTo(address.toString());
                visitorLogMailService.insertLogMail(visitorMailLog);
            }

            Properties props = System.getProperties();
            props.put("mail.smtp.auth", "true");
            props.put("username", "do-not-reply@stonefair.org.cn");
            props.put("password", "Jhxsf2015~");
            props.put("mail.debug", "true");
            props.setProperty("mail.debug", "true");
            // 发送邮件
            mailSender.send(mailMessage);

        } catch (MessagingException e) {
            throw e;
        }
    }

    public void sendInvisitorMailByAsynchronousMode(ExhibitorInvisitorEmail exhibitorInvisitorEmail) throws Exception {
        if (exhibitorInvisitorEmail.getContactList() == null || exhibitorInvisitorEmail.getContactList().size() == 0) {
            throw new IllegalArgumentException("收件人不能为空");
        }

        PDFReaderUtil pdfReaderUtil = new PDFReaderUtil();
        pdfReaderUtil.writePDF(pdfReaderUtil.exhibitorInvitationInputPath,
                pdfReaderUtil.exhibitorInvitationSavePath + "Booth Confirmation of Xiamen Stone Fair 2017.pdf",
                exhibitorInvisitorEmail.getCompanyName(), exhibitorInvisitorEmail.getUserName(), exhibitorInvisitorEmail.getPassword(),
                pdfReaderUtil.exhibitorSelectPath,
                exhibitorInvisitorEmail.getBoothNumber(), exhibitorInvisitorEmail.getExhibitorArea(),
                exhibitorInvisitorEmail.getRawSpaceOrShellScheme());

        ExhibitorInvisitorMailUtil exhibitorInvisitorMailUtil = new ExhibitorInvisitorMailUtil();
        exhibitorInvisitorMailUtil.setHost("smtp.exmail.qq.com");// 邮件发送服务器
        exhibitorInvisitorMailUtil.setFrom(exhibitorInvisitorEmail.getSendEamilAccount());
        exhibitorInvisitorMailUtil.setSubject("Booth Confirmation of Xiamen Stone Fair 2017");
        exhibitorInvisitorMailUtil.setUserName(exhibitorInvisitorEmail.getSendEamilAccount());
        exhibitorInvisitorMailUtil.setPassWord(exhibitorInvisitorEmail.getSendEmailPassword());

        for(TContact tContact:exhibitorInvisitorEmail.getContactList()) {
            if(tContact.getEmail().contains(",")){
                String[] emailList = tContact.getEmail().split(",");
                for(String emailTemp: emailList){
                    exhibitorInvisitorMailUtil.setTo(emailTemp);
                    Template template = freeMarker.getConfiguration().getTemplate("mail/email_content.html");

                    // FreeMarker通过Map传递动态数据
                    Map<Object, Object> model = new HashMap<Object, Object>();
                    model.put("exhibitorName", tContact.getName()); // 注意动态数据的key和模板标签中指定的属性相匹配

                    // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
                    String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    exhibitorInvisitorMailUtil.setContent(emailContent);

                    exhibitorInvisitorMailUtil.attachfile(pdfReaderUtil.exhibitorInvitationSavePath + "Booth Confirmation of Xiamen Stone Fair 2017.pdf");
                    exhibitorInvisitorMailUtil.attachfile(pdfReaderUtil.exhibitorConfirmFilePath);
                    exhibitorInvisitorMailUtil.sendMail();
                }
            }else{
                exhibitorInvisitorMailUtil.setTo(tContact.getEmail());
                Template template = freeMarker.getConfiguration().getTemplate("mail/email_content.html");

                // FreeMarker通过Map传递动态数据
                Map<Object, Object> model = new HashMap<Object, Object>();
                model.put("exhibitorName", tContact.getName()); // 注意动态数据的key和模板标签中指定的属性相匹配

                // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
                String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                exhibitorInvisitorMailUtil.setContent(emailContent);

                exhibitorInvisitorMailUtil.attachfile(pdfReaderUtil.exhibitorInvitationSavePath + "Booth Confirmation of Xiamen Stone Fair 2017.pdf");
                exhibitorInvisitorMailUtil.attachfile(pdfReaderUtil.exhibitorConfirmFilePath);
                exhibitorInvisitorMailUtil.sendMail();
            }
        }
    }

    /**
     * 异步发送
     */
    public void sendMailByAsynchronousMode(final Email email, Integer eid) {
        if (logger.isDebugEnabled()) {
            logger.debug("当前邮件采取异步发送..");
        }
        final Integer id = saveSendDetail(email, eid);
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    sendMailBySynchronizationMode(email);
                    updateResult(true, id);
                    logger.info("邮件发送耗时任务完成");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    updateResult(false, id);
                }
            }
        });
    }

    public void retrySendMailByAsynchronousMode(final Email email, final Integer emailId) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    sendMailBySynchronizationMode(email);
                    updateResult(true, emailId);
                    logger.info("邮件发送耗时任务完成");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    updateResult(false, emailId);
                }
            }
        });
    }

    @Transactional
    private void updateResult(boolean sendResult, Integer detailId) {
        jdbcTemplate.update("update t_email_send_detail set result=? where id = ?", new Object[]{sendResult ? 1 : 0, detailId});
    }

    @Transactional
    private Integer saveSendDetail(Email email, Integer eid) {
        TEmailSendDetail detail = new TEmailSendDetail();
        detail.setEid(eid);
        detail.setAddress(email.getReceivers());
        detail.setCompanyName(email.getName());
        detail.setSendDate(new Date());
        detail.setLanguage(email.getFlag());
        detail.setGender(email.getGender());
        hibernateTemplate.save(detail);
        return detail.getId();
    }

    /**
     * 或者直接使用 spring 3.0 的异步框架 只需使用 @Async 注解, 这里的代码直接会用异步线程来运行。
     * 需要激活 <!-- 注解异步任务驱动 -->
     * <task:annotation-driven/>
     * 详细配置请见： spring-mail.xml
     */
    @Async
    public void sendMailByAsyncAnnotationMode(final Email email) {
        try {
            logger.debug("当前邮件采取spring 3 @Async注解异步任务驱动发送..");
            sendMailBySynchronizationMode(email);
            logger.info("邮件发送耗时任务完成");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendMailByAsyncAnnotationMode(EmailExtent email) {

    }

    @Override
    public void loadDetailByEid(Page page, Integer eid) {
        page.setDatas(sendMailDetailDao.queryPageByHQL("select count(*) from TEmailSendDetail where eid=?", "from TEmailSendDetail where eid=? order by sendDate desc", new Object[]{eid}, page));
    }

    @Override
    public TEmailSendDetail loadMailByMid(Integer mid, Integer eid) {
        List<TEmailSendDetail> results = sendMailDetailDao.queryByHql("from TEmailSendDetail where id=? and eid=?",new Object[]{mid,eid});
        if(results.size()>0){
            return results.get(0);
        }else{
            return null;
        }
    }

    private String getExhibitorInvisitorMailText(Email email) throws Exception {
        // 通过指定模板名获取FreeMarker模板实例
        Template template = freeMarker.getConfiguration().getTemplate("mail/VisitorReplay.html");

        // FreeMarker通过Map传递动态数据
        Map<Object, Object> model = new HashMap<Object, Object>();
        model.put("email", email); // 注意动态数据的key和模板标签中指定的属性相匹配

        // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
        String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return htmlText;
    }

    /**
     * 通过模板构造邮件内容，参数content将替换模板文件中的${content}标签。
     */
    private String getMailText(Email email) throws Exception {
        // 通过指定模板名获取FreeMarker模板实例
        Template template = null;
        if (email.getFlag() == 1 && email.getCountry() == 0) {
            template = freeMarker.getConfiguration().getTemplate("mail/VisitorReplay.html");
        }else if (email.getFlag() == 1 && email.getCountry() == 1){
            template = freeMarker.getConfiguration().getTemplate("mail/VisitorReplay_eng.html");
        }else if (email.getFlag() == 0 && email.getCountry() == 0){
            template = freeMarker.getConfiguration().getTemplate("mail/VisitorReplay_unPro.html");
        }else if (email.getFlag() == 0 && email.getCountry() == 1){
            template = freeMarker.getConfiguration().getTemplate("mail/VisitorReplay_unPro_eng.html");
        }

        // FreeMarker通过Map传递动态数据
        Map<Object, Object> model = new HashMap<Object, Object>();
        model.put("email", email); // 注意动态数据的key和模板标签中指定的属性相匹配

        // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
        String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return htmlText;
    }

    /*
     * (non-Javadoc) 发送邮件的具体实现, 目前是异步发送
     *
     * @see
     * com.haohui.b2b.service.mail.MailService#sendHtmlEmails(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public void sendHtmlEmails(String receivers, String subject, String content) {
        this.sendMailByAsynchronousMode(receivers, subject, content);
    }

    /**
     * 异步发送
     *
     */
    public void sendMailByAsynchronousMode(final String receivers, final String subject, final String content) {
        if (logger.isDebugEnabled()) {
            logger.debug("当前邮件采取异步发送..");
        }
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    sendMailBySynchronizationMode(receivers, subject, content);
                    logger.info("邮件发送耗时任务完成");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    /**
     * 同步发送
     *
     */

    public void sendMailBySynchronizationMode(String receivers, String subject, String content) throws Exception {
        if (receivers == null) {
            throw new IllegalArgumentException("收件人不能为空");
        }
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "utf-8");

        try {
            receivers = receivers.replaceAll("\\;", ",");
            // 设置收件人，寄件人
            InternetAddress[] toAddress = InternetAddress.parse(receivers);
            mailMessage.setRecipients(Message.RecipientType.TO, toAddress); // 发送给多个账号
            messageHelper.setFrom("hxscsd@163.com"); // 发件人
            messageHelper.setSubject(subject); // 主题
            // true 表示启动HTML格式的邮件
            messageHelper.setText(content, true); // 邮件内容，注意加参数true，表示启用html格式

            // 发送邮件
            mailSender.send(mailMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 或者直接使用 spring 3.0 的异步框架 只需使用 @Async 注解即可。
     * 需要激活 <!-- 注解异步任务驱动 -->
     * <task:annotation-driven/>
     * 详细配置请见： spring-mail.xml
     */
    @Async
    public void sendAsync() {
        System.out.println("###### 或者直接采用 spring 3.0 的异步任务注解, 这里的代码直接会用异步线程来运行 #######");
    }
}
