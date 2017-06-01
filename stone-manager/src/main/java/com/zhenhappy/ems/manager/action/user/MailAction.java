package com.zhenhappy.ems.manager.action.user;

import com.alibaba.fastjson.JSONArray;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.lowagie.text.pdf.BaseFont;
import com.zhenhappy.ems.dao.CustomerApplyEmailInfoDao;
import com.zhenhappy.ems.dao.CustomerApplyMsgInfoDao;
import com.zhenhappy.ems.dao.CustomerInfoDao;
import com.zhenhappy.ems.dao.ExhibitorDao;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.dto.Principle;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dao.companyinfo.HistoryCustomerInfoDao;
import com.zhenhappy.ems.manager.dto.GetMailSendDetailsResponse;
import com.zhenhappy.ems.manager.dto.ManagerPrinciple;
import com.zhenhappy.ems.manager.dto.PreviewExhibitorInvitationResponse;
import com.zhenhappy.ems.manager.entity.PreviewExhibitorInvitation;
import com.zhenhappy.ems.manager.entity.TExhibitorBooth;
import com.zhenhappy.ems.manager.entity.companyinfo.THistoryCustomer;
import com.zhenhappy.ems.manager.service.ContactManagerService;
import com.zhenhappy.ems.manager.service.CustomerInfoManagerService;
import com.zhenhappy.ems.manager.service.CustomerTemplateService;
import com.zhenhappy.ems.manager.service.ExhibitorManagerService;
import com.zhenhappy.ems.manager.service.companyinfo.HistoryCustomerService;
import com.zhenhappy.ems.manager.service.lookmailinfo.showEmail;
import com.zhenhappy.ems.manager.sys.Constants;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.service.EmailMailService;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.ems.service.VisitorLogMsgService;
import com.zhenhappy.ems.service.managerrole.TUserInfoService;
import com.zhenhappy.ems.service.managerrole.TUserRoleService;
import com.zhenhappy.system.SystemConfig;
import com.zhenhappy.util.EmailPattern;
import com.zhenhappy.util.Page;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lianghaijian on 2014-08-12.
 */
@Controller
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
@RequestMapping(value = "user")
public class MailAction extends BaseAction {

    private static Logger log = Logger.getLogger(MailAction.class);

    @Autowired
    private ExhibitorService exhibitorService;
    @Autowired
    private ExhibitorManagerService exhibitorManagerService;
    @Autowired
    private ContactManagerService contactManagerService;
    @Autowired
    private CustomerInfoManagerService customerInfoManagerService;
    @Autowired
    private EmailMailService mailService;
    @Autowired
    private CustomerTemplateService customerTemplaeService;
    // 注入Spring封装的异步执行器
    @Autowired
    TaskExecutor taskExecutor;
    @Autowired
    VisitorLogMsgService visitorLogMsgService;
    @Autowired
    private CustomerApplyEmailInfoDao customerApplyEmailInfoDao;
    @Autowired
    private CustomerApplyMsgInfoDao customerApplyMsgInfoDao;
    @Autowired
    FreeMarkerConfigurer freeMarker;// 注入FreeMarker模版封装框架
    @Autowired
    private ExhibitorDao exhibitorDao;
    @Autowired
    private TUserRoleService roleService;
    @Autowired
    private TUserInfoService userInfoService;
    @Autowired
    private HistoryCustomerService historyCustomerService;
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private HistoryCustomerInfoDao historyCustomerInfoDao;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    JavaMailSender mailSender;

    @RequestMapping(value = "sendMail", method = RequestMethod.GET)
    public String sendMail() {
        return "/user/mail/mails";
    }

    @RequestMapping(value = "sendMail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse sendMail(@RequestParam(value = "context") String context,
                                 @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Email> emails = JSONArray.parseArray(context, Email.class);
            String booth = exhibitorService.loadBoothNum(principle.getExhibitor().getEid());
            String company = exhibitorService.query(principle.getExhibitor().getEid()).getCompany();
            String companye = exhibitorService.query(principle.getExhibitor().getEid()).getCompanyEn();
            /*String company = exhibitorService.getExhibitorByEid(principle.getExhibitor().getEid()).getCompany();
            String companye = exhibitorService.getExhibitorByEid(principle.getExhibitor().getEid()).getCompanye();*/
            for (Email email : emails) {
                if (email.getFlag() == 1) {
                    email.setSubject(company + "邀请函");
                    email.setBoothNumber(booth);
                    email.setCompany(company);
                } else {
                    email.setSubject("The invitation Of " + companye);
                    email.setBoothNumber(booth);
                    email.setCompany(companye);
                }
                mailService.sendMailByAsynchronousMode(email, principle.getExhibitor().getEid());
            }
        } catch (Exception e) {
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    private Email initEmailTemplate() {
        Email email = new Email();
        List<TVisitorTemplate> customerTemplatesList = customerTemplaeService.loadAllCustomerTemplate();
        if(customerTemplatesList != null && customerTemplatesList.size()>0){
            for(int k=0;k<customerTemplatesList.size();k++) {
                TVisitorTemplate customerTemplate = customerTemplatesList.get(k);
                if (customerTemplate.getTpl_key().equals("mail_register_subject_cn")) {
                    email.setRegister_subject_cn(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_content_cn")) {
                    email.setRegister_content_cn(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_invite_subject_cn")) {
                    email.setInvite_subject_cn(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_invite_content_cn")) {
                    email.setInvite_content_cn(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_subject_en")) {
                    email.setRegister_subject_en(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_content_en")) {
                    email.setRegister_content_en(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_invite_subject_en")) {
                    email.setInvite_subject_en(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_invite_content_en")) {
                    email.setInvite_content_en(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_policyDeclare_cn")) {
                    email.setPoliceDecareCn(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_policyDeclare_en")) {
                    email.setPoliceDecareEn(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_subject_cn_unpro")) {
                    email.setMail_register_subject_cn_unpro(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_content_cn_unpro")) {
                    email.setMail_register_content_cn_unpro(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_subject_en_unpro")) {
                    email.setMail_register_subject_en_unpro(customerTemplate.getTpl_value());
                }
                if (customerTemplate.getTpl_key().equals("mail_register_content_en_unpro")) {
                    email.setMail_register_content_en_unpro(customerTemplate.getTpl_value());
                }
            }
        }
        return email;
    }

    public void deleteEmailFailure(List<WCustomer> customerList) throws Exception{
        String host = ExhibitorInvisitorMailUtil.emailCustomerHost;
        String username = ExhibitorInvisitorMailUtil.emailCustomerUserName;
        String password = ExhibitorInvisitorMailUtil.emailCustomerPassword;
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imap");
        store.connect(host, username, password);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        Message[] messages = folder.getMessages(folder.getMessageCount() - folder.getUnreadMessageCount() + 1,folder.getMessageCount());
        HashMap<String, Integer> sendEmailFailureMap = customerInfoManagerService.getSendEmailFailureMap();

        for (int i = 0; i < messages.length; i++) {
            showEmail re = new showEmail((MimeMessage) messages[i]);
            String toContact = re.getMailAddress("to");
            if(toContact.indexOf("do-not-reply") >= 0){
                re.getMailContent((Part) messages[i]);
                String bodyContent = re.getBodyText();
                if(re.getSubject().indexOf("stonefair.org.cn的退信") >=0){
                    int emailBegin = bodyContent.indexOf("无法发送到");
                    if(emailBegin >= 0){
                        String emailTo = bodyContent.substring(emailBegin + 5);
                        int emailEndIndex = emailTo.indexOf("</td>");
                        if(emailEndIndex >= 0) {
                            String emailValue = emailTo.substring(0, emailEndIndex);
                            for(WCustomer customer:customerList){
                                if(sendEmailFailureMap != null){
                                    Integer deleteCustomerId = sendEmailFailureMap.get(emailValue);
                                    if(String.valueOf(deleteCustomerId).equalsIgnoreCase(String.valueOf(customer.getId()))){
                                        messages[i].setFlag(Flags.Flag.DELETED, true);
                                        messages[i].saveChanges();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (folder != null)
            folder.close(true);
        if (store != null)
            store.close();
    }

    @ResponseBody
    @RequestMapping(value = "emailSendEmailFailureStoneCustomers", method = RequestMethod.POST)
    public BaseResponse emailSendEmailFailureStoneCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                            @RequestParam(value="deleteEmailFlag") Integer deleteEmailFlag,
                                                     @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        if(deleteEmailFlag == 1){
            List<WCustomer> customerList = new ArrayList<WCustomer>();
            if(cids[0] == -1){
                customerList = customerInfoManagerService.loadAllInlandCustomer();
            }else{
                customerList = customerInfoManagerService.loadSelectedCustomers(cids);
            }
            try{
                deleteEmailFailure(customerList);
            }catch (Exception e){
                log.info("====重新给客商发邮件后，删除未读邮件失败====");
            }
        }
        baseResponse = emailAllInlandStoneCustomers(cids, principle);
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "emailAllInlandStoneCustomers", method = RequestMethod.POST)
    public BaseResponse emailAllInlandStoneCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                     @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<WCustomer> customers = new ArrayList<WCustomer>();
        try {
            if(cids[0] == -1){
                customers = customerInfoManagerService.loadAllInlandCustomer();
            }
            else if(cids[0] == -2) {
                List<VApplyEmail> customerApplyEmailList = customerApplyEmailInfoDao.queryByHql("from VApplyEmail where status=?", new Object[]{0});
                if(customerApplyEmailList !=null && customerApplyEmailList.size()>0) {
                    for (int m=0;m<customerApplyEmailList.size();m++){
                        VApplyEmail applyEmail = customerApplyEmailList.get(m);
                        WCustomer customer = customerInfoManagerService.loadCustomerInfoById(applyEmail.getCustomerID());
                        customers.add(customer);
                    }
                }
            }
            else {
                customers = customerInfoManagerService.loadSelectedCustomers(cids);
            }
            if(customers.size()>0) {
                //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                for(int i=0;i<customers.size();i++) {
                    Email email = initEmailTemplate();
                    WCustomer customer = customers.get(i);
                    EmailPattern pattern = new EmailPattern();
                    if(customer != null && pattern.isEmailPattern(customer.getEmail())) {
                        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
                        Date date = new Date();
                        String str = bartDateFormat.format(date);

                        /*ReadWriteEmailAndMsgFile.setFileContentIsNull();
                        ReadWriteEmailAndMsgFile.readTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                        ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给邮箱为：" + customer.getEmail() + "账号发邮件。", ReadWriteEmailAndMsgFile.stoneEmailFileName);*/

                        if(StringUtil.isNotEmpty(customer.getBackupEmail())){
                            email.setBackupReceivers(customer.getBackupEmail());
                            //ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给邮箱为：" + customer.getBackupEmail() + "账号发邮件。", ReadWriteEmailAndMsgFile.stoneEmailFileName);
                        }
                        //log.info("======给境内邮箱为：" + customer.getEmail() + "账号发邮件======");
                        if(customer.getIsProfessional() == 1) {
                            email.setFlag(1);//专业采购商
                        } else {
                            email.setFlag(0);//展会观众
                        }

                        email.setEmailType(customer.getIsProfessional());
                        email.setCheckingNo(customer.getCheckingNo());
                        email.setCustomerId(customer.getId());
                        email.setCountry(customer.getCountry() == 44 ? 0:1);
                        email.setUseTemplate(false);
                        email.setCompany(customer.getCompany());
                        email.setName(customer.getFirstName());
                        if(customer.getPosition() == null || customer.getPosition() == ""){
                            email.setPosition("");
                        } else {
                            email.setPosition(customer.getPosition());
                        }
                        email.setRegID(customer.getCheckingNo());
                        email.setReceivers(customer.getEmail());
                        //email.setReceivers("datea120@163.com");

                        customerInfoManagerService.updateCustomerEmailNum(customer.getId());
                        if(StringUtil.isNotEmpty(customer.getBackupEmail())){
                            customerInfoManagerService.updateCustomerEmailNum(customer.getId());
                        }
                        mailService.sendMailByAsyncAnnotationMode(email);

                        List<VApplyEmail> customerApplyEmailList = customerApplyEmailInfoDao.queryByHql("from VApplyEmail where CustomerID=?", new Object[]{customer.getId()});
                        if(customerApplyEmailList != null && customerApplyEmailList.size()>0){
                            for(int k=0;k<customerApplyEmailList.size();k++){
                                VApplyEmail applyEmail = customerApplyEmailList.get(k);
                                if(principle.getAdmin() != null){
                                    applyEmail.setAdmin(principle.getAdmin().getName());
                                }
                                applyEmail.setStatus(1);
                                applyEmail.setConfirmTime(new Date());
                                applyEmail.setConfirmIP(InetAddress.getLocalHost().getHostAddress());
                                customerApplyEmailInfoDao.update(applyEmail);
                            }
                        }
                    }
                }
            } else {
                throw new Exception("Mail can not found");
            }
        } catch (Exception e) {
            System.out.println("=====exception: " + e);
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "emailAllStoneIsActivedCustomers", method = RequestMethod.POST)
    public BaseResponse emailAllStoneIsActivedCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                     @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<WCustomer> customers = new ArrayList<WCustomer>();
        try {
            if(cids[0] == -1){
                customers = customerInfoManagerService.loadAllInlandIsActivedCustomer();
            }else {
                customers = customerInfoManagerService.loadSelectedCustomers(cids);
            }
            if(customers.size()>0) {
                for(int i=0;i<customers.size();i++) {
                    Email email = initEmailTemplate();
                    WCustomer customer = customers.get(i);
                    EmailPattern pattern = new EmailPattern();
                    if(customer != null && pattern.isEmailPattern(customer.getEmail())) {
                        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
                        Date date = new Date();
                        String str = bartDateFormat.format(date);

                        if(StringUtil.isNotEmpty(customer.getBackupEmail())){
                            email.setBackupReceivers(customer.getBackupEmail());
                        }
                        //log.info("======给境内邮箱为：" + customer.getEmail() + "账号发邮件======");
                        if(customer.getIsProfessional() == 1) {
                            email.setFlag(1);//专业采购商
                        } else {
                            email.setFlag(0);//展会观众
                        }

                        email.setEmailType(customer.getIsProfessional());
                        email.setCheckingNo(customer.getCheckingNo());
                        email.setCustomerId(customer.getId());
                        email.setCountry(customer.getCountry() == 44 ? 0:1);
                        email.setUseTemplate(false);
                        email.setCompany(customer.getCompany());
                        email.setName(customer.getFirstName());
                        if(customer.getPosition() == null || customer.getPosition() == ""){
                            email.setPosition("");
                        } else {
                            email.setPosition(customer.getPosition());
                        }
                        email.setRegID(customer.getCheckingNo());
                        email.setReceivers(customer.getEmail());

                        customerInfoManagerService.updateCustomerEmailNum(customer.getId());
                        if(StringUtil.isNotEmpty(customer.getBackupEmail())){
                            customerInfoManagerService.updateCustomerEmailNum(customer.getId());
                        }
                        mailService.sendMailByAsyncAnnotationMode(email);

                        /*List<VApplyEmail> customerApplyEmailList = customerApplyEmailInfoDao.queryByHql("from VApplyEmail where CustomerID=?", new Object[]{customer.getId()});
                        if(customerApplyEmailList != null && customerApplyEmailList.size()>0){
                            for(int k=0;k<customerApplyEmailList.size();k++){
                                VApplyEmail applyEmail = customerApplyEmailList.get(k);
                                if(principle.getAdmin() != null){
                                    applyEmail.setAdmin(principle.getAdmin().getName());
                                }
                                applyEmail.setStatus(1);
                                applyEmail.setConfirmTime(new Date());
                                applyEmail.setConfirmIP(InetAddress.getLocalHost().getHostAddress());
                                customerApplyEmailInfoDao.update(applyEmail);
                            }
                        }*/
                    }
                }
            } else {
                throw new Exception("Mail can not found");
            }
        } catch (Exception e) {
            System.out.println("=====exception: " + e);
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "emailAllForeignStoneCustomers", method = RequestMethod.POST)
    public BaseResponse emailAllForeignStoneCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                      @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<WCustomer> customers = new ArrayList<WCustomer>();
        try {
            if(cids[0] == -1){
                customers = customerInfoManagerService.loadAllForeignActiveCustomer(1);
            } else if(cids[0] == -2) {
                List<VApplyEmail> customerApplyEmailList = customerApplyEmailInfoDao.queryByHql("from VApplyEmail where status=?", new Object[]{0});
                if(customerApplyEmailList !=null && customerApplyEmailList.size()>0) {
                    for (int m=0;m<customerApplyEmailList.size();m++){
                        VApplyEmail applyEmail = customerApplyEmailList.get(m);
                        WCustomer customer = customerInfoManagerService.loadCustomerInfoById(applyEmail.getCustomerID());
                        customers.add(customer);
                    }
                }
            } else {
                customers = customerInfoManagerService.loadSelectedCustomers(cids);
            }
            List<TVisitorTemplate> customerTemplatesList = new ArrayList<TVisitorTemplate>();
            if(customers.size()>0) {
                //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                for(int i=0;i<customers.size();i++) {
                    Email email = initEmailTemplate();
                    WCustomer customer = customers.get(i);
                    EmailPattern pattern = new EmailPattern();
                    if(customer != null && pattern.isEmailPattern(customer.getEmail())) {
                        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
                        Date date = new Date();
                        String str = bartDateFormat.format(date);

                        /*ReadWriteEmailAndMsgFile.setFileContentIsNull();
                        ReadWriteEmailAndMsgFile.readTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                        ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给邮箱为：" + customer.getEmail() + "账号发邮件。", ReadWriteEmailAndMsgFile.stoneEmailFileName);*/

                        //log.info("======给境外邮箱为：" + customer.getEmail() + "账号发邮件======");
                        if(customer.getIsProfessional() == 1) {
                            email.setFlag(1);//专业采购商
                        } else {
                            email.setFlag(0);//展会观众
                        }

                        email.setEmailType(customer.getIsProfessional());
                        email.setCheckingNo(customer.getCheckingNo());
                        email.setCustomerId(customer.getId());
                        email.setCountry(customer.getCountry() == 44 ? 0:1);
                        email.setUseTemplate(false);
                        email.setCompany(customer.getCompany());
                        email.setName(customer.getFirstName());
                        if(customer.getPosition() == null || customer.getPosition() == ""){
                            email.setPosition("");
                        } else {
                            email.setPosition(customer.getPosition());
                        }
                        email.setRegID(customer.getCheckingNo());
                        email.setReceivers(customer.getEmail());
                        //email.setReceivers("datea120@163.com");

                        customerInfoManagerService.updateCustomerEmailNum(customer.getId());
                        mailService.sendMailByAsyncAnnotationMode(email);

                        List<VApplyEmail> customerApplyEmailList = customerApplyEmailInfoDao.queryByHql("from VApplyEmail where CustomerID=?", new Object[]{customer.getId()});
                        if(customerApplyEmailList != null && customerApplyEmailList.size()>0){
                            for(int k=0;k<customerApplyEmailList.size();k++){
                                VApplyEmail applyEmail = customerApplyEmailList.get(k);
                                if(principle.getAdmin() != null){
                                    applyEmail.setAdmin(principle.getAdmin().getName());
                                }
                                applyEmail.setStatus(1);
                                applyEmail.setConfirmTime(new Date());
                                applyEmail.setConfirmIP(InetAddress.getLocalHost().getHostAddress());
                                customerApplyEmailInfoDao.update(applyEmail);
                            }
                        }
                    }
                }
            } else {
                throw new Exception("Mail can not found");
            }
        } catch (Exception e) {
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    //获取短信标题
    private String getMsgSubject(Integer content){
        String mobileSubject = "";
        List<TVisitorTemplate> customerTemplatesList = customerTemplaeService.loadAllCustomerTemplate();
        if(customerTemplatesList != null && customerTemplatesList.size()>0){
            for(int k=0;k<customerTemplatesList.size();k++){
                TVisitorTemplate customerTemplate = customerTemplatesList.get(k);
                if(1 == content && customerTemplate.getTpl_key().equals("msg_register_subject_cn")) {
                    mobileSubject = customerTemplate.getTpl_value();
                }else if(2 == content && customerTemplate.getTpl_key().equals("msg_unactive_subject_cn")) {
                    mobileSubject = customerTemplate.getTpl_value();
                }else if(3 == content && customerTemplate.getTpl_key().equals("msg_unregister_subject_cn")) {
                    mobileSubject = customerTemplate.getTpl_value();
                }
            }
        }
        return mobileSubject;
    }

    //获取短信内容
    private String getMsgContent(Integer content){
        String mobileContent = "";
        List<TVisitorTemplate> customerTemplatesList = customerTemplaeService.loadAllCustomerTemplate();
        if(customerTemplatesList != null && customerTemplatesList.size()>0){
            for(int k=0;k<customerTemplatesList.size();k++){
                TVisitorTemplate customerTemplate = customerTemplatesList.get(k);
                if(1 == content && customerTemplate.getTpl_key().equals("msg_register_content_cn")) {
                    mobileContent = customerTemplate.getTpl_value();
                }else if(2 == content && customerTemplate.getTpl_key().equals("msg_unactive_content_cn")) {
                    mobileContent = customerTemplate.getTpl_value();
                }else if(3 == content && customerTemplate.getTpl_key().equals("msg_unregister_content_cn")) {
                    mobileContent = customerTemplate.getTpl_value();
                }
            }
        }
        return mobileContent;
    }

    /**
     * 接收客商短信回复内容
     * @param phone   对应的手机号
     * @param content 回复内容
     * @param type    类别：1：表示客商；2：表示展商
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "msgReturnContent")
    public int msgReturnContent(@RequestParam("phone") String phone,
                                 @RequestParam("content") Integer content,
                                 @RequestParam("type") Integer type) {
        int result = 0;

        if(StringUtil.isNotEmpty(phone)){
            //List<TVisitorTemplate> customerTemplatesList = customerTemplaeService.loadAllCustomerTemplate();
            /*String mobileSubject = getMsgSubject(contentInt);
            String mobileContent = getMsgContent(contentInt);*/

            String mobileSubject = "";
            String mobileContent = "";
            if(1 == content) {
                mobileContent = "您已成功预登记，确认号为@@_CHECKINGNUMBER_@@。凭此号在现场领取专业观众证。厦门石材展展览时间：2017.3.6-9，地址：厦门会展中心，真诚期待您的光临！【金泓信展览】";
            }

            List<WCustomer> wCustomerList = customerInfoManagerService.loadCustomerByPhone(phone);
            if(wCustomerList != null && wCustomerList.size()>0 && 1 == content){
                System.out.println("phone: " + phone + ", content: " + content);
                WCustomer customer = wCustomerList.get(0);
                //如果是已经激活的，则不再发短信
                if(customer.getIsActivated() == 0){
                    //设置2表示短信激活
                    customer.setIsActivated(2);
                    customer.setUpdateTime(new Date());
                    customerInfoDao.update(customer);

                    sendMsgByTelphone(null, customer, null, mobileSubject, mobileContent, phone, content);

                    result = 1;
                }
            }
        }
        return result;
    }

    //type字段：1：已预登记(已激活)；2：表示已预登记(未激活)；3：表示未预登记
    @ResponseBody
    @RequestMapping(value = "msgAllInlandStoneCustomers", method = RequestMethod.POST)
    public BaseResponse msgAllInlandStoneCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                   @RequestParam("type") Integer type,
                                                   @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<WCustomer> customers = new ArrayList<WCustomer>();
        List<THistoryCustomer> tHistoryCustomerList = new ArrayList<THistoryCustomer>();
        try {
            if(cids[0] == -1){
                if(3 == type){
                    TUserInfo userInfo = (TUserInfo) principle.getAdmin();
                    TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
                    tHistoryCustomerList = historyCustomerService.loadAllHistoryInlandCustomerByUserInfoAndUserRole(userInfo1);
                }else{
                    customers = customerInfoManagerService.loadAllInlandCustomer();
                }
            } else if(cids[0] == -2) {
                List<VApplyMsg> customerApplyMsgList = customerApplyMsgInfoDao.queryByHql("from VApplyMsg where status=?", new Object[]{0});
                if(customerApplyMsgList !=null && customerApplyMsgList.size()>0) {
                    for (int m=0;m<customerApplyMsgList.size();m++){
                        VApplyMsg applyMsg = customerApplyMsgList.get(m);
                        WCustomer customer = customerInfoManagerService.loadCustomerInfoById(applyMsg.getCustomerID());
                        customers.add(customer);
                    }
                }
            } else {
                if(3 == type){
                    tHistoryCustomerList = historyCustomerService.loadSelectedHistoryCustomers(cids);
                }else{
                    customers = customerInfoManagerService.loadSelectedCustomers(cids);
                }
            }
            String mobileSubject = getMsgSubject(type);
            String mobileContent = getMsgContent(type);
            if(3 == type) {
                if(tHistoryCustomerList.size()>0) {
                    StringBuffer mobileStr = new StringBuffer();
                    //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneMsgFileName);
                    for(int i=0;i<tHistoryCustomerList.size();i++) {
                        THistoryCustomer tHistoryCustomer = tHistoryCustomerList.get(i);
                        EmailPattern pattern = new EmailPattern();
                        if(tHistoryCustomer != null) {
                            String telphoneTemp = tHistoryCustomer.getTelphone();
                            if(telphoneTemp.contains("/")){
                                String[] telphoneArray = telphoneTemp.split("/");
                                for(String telphoneValue:telphoneArray){
                                    if(pattern.isMobileNO(telphoneValue)){
                                        sendMsgByTelphone(principle, null, tHistoryCustomer, mobileSubject, mobileContent, telphoneValue, type);
                                    }
                                }
                            }else{
                                if(pattern.isMobileNO(telphoneTemp)){
                                    sendMsgByTelphone(principle, null, tHistoryCustomer, mobileSubject, mobileContent, telphoneTemp, type);
                                }
                            }
                        }
                        tHistoryCustomer.setUpdatetime(new Date());
                        historyCustomerInfoDao.update(tHistoryCustomer);
                    }
                } else {
                    throw new Exception("mobile can not found");
                }
            }else{
                if(customers.size()>0) {
                    StringBuffer mobileStr = new StringBuffer();
                    //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneMsgFileName);
                    for(int i=0;i<customers.size();i++) {
                        TVisitorMsgLog visitorMsgLog = new TVisitorMsgLog();
                        WCustomer customer = customers.get(i);
                        EmailPattern pattern = new EmailPattern();
                        if(customer != null) {
                            String telphoneTemp = customer.getMobilePhone();
                            if(telphoneTemp != null && StringUtil.isNotEmpty(telphoneTemp)){
                                if(telphoneTemp.contains("/")){
                                    String[] telphoneArray = telphoneTemp.split("/");
                                    for(String telphoneValue:telphoneArray){
                                        if(pattern.isMobileNO(telphoneValue)){
                                            sendMsgByTelphone(principle, customer, null, mobileSubject, mobileContent, telphoneValue, type);
                                        }
                                    }
                                }else{
                                    if(pattern.isMobileNO(telphoneTemp)){
                                        sendMsgByTelphone(principle, customer, null, mobileSubject, mobileContent, telphoneTemp, type);
                                    }
                                }
                            }
                        }
                        customer.setUpdateTime(new Date());
                        customerInfoDao.update(customer);
                    }
                } else {
                    throw new Exception("mobile can not found");
                }
            }
        } catch (Exception e) {
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    //新年短信祝福
    @ResponseBody
    @RequestMapping(value = "msgAllInlandHistoryStoneCustomers", method = RequestMethod.POST)
    public BaseResponse msgAllInlandHistoryStoneCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                          @RequestParam("content") String content,
                                                          @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<WCustomer> customers = new ArrayList<WCustomer>();
        List<THistoryCustomer> tHistoryCustomerList = new ArrayList<THistoryCustomer>();
        try {
            if(cids[0] == -1){
                TUserInfo userInfo = (TUserInfo) principle.getAdmin();
                TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
                tHistoryCustomerList = historyCustomerService.loadAllHistoryInlandCustomerByUserInfoAndUserRole(userInfo1);
            } else {
                tHistoryCustomerList = historyCustomerService.loadSelectedHistoryCustomers(cids);
            }
            String mobileSubject = "石材展新年祝福短信";
            String mobileContent = content;
            if(tHistoryCustomerList.size()>0) {
                StringBuffer mobileStr = new StringBuffer();
                //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneMsgFileName);
                for(int i=0;i<tHistoryCustomerList.size();i++) {
                    THistoryCustomer tHistoryCustomer = tHistoryCustomerList.get(i);
                    EmailPattern pattern = new EmailPattern();
                    if(tHistoryCustomer != null) {
                        String telphoneTemp = tHistoryCustomer.getTelphone();
                        if(telphoneTemp.contains("/")){
                            String[] telphoneArray = telphoneTemp.split("/");
                            for(String telphoneValue:telphoneArray){
                                if(pattern.isMobileNO(telphoneValue)){
                                    sendMsgByTelphone(principle, null, tHistoryCustomer, mobileSubject, mobileContent, telphoneValue, 4);
                                }
                            }
                        }else{
                            if(pattern.isMobileNO(telphoneTemp)){
                                sendMsgByTelphone(principle, null, tHistoryCustomer, mobileSubject, mobileContent, telphoneTemp, 4);
                            }
                        }
                    }
                    /*tHistoryCustomer.setUpdatetime(new Date());
                    historyCustomerInfoDao.update(tHistoryCustomer);*/
                }
            } else {
                throw new Exception("mobile can not found");
            }
        } catch (Exception e) {
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    private void sendMsgByTelphone(ManagerPrinciple principle, WCustomer customer, THistoryCustomer tHistoryCustomer,
                                   String mobileSubject, String mobileContent, String telphone, Integer content){
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
        Date date = new Date();
        String str = bartDateFormat.format(date);
        /*ReadWriteEmailAndMsgFile.setFileContentIsNull();
        ReadWriteEmailAndMsgFile.readTxtFile(ReadWriteEmailAndMsgFile.stoneMsgFileName);*/
        try {
            //ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给境内手机号为：" + telphone + "发短信。", ReadWriteEmailAndMsgFile.stoneMsgFileName);
            //log.info("======给境内手机号为：" + customer.getMobilePhone() + "发短信======");
            String mobileContentTemp = mobileContent;
            if(1 == content && customer != null && (customer.getIsActivated() == 1 || customer.getIsActivated() == 2)) {
                mobileContent = mobileContentTemp.replace("@@_username_@@",customer.getFirstName());
                mobileContent = mobileContent.replace("@@_CHECKINGNUMBER_@@",customer.getCheckingNo());
            }else if(2 == content && customer != null && customer.getIsActivated() == 0){
                mobileContent = mobileContentTemp.replace("@@_username_@@",customer.getFirstName());
            }else if((3 == content || 4 == content) && tHistoryCustomer != null){
                mobileContent = mobileContentTemp.replace("@@_username_@@",tHistoryCustomer.getContact());
            }
            //发送短信
            if(3 == content && tHistoryCustomer != null){
                sendMsgByAsynchronousMode(principle, customer, tHistoryCustomer, tHistoryCustomer.getTelphone(), tHistoryCustomer.getId(), mobileContent, mobileSubject, content);
            }else if(((1== content && customer.getIsActivated() == 1)
                    || (1 == content && customer.getIsActivated() == 2)
                    || (2 == content && customer.getIsActivated() == 0))
                    && customer != null){
                sendMsgByAsynchronousMode(principle, customer, tHistoryCustomer, customer.getMobilePhone(), customer.getId(), mobileContent, mobileSubject, content);
            }else if(4 == content){
                //表示发的是新年祝福内容
                sendMsgByAsynchronousMode(principle, null, tHistoryCustomer, tHistoryCustomer.getTelphone(), tHistoryCustomer.getId(), mobileContent, mobileSubject, 3);
            }
        } catch (Exception e) {
            log.info("======给境内手机号为：" + telphone + "发短信，出错了======");
            e.printStackTrace();
        }
    }

    private void saveMsgInfoLog(ManagerPrinciple principle, WCustomer customer, THistoryCustomer tHistoryCustomer,
                                String mobileContent, String mobileSubject, String telphone, Integer content) {
        TVisitorMsgLog visitorMsgLog = new TVisitorMsgLog();
        visitorMsgLog.setMsgContent(mobileContent);

        visitorMsgLog.setCreateTime(new Date());
        visitorMsgLog.setLogSubject("");
        visitorMsgLog.setReply(0);
        visitorMsgLog.setLogSubject("");
        visitorMsgLog.setLogContent("");
        visitorMsgLog.setGUID("");
        visitorMsgLog.setMsgSubject(mobileSubject);
        visitorMsgLog.setMsgFrom("");
        visitorMsgLog.setMsgTo(telphone);
        visitorMsgLog.setStatus(0);
        if(3 == content && tHistoryCustomer != null){
            visitorMsgLog.setCustomerID(tHistoryCustomer.getId());
        }else if(customer != null){
            visitorMsgLog.setCustomerID(customer.getId());
        }
        visitorLogMsgService.insertLogMsg(visitorMsgLog);

        if(3 != content){
            List<VApplyMsg> customerApplyMsgList = customerApplyMsgInfoDao.queryByHql("from VApplyMsg where CustomerID=?", new Object[]{customer.getId()});
            if(customerApplyMsgList != null && customerApplyMsgList.size()>0){
                for(int k=0;k<customerApplyMsgList.size();k++){
                    VApplyMsg applyMsg = customerApplyMsgList.get(k);
                    if(principle != null && principle.getAdmin() != null){
                        applyMsg.setAdmin(principle.getAdmin().getName());
                    }
                    applyMsg.setStatus(1);
                    applyMsg.setConfirmTime(new Date());
                    try {
                        applyMsg.setConfirmIP(InetAddress.getLocalHost().getHostAddress());
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    customerApplyMsgInfoDao.update(applyMsg);
                }
            }
        }
    }

    /**
     * 异步发送
     */
    public void sendMsgByAsynchronousMode(final ManagerPrinciple principle, final WCustomer customer, final THistoryCustomer tHistoryCustomer,
                                          final String phone, final Integer cusId, final String mobileContent, final String mobileSubject, final Integer type) {
        if (log.isDebugEnabled()) {
            log.debug("当前短信采取异步发送....");
        }
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    /*HttpGet post = new HttpGet("http://113.106.91.228:9000/WebService.asmx/mt?Sn=SDK100&Pwd=123321&mobile="
                            + phone + "&content=" + mobileContent);*/
                    HttpGet post = new HttpGet("http://113.106.91.228:9000/WebService.asmx/mt2?Sn=SDK100&Pwd=123321&mobile="
                            + phone + "&content=" + mobileContent);
                    HttpResponse response = httpClient.execute(post);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        if(3 == type){
                            saveMsgInfoLog(principle, customer, tHistoryCustomer, mobileContent, "历史客商:" + mobileSubject, phone, type);
                        }else{
                            saveMsgInfoLog(principle, customer, tHistoryCustomer, mobileContent, mobileSubject, phone, type);
                            customerInfoManagerService.updateCustomerMsgNum(cusId);
                        }
                        log.info("群发短信任务完成");
                    } else {
                        /*if(3 == type && tHistoryCustomer != null){
                            sendFailTelephoneList.add(tHistoryCustomer);
                        }else if(customer != null){
                            sendFailTelephoneList.add(customer);
                        }*/
                        log.error("群发短信失败，错误码：" + response.getStatusLine().getStatusCode());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    @RequestMapping(value = "mails", method = RequestMethod.GET)
    @ResponseBody
    public GetMailSendDetailsResponse getMailSendDetail(@ModelAttribute Page page,
                                                        @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        GetMailSendDetailsResponse response = new GetMailSendDetailsResponse();
        try {
            if (page == null) {
                page = new Page();
                page.setPageIndex(1);
                page.setPageSize(10);
            }
            mailService.loadDetailByEid(page, principle.getExhibitor().getEid());
            BeanUtils.copyProperties(page, response);
            response.setDatas(page.getRows());
        } catch (Exception e) {
            log.error("get mails error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    @RequestMapping(value = "previewMail", method = RequestMethod.POST)
    public ModelAndView previewMail(@ModelAttribute("mail") Email email,
                                    @ModelAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE) Principle principle) {
        ModelAndView modelAndView = new ModelAndView();
        String booth = exhibitorService.loadBoothNum(principle.getExhibitor().getEid());
        String company = exhibitorService.query(principle.getExhibitor().getEid()).getCompany();
        String companye = exhibitorService.query(principle.getExhibitor().getEid()).getCompanyEn();
        if (email.getFlag() == 1) {
            modelAndView.setViewName("/user/mail/VisitorReplay");
            email.setSubject("厦门国际石材展邀请函");
            email.setBoothNumber(booth);
            email.setCompany(company);
        } else {
            modelAndView.setViewName("/user/mail/VisitorReplay_unPro");
            email.setSubject("The invitation Of China Xiamen International Stone Fair");
            email.setBoothNumber(booth);
            email.setCompany(companye);
        }
        modelAndView.addObject("email", email);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "sendExhibitorInvisitor", method = RequestMethod.POST)
    public BaseResponse sendExhibitorInvisitor(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
                                                @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<TExhibitorInfo> exhibitorInfoList = new ArrayList<TExhibitorInfo>();
        try {
            exhibitorInfoList = exhibitorService.loadExhibitorInfoList(eids);
            ExhibitorInvisitorEmail exhibitorInvisitorEmail = new ExhibitorInvisitorEmail();

            if(exhibitorInfoList.size()>0){
                for( TExhibitorInfo exhibitorInfo:exhibitorInfoList){
                    TExhibitor tExhibitor = exhibitorService.loadExhibitorByEid(exhibitorInfo.getEid());
                    TExhibitorBooth tExhibitorBooth = exhibitorManagerService.queryBoothByEid(exhibitorInfo.getEid());
                    /*if(44 != tExhibitor.getCountry()) {
                        exhibitorInvisitorEmail.setCompanyName(exhibitorInfo.getCompanyEn());
                    }else{
                        exhibitorInvisitorEmail.setCompanyName(exhibitorInfo.getCompany());
                    }*/
                    if(tExhibitor.getCountry() != 44){
                        exhibitorInvisitorEmail.setCompanyName(exhibitorInfo.getCompanyEn());
                    }else{
                        exhibitorInvisitorEmail.setCompanyName(exhibitorInfo.getCompany());
                    }
                    exhibitorInvisitorEmail.setBoothNumber(tExhibitorBooth.getBoothNumber());
                    exhibitorInvisitorEmail.setUserName(tExhibitor.getUsername());
                    exhibitorInvisitorEmail.setPassword(tExhibitor.getPassword());
                    exhibitorInvisitorEmail.setExhibitorArea(tExhibitor.getExhibitionArea());
                    //1表示标摊类型，其它表示空地类型
                    if("1".equals(tExhibitorBooth.getMark())) {
                        exhibitorInvisitorEmail.setRawSpaceOrShellScheme("1");
                    }else{
                        exhibitorInvisitorEmail.setRawSpaceOrShellScheme("0");
                    }
                    List<TContact> contactList = contactManagerService.loadContactByEid(exhibitorInfo.getEid());
                    exhibitorInvisitorEmail.setContactList(contactList);
                    exhibitorInvisitorEmail.setSendEamilAccount(ExhibitorInvisitorMailUtil.emailUserName);
                    exhibitorInvisitorEmail.setSendEmailPassword(ExhibitorInvisitorMailUtil.emailPassword);
                    tExhibitor.setSend_invitation_flag(1);
                    tExhibitor.setUpdateTime(new Date());
                    exhibitorDao.update(tExhibitor);
                    exhibitorInfo.setUpdateTime(new Date());
                    exhibitorManagerService.update(exhibitorInfo);
                    mailService.sendInvisitorMailByAsynchronousMode(exhibitorInvisitorEmail);
                    baseResponse.setResultCode(0);
                }
            }
        } catch (Exception e) {
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "previewExhibitorInvisitor", method = RequestMethod.POST)
    public PreviewExhibitorInvitationResponse previewExhibitorInvisitor(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
                                                                        @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        PreviewExhibitorInvitationResponse baseResponse = new PreviewExhibitorInvitationResponse();
        List<TExhibitorInfo> exhibitorInfoList = new ArrayList<TExhibitorInfo>();
        List<PreviewExhibitorInvitation> previewExhibitorInvitationList = new ArrayList<PreviewExhibitorInvitation>();
        try{
            exhibitorInfoList = exhibitorService.loadExhibitorInfoList(eids);

            if(exhibitorInfoList.size()>0){
                for( TExhibitorInfo exhibitorInfo:exhibitorInfoList){
                    TExhibitor tExhibitor = exhibitorService.loadExhibitorByEid(exhibitorInfo.getEid());
                    TExhibitorBooth tExhibitorBooth = exhibitorManagerService.queryBoothByEid(exhibitorInfo.getEid());

                    PDFReaderUtil pdfReaderUtil = new PDFReaderUtil();
                    if(exhibitorInfo.getCompany() == null) {
                        //在这里处理公司为空或展商为国内的情况
                    }else{
                        pdfReaderUtil.writePDF(pdfReaderUtil.exhibitorInvitationInputPath,
                                pdfReaderUtil.exhibitorInvitationSavePath + (44 == tExhibitor.getCountry()?exhibitorInfo.getCompany():exhibitorInfo.getCompanyEn()) + ".pdf",
                                (44 == tExhibitor.getCountry()?exhibitorInfo.getCompany():exhibitorInfo.getCompanyEn()),
                                tExhibitor.getUsername(), tExhibitor.getPassword(),
                                pdfReaderUtil.exhibitorSelectPath,
                                tExhibitorBooth.getBoothNumber(), tExhibitor.getExhibitionArea(),
                                ("1".equals(tExhibitorBooth.getMark())?"1":"0"));

                        List<TContact> contactList = contactManagerService.loadContactByEid(exhibitorInfo.getEid());
                        for(TContact tContact:contactList){
                            PreviewExhibitorInvitation previewExhibitorInvitation = new PreviewExhibitorInvitation();
                            previewExhibitorInvitation.setEid(exhibitorInfo.getEid());
                            previewExhibitorInvitation.setContactName(tContact.getName());
                            previewExhibitorInvitation.setBoothConfirm((tExhibitor.getCountry() == 44?exhibitorInfo.getCompany():exhibitorInfo.getCompanyEn()) + ".pdf");
                            previewExhibitorInvitationList.add(previewExhibitorInvitation);
                        }
                    }
                }
                baseResponse.setResultCode(0);
            }
            baseResponse.setPreviewExhibitorInvitationList(previewExhibitorInvitationList);
        }catch (Exception e){
            System.out.println("预览出错信息：" + e);
            try {
                FileUtils.forceMkdir(new File("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\boothconfirm\\"));
                System.out.println("----重新创建文件夹(boothconfirm)----");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            baseResponse.setDescription(e.toString());
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @RequestMapping(value = "showExhibitorInvitation", method = RequestMethod.GET)
    public void showExhibitorInvitation(HttpServletResponse response,
                                        @RequestParam("eid") Integer eid) {
        try {
            TExhibitor tExhibitor = exhibitorService.loadExhibitorByEid(eid);
            TExhibitorInfo exhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(eid);
            String companyName = "";
            if(44 != tExhibitor.getCountry()) {
                companyName = exhibitorInfo.getCompanyEn();
            }else{
                companyName = exhibitorInfo.getCompany();
            }
            PDFReaderUtil pdfReaderUtil = new PDFReaderUtil();
            String exhibitorInvitationPath = pdfReaderUtil.exhibitorInvitationSavePath + companyName + ".pdf";
            if (StringUtils.isNotEmpty(exhibitorInvitationPath)) {
                OutputStream outputStream = response.getOutputStream();
                response.reset();// 清空输出流
                response.setContentType("application/x-msdownload");
                //response.setContentType("application/pdf ");
                response.setHeader( "Content-disposition", "attachment;filename=" + exhibitorInvitationPath);
                File logo = new File(exhibitorInvitationPath);
                if (!logo.exists()) {
                    return;
                }
                FileUtils.copyFile(new File(exhibitorInvitationPath), outputStream);
                outputStream.close();
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMailText(Email email) throws Exception {
        // 通过指定模板名获取FreeMarker模板实例
        Template template = freeMarker.getConfiguration().getTemplate("mail/VisitorRegistrate_Zh.html");

        // FreeMarker通过Map传递动态数据
        Map<Object, Object> model = new HashMap<Object, Object>();
        model.put("email", email); // 注意动态数据的key和模板标签中指定的属性相匹配

        // 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
        String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return htmlText;
    }

    public void htmlToPdf(String inputFile,String outputFile) throws Exception{
        String url = new File(inputFile).toURI().toURL().toString();
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("c:/Windows/Fonts/<span style='color:#FF0000;'>simsun</span>.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 解决图片的相对路径问题
        // renderer.getSharedContext().setBaseURL("file:/D:/z/temp/");
        renderer.layout();
        renderer.createPDF(os);
        os.flush();
        os.close();
    }

    // 支持中文
    public static void htmlToPdf2(String inputFile,String outputFile) throws Exception {
        OutputStream os =  new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("C:/Windows/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        String url = new File(inputFile).toURI().toURL().toString();
        renderer.setDocument(url);
        renderer.layout();
        renderer.createPDF(os);
        os.flush();
        os.close();
        /*String url = new File(inputFile).toURI().toURL().toString();
        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("C:/Windows/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        StringBuffer html = new StringBuffer();
        // DOCTYPE 必需写否则类似于 这样的字符解析会出现错误
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").
                append("<head>")
                .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />")
                .append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
                .append("</head>")
                .append("<body>");
        html.append(url);
        html.append("</body></html>");
        renderer.setDocumentFromString(html.toString());
        renderer.layout();
        renderer.createPDF(os);
        os.close();*/
    }

    /**
     * 下载邮件内容
     * @param cid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportEmailContentToZip")
    public ModelAndView exportEmailContentToZip(@RequestParam(value = "cids") Integer cid,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
        String randomFile = UUID.randomUUID().toString();
        String destDir = appendix_directory + "\\tmp\\emailContent\\";
        FileUtils.forceMkdir(new File(destDir)); // 创建临时文件夹

        WCustomer customer = customerInfoManagerService.loadCustomerInfoById(cid);
        EmailPattern pattern = new EmailPattern();
        if(customer != null && pattern.isEmailPattern(customer.getEmail())) {
            Email email = initEmailTemplate();
            if(customer.getIsProfessional() == 1) {
                email.setFlag(1);//专业采购商
            } else {
                email.setFlag(0);//展会观众
            }

            email.setEmailType(customer.getIsProfessional());
            email.setCheckingNo(customer.getCheckingNo());
            email.setCustomerId(customer.getId());
            email.setCountry(customer.getCountry() == 44 ? 0:1);
            email.setCompany(customer.getCompany());
            email.setName(customer.getFirstName());
            if(customer.getPosition() == null || customer.getPosition() == ""){
                email.setPosition("");
            } else {
                email.setPosition(customer.getPosition());
            }
            email.setRegID(customer.getCheckingNo());
            email.setReceivers(customer.getEmail());

            String emailContentHtml = getMailContentByAsyncAnnotationMode(email);
            String inputFile = destDir + customer.getCompany() + ".html";
            OutputStream os = new FileOutputStream(new File(inputFile));
            os.write(emailContentHtml.getBytes());
            os.close();
        }

        return downloadEmailContent(destDir, customer.getCompany(), request, response);
    }

    public ModelAndView downloadEmailContent(String destDir, String zipName, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String filePath = destDir + "\\" + zipName + ".html";  //获取完整的文件名
        try {
            File file = new File(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);//得到文件名
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            response.setContentType("application/octet-stream");//告诉浏览器输出内容为流
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);//Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            String len = String.valueOf(file.length());
            response.setHeader("Content-Length", len);//设置内容长度
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMailContentByAsyncAnnotationMode(Email email) {
        if (email.getReceivers() == null) {
            throw new IllegalArgumentException("收件人不能为空");
        }

        String emailContent = "";
        if(email.getEmailType() ==0){
            if(email.getCountry() == 0){
                emailContent = email.getMail_register_content_cn_unpro().replace("@@_NAME_@@",email.getName());
            } else {
                emailContent = email.getMail_register_content_en_unpro().replace("@@_NAME_@@",email.getName());
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
                emailContent = email.getRegister_content_cn();
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
                emailContent = email.getRegister_content_en();
            }
        }
        return emailContent;
    }

    public static void main(String[] args) throws IOException, ClientException {
        /*String host = "http://sms.market.alicloudapi.com";
        String path = "/singleSendSms";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "LTAIy13SYtslNdfV");
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ParamString", "大地");
        querys.put("RecNum", "13459298520");
        querys.put("SignName", "金泓信展览");
        querys.put("TemplateCode", "SMS_44375554");

        try {
            *//**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             *//*
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIy13SYtslNdfV", "oDX5C0HRCJA1s5Su0lzMkMqOA2DDcN");
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        try {
            request.setSignName("金泓信展览");
            request.setTemplateCode("SMS_44375554");
            request.setParamString( "{\"CHECKINGNUMBER\" : \"大地\"}");
            request.setRecNum("13459298520");
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        String uri = new File("d:\\EmailContent.html").toURI().toString();
        imageGenerator.loadHtml(uri);
        *//*String htmlstr = "<table width='654' cellpadding='0' cellspacing='0' bordercolor='#FFFFFF'><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr></table>";
        imageGenerator.loadHtml(htmlstr);*//*
        imageGenerator.saveAsImage("d:\\EmailContent.png");
        imageGenerator.saveAsHtmlWithMap("EmailContent.html", "EmailContent.png");*/

        /*Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher("13459298520");
        System.out.println(m.matches());*/

        /*MailAction mailAction = new MailAction();
        String inputFile = "mail/VisitorReplay.html";
        String outputFile = "D://VisitorReplay.pdf";
        try {
            mailAction.htmlToPdf(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //new MailAction();
    }
}
