package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.entity.TVisitorTemplate;
import org.apache.log4j.Logger;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.service.CustomerTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by wangxd on 2016/4/5.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class TemplateManagerAction extends BaseAction {

    private static Logger log = Logger.getLogger(TemplateManagerAction.class);

    @Autowired
    private CustomerTemplateService customerTemplaeService;

    @Autowired
    JavaMailSender mailSender;// 注入Spring封装的javamail，Spring的xml中已让框架装配

    //邮件网页
    @RequestMapping(value = "tEmail")
    public ModelAndView directToEmailTemplate() {
        ModelAndView modelAndView = new ModelAndView("user/email/emailTemplate");
        return modelAndView;
    }

    /**
     * 查询邮件模板
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryEmailManagerTemplateByPage")
    public QueryEmailTemplateResponse queryEmailManagerTemplateByPage(@ModelAttribute QueryEmailTemplateRequest request) {
        QueryEmailTemplateResponse response = new QueryEmailTemplateResponse();
        try {
            response = customerTemplaeService.queryEmailManagerTemplate(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query email template error.", e);
        }
        return response;
    }

    /**
     * 更新邮件模板
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEmailManagerTemplateByPage")
    public BaseResponse updateEmailManagerTemplateByPage(@ModelAttribute QueryEmailTemplateRequest request,
                                                         @RequestParam(value = "mail_register_subject_cn", defaultValue = "") String mail_register_subject_cn,
                                                         @RequestParam(value = "mail_register_subject_en", defaultValue = "") String mail_register_subject_en,
                                                         @RequestParam(value = "mail_register_content_cn", defaultValue = "") String mail_register_content_cn,
                                                         @RequestParam(value = "mail_register_content_en", defaultValue = "") String mail_register_content_en,
                                                         @RequestParam(value = "mail_invite_subject_cn", defaultValue = "") String mail_invite_subject_cn,
                                                         @RequestParam(value = "mail_invite_subject_en", defaultValue = "") String mail_invite_subject_en,
                                                         @RequestParam(value = "mail_invite_content_cn", defaultValue = "") String mail_invite_content_cn,
                                                         @RequestParam(value = "mail_invite_content_en", defaultValue = "") String mail_invite_content_en,
                                                         @RequestParam(value = "mail_register_subject_cn_unpro", defaultValue = "") String mail_register_subject_cn_unpro,
                                                         @RequestParam(value = "mail_register_content_cn_unpro", defaultValue = "") String mail_register_content_cn_unpro,
                                                         @RequestParam(value = "mail_register_subject_en_unpro", defaultValue = "") String mail_register_subject_en_unpro,
                                                         @RequestParam(value = "mail_register_content_en_unpro", defaultValue = "") String mail_register_content_en_unpro,
                                                         @RequestParam(value = "mail_register_policyDeclare_cn", defaultValue = "") String mail_register_policyDeclare_cn,
                                                         @RequestParam(value = "mail_register_policyDeclare_en", defaultValue = "") String mail_register_policyDeclare_en) {
        BaseResponse response = new BaseResponse();
        try {
            customerTemplaeService.modifyEmailManagerTemplate(mail_register_subject_cn,mail_register_subject_en,mail_register_content_cn,
                    mail_register_content_en,mail_invite_subject_cn,mail_invite_subject_en,mail_invite_content_cn,mail_invite_content_en,
                    mail_register_subject_cn_unpro,mail_register_content_cn_unpro,mail_register_subject_en_unpro,
                    mail_register_content_en_unpro,mail_register_policyDeclare_cn,mail_register_policyDeclare_en);
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("modify exhibitor group error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    //短信网页
    @RequestMapping(value = "tMsg")
    public ModelAndView directToMsgTemplate() {
        ModelAndView modelAndView = new ModelAndView("user/email/messageTemplate");
        return modelAndView;
    }

    /**
     * 查询短信模板
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryMsgManagerTemplateByPage")
    public QueryEmailTemplateResponse queryMsgManagerTemplateByPage(@ModelAttribute QueryEmailTemplateRequest request) {
        QueryEmailTemplateResponse response = new QueryEmailTemplateResponse();
        try {
            response = customerTemplaeService.queryEmailManagerTemplate(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query email template error.", e);
        }
        return response;
    }

    /**
     * 更新短信模板
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateMsgManagerTemplateByPage")
    public BaseResponse updateMsgManagerTemplateByPage(@ModelAttribute QueryEmailTemplateRequest request,
                                                         @RequestParam(value = "msg_register_subject_cn", defaultValue = "") String msg_register_subject_cn,
                                                         @RequestParam(value = "msg_register_subject_en", defaultValue = "") String msg_register_subject_en,
                                                         @RequestParam(value = "msg_register_content_cn", defaultValue = "") String msg_register_content_cn,
                                                         @RequestParam(value = "msg_register_content_en", defaultValue = "") String msg_register_content_en,
                                                         @RequestParam(value = "msg_invite_subject_cn", defaultValue = "") String msg_invite_subject_cn,
                                                         @RequestParam(value = "msg_invite_subject_en", defaultValue = "") String msg_invite_subject_en,
                                                         @RequestParam(value = "msg_invite_content_cn", defaultValue = "") String msg_invite_content_cn,
                                                         @RequestParam(value = "msg_invite_content_en", defaultValue = "") String msg_invite_content_en,
                                                       @RequestParam(value = "msg_unregister_subject_cn", defaultValue = "") String msg_unregister_subject_cn,
                                                       @RequestParam(value = "msg_unregister_content_cn", defaultValue = "") String msg_unregister_content_cn,
                                                       @RequestParam(value = "msg_unregister_subject_en", defaultValue = "") String msg_unregister_subject_en,
                                                       @RequestParam(value = "msg_unregister_content_en", defaultValue = "") String msg_unregister_content_en,
                                                       @RequestParam(value = "msg_unactive_subject_cn", defaultValue = "") String msg_unactive_subject_cn,
                                                       @RequestParam(value = "msg_unactive_content_cn", defaultValue = "") String msg_unactive_content_cn,
                                                       @RequestParam(value = "msg_unactive_subject_en", defaultValue = "") String msg_unactive_subject_en,
                                                       @RequestParam(value = "msg_unactive_content_en", defaultValue = "") String msg_unactive_content_en,
                                                       @RequestParam(value = "msg_new_year_content", defaultValue = "") String msg_new_year_content) {
        BaseResponse response = new BaseResponse();
        try {
            customerTemplaeService.modifyMessageManagerTemplate(msg_register_subject_cn,msg_register_subject_en,msg_register_content_cn,
                    msg_register_content_en,msg_invite_subject_cn,msg_invite_subject_en,msg_invite_content_cn,msg_invite_content_en, msg_unregister_subject_cn,
                    msg_unregister_content_cn, msg_unregister_subject_en, msg_unregister_content_en, msg_unactive_subject_cn, msg_unactive_content_cn,
                    msg_unactive_subject_en, msg_unactive_content_en, msg_new_year_content);
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("modify exhibitor group error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 更新新年短信祝福模板
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadMsgTemplatContent")
    public QueryMsgNewYearContentResponse loadMsgTemplatContent(@ModelAttribute QueryMsgNewYearContentRequest request) {
        QueryMsgNewYearContentResponse response = new QueryMsgNewYearContentResponse();
        try {
            List<TVisitorTemplate> customerTemplatesList = customerTemplaeService.loadAllCustomerTemplate();
            if(customerTemplatesList != null){
                for(TVisitorTemplate tVisitorTemplate:customerTemplatesList){
                    if("msg_new_year_content".equalsIgnoreCase(tVisitorTemplate.getTpl_key())){
                        response.setResultCode(0);
                        response.setMsgContent(tVisitorTemplate.getTpl_value());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query tags error.", e);
        }
        return response;
    }
}
