package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dao.CustomerInfoDao;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.dto.QueryEmailOrMsgRequest;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dao.visitorgroup.VisitorGroupDao;
import com.zhenhappy.ems.manager.dao.visitorgroup.VisitorMemberDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.dto.visitorfeedback.CountVisitorFeedBackInfo;
import com.zhenhappy.ems.manager.dto.visitorfeedback.CountVisitorFeedBakcInfoResponse;
import com.zhenhappy.ems.manager.dto.visitorfeedback.QueryCustomerFeedBackRequest;
import com.zhenhappy.ems.manager.dto.visitorfeedback.QueryCustomerFeedBackResponse;
import com.zhenhappy.ems.manager.dto.visitorgroup.ModifyGroupInfo;
import com.zhenhappy.ems.manager.dto.visitorgroup.ModifyGroupMemberInfo;
import com.zhenhappy.ems.manager.dto.visitorgroup.QueryVisitorGroupRequest;
import com.zhenhappy.ems.manager.dto.visitorgroup.QueryVisitorMemberListRequest;
import com.zhenhappy.ems.manager.entity.WCustomerEx;
import com.zhenhappy.ems.manager.entity.backupinfo.TVisitorBackupInfo;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBack;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBackType;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorGroupInfo;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorMemberInfo;
import com.zhenhappy.ems.manager.exception.DuplicateUsernameException;
import com.zhenhappy.ems.manager.service.CustomerInfoManagerService;
import com.zhenhappy.ems.manager.service.CustomerTemplateService;
import com.zhenhappy.ems.manager.service.visitorfeedback.VisitorFeedBackService;
import com.zhenhappy.ems.manager.service.visitorfeedback.VisitorFeedBackTypeService;
import com.zhenhappy.ems.manager.sys.Constants;
import com.zhenhappy.ems.service.CountryProvinceService;
import com.zhenhappy.ems.service.EmailMailService;
import com.zhenhappy.ems.service.VisitorLogMsgService;
import com.zhenhappy.system.SystemConfig;
import com.zhenhappy.util.EmailPattern;
import com.zhenhappy.util.Page;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wujianbin on 2014-07-02.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class CustomerAction extends BaseAction {

    private static Logger log = Logger.getLogger(CustomerAction.class);

    @Autowired
    private CustomerInfoManagerService customerInfoManagerService;
    @Autowired
    private CountryProvinceService countryProvinceService;
    @Autowired
    private VisitorMemberDao visitorMemberDao;
    @Autowired
    private VisitorGroupDao visitorGroupDao;
    @Autowired
    private CustomerTemplateService customerTemplaeService;
    @Autowired
    private EmailMailService mailService;
    // 注入Spring封装的异步执行器
    @Autowired
    TaskExecutor taskExecutor;
    @Autowired
    VisitorLogMsgService visitorLogMsgService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private VisitorFeedBackTypeService visitorFeedBackTypeService;
    @Autowired
    private VisitorFeedBackService visitorFeedBackService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TreeMap<String, Integer> feedBackCountTreeMap = new TreeMap<String, Integer>();

    /**
     * 现场登记：根据预登记号查询对应的客商，用于客商打印系统调用
     * @param checkingNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCustomerInfoForPrintSystem")
    public WCustomer getCustomerInfoForPrintSystem(@RequestParam("checkingNo") String checkingNo) {
        WCustomer wCustomer = customerInfoManagerService.loadCustomerByCheckingNo(checkingNo);
        return wCustomer;
    }

    /**
     * 显示客商详细页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "directToCustomerInfo")
    public ModelAndView directToCustomerInfo(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/customer/customerInfo");
        modelAndView.addObject("id", id);
        WCustomer wCustomer = customerInfoManagerService.loadCustomerInfoById(id);
        WCustomerEx wCustomerEx = new WCustomerEx();
        if(wCustomer != null){
            int countryIndex = wCustomer.getCountry();
            WCountry country = countryProvinceService.loadCountryById(countryIndex);
            if(country != null){
                BeanUtils.copyProperties(wCustomer, wCustomerEx);
                wCustomerEx.setCountryValue(country.getChineseName());
            }
        }
        modelAndView.addObject("customer", wCustomerEx);
        modelAndView.addObject("customerSurvey", customerInfoManagerService.loadCustomerSurveyInfoByCustomerId(id));
        modelAndView.addObject("customerInfoYear", customerInfoManagerService.loadVisitorInfoYearByCustomerId(id));
        /*modelAndView.addObject("exhibitor", exhibitorManagerService.loadExhibitorByEid(eid));
        modelAndView.addObject("term", exhibitorManagerService.getExhibitorTermByEid(eid));
        modelAndView.addObject("booth", exhibitorManagerService.queryBoothByEid(eid));
        modelAndView.addObject("currentTerm", exhibitorManagerService.queryCurrentTermNumber());
        modelAndView.addObject("exhibitorInfo", exhibitorManagerService.loadExhibitorInfoByEid(eid));
        *//*石材展需求开始*//*
//        modelAndView.addObject("exhibitorMeipai", meipaiService.getMeiPaiByEid(eid));
        *//*石材展需求结束*//*
        modelAndView.addObject("invoice", invoiceService.getByEid(eid));*/
        return modelAndView;
    }

    /**
     * 显示客商详细页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "directToEmailSendFailureCustomerInfo")
    public ModelAndView directToEmailSendFailureCustomerInfo(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/customer/emailSendFailureCustomerInfo");
        modelAndView.addObject("id", id);
        WCustomer wCustomer = customerInfoManagerService.loadCustomerInfoById(id);
        WCustomerEx wCustomerEx = new WCustomerEx();
        if(wCustomer != null){
            int countryIndex = wCustomer.getCountry();
            WCountry country = countryProvinceService.loadCountryById(countryIndex);
            if(country != null){
                BeanUtils.copyProperties(wCustomer, wCustomerEx);
                wCustomerEx.setCountryValue(country.getChineseName());
            }
        }
        modelAndView.addObject("customer", wCustomerEx);
        modelAndView.addObject("customerSurvey", customerInfoManagerService.loadCustomerSurveyInfoByCustomerId(id));
        return modelAndView;
    }

    /**
     * 根据客商ID获取公司名称
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getCompanyByCustomerId", method = RequestMethod.POST)
    public BaseResponse getCompanyByCustomerId(@RequestParam("id") Integer id) {
        BaseResponse response = new BaseResponse();
        WCustomer customer = customerInfoManagerService.loadCustomerInfoById(id);
        if(customer != null) {
            response.setDescription(customer.getCompany());
        } else {
            response.setDescription("");
        }
        return response;
    }

    /**
     * 修改客商账号
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyCustomerInfo", method = RequestMethod.POST)
    public BaseResponse modifyCustomerAccount(@ModelAttribute ModifyCustomerInfo request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            EmailPattern pattern = new EmailPattern();
            if(pattern.isEmailPattern(request.getEmail())) {
                List<WCustomer> wCustomers = customerInfoManagerService.loadCustomerByEmail(request.getEmail());
                if(wCustomers == null){
                    customerInfoManagerService.modifyCustomerAccount(request, principle.getAdmin().getId());
                } else {
                    if(wCustomers.size()>1){
                        response.setDescription("邮箱不能重复");
                        response.setResultCode(3);
                    }else{
                        customerInfoManagerService.modifyCustomerAccount(request, principle.getAdmin().getId());
                    }
                }
            } else {
                response.setResultCode(2);
                response.setDescription("请输入有效的邮箱格式");
            }
            if(!pattern.isMobileNO(request.getMobilePhone())) {
                response.setResultCode(2);
                response.setDescription("请输入有效的手机号码");
            }
        } catch (DuplicateUsernameException e) {
            response.setResultCode(2);
            response.setDescription(e.getMessage());
        } catch (Exception e) {
            log.error("modify customer account error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 修改客商是否专业
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyCustomerProfessional", method = RequestMethod.POST)
    public BaseResponse modifyCustomerProfessional(@ModelAttribute QueryCustomerRequest request, @RequestParam(value = "id", defaultValue = "")Integer id) {
        BaseResponse response = new BaseResponse();
        try {
            customerInfoManagerService.modifyCustomerProfessional(request, id);
        } catch (DuplicateUsernameException e) {
            response.setResultCode(2);
            response.setDescription(e.getMessage());
        } catch (Exception e) {
            log.error("modify customer account error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 激活或注销客商信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "setCustomerActiveOrUnactive", method = RequestMethod.POST)
    public BaseResponse setCustomerActiveOrUnactive(@ModelAttribute QueryCustomerRequest request,
                                                    @RequestParam(value = "id", defaultValue = "")Integer id) {
        BaseResponse response = new BaseResponse();
        try {
            customerInfoManagerService.setCustomerActiveOrUnactive(request, id);
        } catch (DuplicateUsernameException e) {
            response.setResultCode(2);
            response.setDescription(e.getMessage());
        } catch (Exception e) {
            log.error("modify customer active or unactive error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 分页查询客商
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryStoneCustomersByPage")
    public QueryCustomerResponse queryStoneCustomersByPage(@ModelAttribute QueryCustomerRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.queryCustomersByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customers error.", e);
        }
        return response;
    }

    /**
     * 分页查询国内客商邮件发送失败
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryCustomersBySendEmailFailurePage")
    public QueryCustomerResponse queryCustomersBySendEmailFailurePage(@ModelAttribute QueryCustomerRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.queryCustomersBySendEmailFailurePage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customers for send email failure error.", e);
        }
        return response;
    }

	/**
	 * 分页查询国内客商
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryInlandCustomersByPage")
	public QueryCustomerResponse queryInlandCustomersByPage(@ModelAttribute QueryCustomerRequest request) {
		QueryCustomerResponse response = new QueryCustomerResponse();
		try {
			response = customerInfoManagerService.queryInlandCustomersByPage(request);
		} catch (Exception e) {
			response.setResultCode(1);
			log.error("query customers error.", e);
		}
		return response;
	}

	/**
	 * 分页查询国外客商
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryForeignCustomersByPage")
	public QueryCustomerResponse queryForeignCustomersByPage(@ModelAttribute QueryCustomerRequest request) {
		QueryCustomerResponse response = new QueryCustomerResponse();
		try {
			response = customerInfoManagerService.queryForeignCustomersByPage(request);
		} catch (Exception e) {
			response.setResultCode(1);
			log.error("query customers error.", e);
		}
		return response;
	}
    
    @RequestMapping(value = "inlandCustomer")
    public ModelAndView directToInlandCustomer() {
        ModelAndView modelAndView = new ModelAndView("user/customer/inlandCustomer");
        return modelAndView;
    }

	@RequestMapping(value = "foreignCustomer")
	public ModelAndView directToForeignCustomer() {
		ModelAndView modelAndView = new ModelAndView("user/customer/foreignCustomer");
		return modelAndView;
	}

    @RequestMapping(value = "emailApplyPage")
    public ModelAndView directToEmailApplyPage() {
        ModelAndView modelAndView = new ModelAndView("user/customer/emailApplyPage");
        return modelAndView;
    }

    /**
     * 分页查询邮件申请记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadEmailApplyByPage")
    public QueryCustomerResponse loadEmailApplyByPage(@ModelAttribute QueryCustomerRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.loadEmailApplyByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query email apply error.", e);
        }
        return response;
    }

    /**
     * 分页查询邮件申请记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryEmailApplyByPage")
    public QueryCustomerResponse queryEmailApplyByPage(@ModelAttribute QueryEmailOrMsgRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.queryEmailOrMsgApplyByPage(request, 1);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query email apply error.", e);
        }
        return response;
    }

    /**
     * 分页查询短信申请记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryMsgApplyByPage")
    public QueryCustomerResponse queryMsgApplyByPage(@ModelAttribute QueryEmailOrMsgRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.queryEmailOrMsgApplyByPage(request, 2);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query msg apply error.", e);
        }
        return response;
    }

    @RequestMapping(value = "msgApplyPage")
    public ModelAndView directToMmailApplyPage() {
        ModelAndView modelAndView = new ModelAndView("user/customer/msgApplyPage");
        return modelAndView;
    }

    /**
     * 分页查询短信申请记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadMsgApplyByPage")
    public QueryCustomerResponse loadMsgApplyByPage(@ModelAttribute QueryCustomerRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.loadMsgApplyByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query msg apply error.", e);
        }
        return response;
    }
    
    @ResponseBody
    @RequestMapping(value = "addCustomer", method = RequestMethod.POST)
    public BaseResponse addCustomer(@ModelAttribute AddArticleRequest request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            TArticle article = new TArticle();
            article.setTitle(request.getTitle());
            article.setTitleEn(request.getTitleEn());
            article.setDigest(request.getDigestEn());
            article.setDigestEn(request.getDigestEn());
            article.setContent(request.getContent());
            article.setContentEn(request.getContentEn());
            article.setCreateTime(new Date());
            if(principle.getAdmin().getId() != null || "".equals(principle.getAdmin().getId())){
            	article.setCreateAdmin(principle.getAdmin().getId());
            }else{
            	throw new Exception();
            }
        } catch (Exception e) {
            log.error("add article error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    @ResponseBody
    @RequestMapping(value = "modifyCustomer", method = RequestMethod.POST)
    public BaseResponse modifyCustomer(@ModelAttribute ModifyArticleRequest request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        } catch (Exception e) {
            log.error("modify article error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    @ResponseBody
    @RequestMapping(value = "deleteCustomers", method = RequestMethod.POST)
    public BaseResponse deleteCustomers(@RequestParam(value = "ids", defaultValue = "") Integer[] ids) {
        BaseResponse response = new BaseResponse();
        try {
        	if(ids == null) throw new Exception();
        } catch (Exception e) {
        	log.error("delete articles error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "loadYearListForCustomer", method = RequestMethod.POST)
    public QueryCustomerYearResponse loadYearListForCustomer(@ModelAttribute QueryCustomerYearRequest request) {
        QueryCustomerYearResponse response = new QueryCustomerYearResponse();
        try {
            response = customerInfoManagerService.loadYearListForCustomer(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query tags error.", e);
        }
        return response;
    }

    /**
     * 备份客商数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "backupCustomerData", method = RequestMethod.POST)
    public BaseResponse backupCustomerData() {
        BaseResponse response = new BaseResponse();
        try {
            customerInfoManagerService.backupCustomerData();
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("backup customer info error.", e);
        }
        return response;
    }

    /**
     * 分页查询备份客商数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAllCustomerBackupInfosByPage")
    public QueryCustomerResponse queryAllCustomerBackupInfosByPage(@ModelAttribute QueryCustomerBackupInfoRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.queryAllCustomerBackupInfosByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customers error.", e);
        }
        return response;
    }

    /**
     * 显示客商详细页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "directToCustomerBackupInfo")
    public ModelAndView directToCustomerBackupInfo(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/databackup/customerBackupDetailInfo");
        modelAndView.addObject("id", id);
        TVisitorBackupInfo tVisitorBackupInfo = customerInfoManagerService.loadCustomerBackupInfoById(id);
        modelAndView.addObject("customer", tVisitorBackupInfo);
        return modelAndView;
    }

    /**
     * 分页查询参观团信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryVisitorGroupByPage")
    public QueryCustomerResponse queryVisitorGroupByPage(@ModelAttribute QueryVisitorGroupRequest request) {
        QueryCustomerResponse response = new QueryCustomerResponse();
        try {
            response = customerInfoManagerService.queryVisitorGroupByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customers error.", e);
        }
        return response;
    }

    /**
     * 显示参观团详细页面
     *
     * @param groupID
     * @return
     */
    @RequestMapping(value = "directToVisitorGroupInfo")
    public ModelAndView directToVisitorGroupInfo(@RequestParam("leaderID") Integer groupID) {
        ModelAndView modelAndView = new ModelAndView("user/customer/visitorGroupDetailInfo");
        modelAndView.addObject("leaderID", groupID);
        TVisitorGroupInfo tVisitorGroupInfo = customerInfoManagerService.loadVisitorGroupDetailInfoById(groupID);
        modelAndView.addObject("tVisitorGroupInfo", tVisitorGroupInfo);
        return modelAndView;
    }

    /**
     * 根据id查询参观团对应成员列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryVisitorMemberListByGroupId", method = RequestMethod.POST)
    public QueryExhibitorResponse queryVisitorMemberListByGroupId(@ModelAttribute QueryVisitorMemberListRequest request) {
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TVisitorMemberInfo> tVisitorMemberInfoList = customerInfoManagerService.loadVisitorMemberListByLeaderID(request.getLeaderID());
            response.setRows(tVisitorMemberInfoList);
            response.setResultCode(0);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response = new QueryExhibitorResponse();
            log.error("query visitor member list error.",e);
            response.setDescription("query visitor member list error.");
        }
        return response;
    }

    /**
     * 修改参展团信息
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyGroupInfo", method = RequestMethod.POST)
    public BaseResponse modifyGroupInfo(@ModelAttribute ModifyGroupInfo request,
                                        @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            TVisitorGroupInfo tVisitorGroupInfo = customerInfoManagerService.loadVisitorGroupDetailInfoById(request.getLeaderID());
            if(tVisitorGroupInfo != null){
                tVisitorGroupInfo.setGroupName(request.getGroupName());
                tVisitorGroupInfo.setUpdateTime(new Date());
                visitorGroupDao.update(tVisitorGroupInfo);
                response.setResultCode(0);
            }
        } catch (Exception e) {
            log.error("modify group info error.", e);
            response.setDescription("modify group info error.");
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 根据id查询参观团对应成员列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyGroupMemberInfo", method = RequestMethod.POST)
    public BaseResponse modifyGroupMemberInfo(@ModelAttribute ModifyGroupMemberInfo request,
                                              @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            if(1 == request.getGroup_member_Identify()){
                List<TVisitorMemberInfo> tVisitorMemberInfoList = customerInfoManagerService.loadVisitorMemberListByLeaderID(request.getLeaderID());
                for(TVisitorMemberInfo tVisitorMemberInfo:tVisitorMemberInfoList){
                    tVisitorMemberInfo.setIsLeader(0);
                    visitorMemberDao.update(tVisitorMemberInfo);
                }
            }
            TVisitorMemberInfo tVisitorMemberInfo = visitorMemberDao.query(request.getId());
            if(tVisitorMemberInfo != null){
                tVisitorMemberInfo.setIsLeader(request.getGroup_member_Identify());
                tVisitorMemberInfo.setCompany(request.getGroup_member_company());
                tVisitorMemberInfo.setMemberName(request.getGroup_member_name());
                tVisitorMemberInfo.setPosition(request.getGroup_member_position());
                tVisitorMemberInfo.setMobile(request.getGroup_member_telphone());
                tVisitorMemberInfo.setEmail(request.getGroup_member_email());
                tVisitorMemberInfo.setAddress(request.getGroup_member_address());
                tVisitorMemberInfo.setUpdateTime(new Date());
                visitorMemberDao.update(tVisitorMemberInfo);
            }
            response.setResultCode(0);
        } catch (Exception e) {
            log.error("modify group member error.", e);
            response.setDescription("modify group member error.");
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 激活或注销客商信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "setGroupActiveOrUnactive", method = RequestMethod.POST)
    public BaseResponse setGroupActiveOrUnactive(@ModelAttribute QueryCustomerRequest request,
                                                    @RequestParam(value = "leaderID")Integer leaderID) {
        BaseResponse response = new BaseResponse();
        try {
            TVisitorGroupInfo tVisitorGroupInfo = customerInfoManagerService.loadVisitorGroupDetailInfoById(leaderID);
            if(tVisitorGroupInfo != null){
                if(1 == tVisitorGroupInfo.getIsDisabled()){
                    tVisitorGroupInfo.setIsDisabled(0);
                }else if(0 == tVisitorGroupInfo.getIsDisabled()){
                    tVisitorGroupInfo.setIsDisabled(1);
                }
                visitorGroupDao.update(tVisitorGroupInfo);
                response.setResultCode(0);
            }else{
                response.setDescription("对应参展团信息不存在！");
                response.setResultCode(1);
            }
        } catch (Exception e) {
            response.setDescription("modify group active or unactive error.");
            log.error("modify group active or unactive error.", e);
            response.setResultCode(1);
        }
        return response;
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

    @ResponseBody
    @RequestMapping(value = "msgAllGroupMember", method = RequestMethod.POST)
    public BaseResponse msgAllGroupMember(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                          @RequestParam(value = "leaderID", defaultValue = "") Integer[] leaderID,
                                          @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            String mobileSubject = "给参展团发送短信";
            String mobileContent = getMsgContent(1);
            List<TVisitorMemberInfo> tVisitorMemberInfoList = new ArrayList<TVisitorMemberInfo>();
            if(cids[0] == -1){
                tVisitorMemberInfoList = customerInfoManagerService.loadVisitorMemberListByLeaderID(leaderID[0]);
            }else{
                tVisitorMemberInfoList = customerInfoManagerService.loadVisitorMemberListById(cids);
            }
            if(tVisitorMemberInfoList.size()>0) {
                StringBuffer mobileStr = new StringBuffer();
                //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneMsgFileName);
                for(int i=0;i<tVisitorMemberInfoList.size();i++) {
                    TVisitorMemberInfo tVisitorMemberInfo = tVisitorMemberInfoList.get(i);
                    EmailPattern pattern = new EmailPattern();
                    String telphoneTemp = tVisitorMemberInfo.getMobile();
                    if (pattern.isMobileNO(telphoneTemp)) {
                        sendMsgByTelphone(principle, tVisitorMemberInfo, mobileSubject, mobileContent, telphoneTemp, 1);
                    }
                }
            }
        } catch (Exception e) {
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "emailAllGroupMember", method = RequestMethod.POST)
    public BaseResponse emailAllGroupMember(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                            @RequestParam(value = "leaderID", defaultValue = "") Integer[] leaderID,
                                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<TVisitorMemberInfo> tVisitorMemberInfoList = new ArrayList<TVisitorMemberInfo>();
            if(cids[0] == -1){
                tVisitorMemberInfoList = customerInfoManagerService.loadVisitorMemberListByLeaderID(leaderID[0]);
            }else{
                tVisitorMemberInfoList = customerInfoManagerService.loadVisitorMemberListById(cids);
            }
            if(tVisitorMemberInfoList.size()>0) {
                //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                for(int i=0;i<tVisitorMemberInfoList.size();i++) {
                    Email email = initEmailTemplate();
                    TVisitorMemberInfo tVisitorMemberInfo = tVisitorMemberInfoList.get(i);
                    EmailPattern pattern = new EmailPattern();
                    if(tVisitorMemberInfo != null && pattern.isEmailPattern(tVisitorMemberInfo.getEmail())) {
                        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
                        Date date = new Date();
                        String str = bartDateFormat.format(date);

                        /*ReadWriteEmailAndMsgFile.setFileContentIsNull();
                        ReadWriteEmailAndMsgFile.readTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                        ReadWriteEmailAndMsgFile.writeTxtFile("参观展团邮件：" + str + ", 给邮箱为：" + tVisitorMemberInfo.getEmail() + "账号发邮件。", ReadWriteEmailAndMsgFile.stoneEmailFileName);
                        */
                        //log.info("======给境内邮箱为：" + customer.getEmail() + "账号发邮件======");
                        email.setFlag(1);//专业采购商

                        email.setEmailType(1);
                        email.setCheckingNo(tVisitorMemberInfo.getCheckingNo());
                        email.setCustomerId(tVisitorMemberInfo.getId());
                        email.setCountry(0);
                        email.setUseTemplate(false);
                        email.setCompany(tVisitorMemberInfo.getCompany());
                        email.setName(tVisitorMemberInfo.getMemberName());
                        if(tVisitorMemberInfo.getPosition() == null || tVisitorMemberInfo.getPosition() == ""){
                            email.setPosition("");
                        } else {
                            email.setPosition(tVisitorMemberInfo.getPosition());
                        }
                        email.setRegID(tVisitorMemberInfo.getCheckingNo());
                        email.setReceivers(tVisitorMemberInfo.getEmail());

                        customerInfoManagerService.updateGroupEmailNum(leaderID[0], tVisitorMemberInfo.getId());
                        mailService.sendMailByAsyncAnnotationMode(email);
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

    private void saveMsgInfoLog(ManagerPrinciple principle, TVisitorMemberInfo tVisitorMemberInfo,
                                String mobileContent, String mobileSubject, String telphone, Integer content) {
        //保存短信发送记录
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
        visitorMsgLog.setCustomerID(tVisitorMemberInfo.getId());
        visitorLogMsgService.insertLogMsg(visitorMsgLog);

        //更新对应的短信数量值
        tVisitorMemberInfo.setUpdateTime(new Date());
        if(tVisitorMemberInfo.getMsgNum() == null){
            tVisitorMemberInfo.setMsgNum(1);
        }else{
            tVisitorMemberInfo.setMsgNum(tVisitorMemberInfo.getMsgNum() + 1);
        }
        visitorMemberDao.update(tVisitorMemberInfo);
    }

    /**
     * 异步发送
     */
    public void sendMsgByAsynchronousMode(final ManagerPrinciple principle, final TVisitorMemberInfo tVisitorMemberInfo,
                                          final String mobileContent, final String mobileSubject, final Integer type) {
        if (log.isDebugEnabled()) {
            log.debug("当前短信采取异步发送....");
        }
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet post = new HttpGet("http://113.106.91.228:9000/WebService.asmx/mt2?Sn=SDK100&Pwd=123321&mobile="
                            + tVisitorMemberInfo.getMobile() + "&content=" + mobileContent);
                    HttpResponse response = httpClient.execute(post);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        saveMsgInfoLog(principle, tVisitorMemberInfo, mobileContent, mobileSubject, tVisitorMemberInfo.getMobile(), type);
                        customerInfoManagerService.updateCustomerMsgNum(tVisitorMemberInfo.getId());
                        log.info("群发短信任务完成");
                    } else {
                        log.error("群发短信失败，错误码：" + response.getStatusLine().getStatusCode());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    private void sendMsgByTelphone(ManagerPrinciple principle, TVisitorMemberInfo tVisitorMemberInfo, String mobileSubject,
                                   String mobileContent, String telphone, Integer content){
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
        Date date = new Date();
        String str = bartDateFormat.format(date);
        /*ReadWriteEmailAndMsgFile.setFileContentIsNull();
        ReadWriteEmailAndMsgFile.readTxtFile(ReadWriteEmailAndMsgFile.stoneMsgFileName);*/
        try {
            //ReadWriteEmailAndMsgFile.writeTxtFile("给参展团发短信：" + str + ", 给境内手机号为：" + telphone + "发短信。", ReadWriteEmailAndMsgFile.stoneMsgFileName);
            String mobileContentTemp = mobileContent;
            if(1 == content && tVisitorMemberInfo != null) {
                mobileContent = mobileContentTemp.replace("@@_CHECKINGNUMBER_@@",tVisitorMemberInfo.getCheckingNo());
                //发送短信
                sendMsgByAsynchronousMode(principle, tVisitorMemberInfo, mobileContent, mobileSubject, content);
            }
        } catch (Exception e) {
            log.info("给参展团发短信，给手机号为：" + telphone + "发短信，出错了======");
            e.printStackTrace();
        }
    }

    /**
     * 导入参观团账号
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="upload/groupcustomer", method={RequestMethod.POST,RequestMethod.GET})
    public List<String> importGroupCustomer(@RequestParam MultipartFile file) throws IOException {
        File importFile = uploadGroupCustomer(file, "\\import", FilenameUtils.getBaseName(file.getOriginalFilename()) + new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        List<String> report = importGroupCustomerInfo(importFile);
        return report;
    }

    public File uploadGroupCustomer(@RequestParam MultipartFile file, String destDir, String fileName){
        String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
        if(StringUtils.isEmpty(fileName))
            fileName = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        if(StringUtils.isNotEmpty(destDir))
            destDir = appendix_directory + destDir;
        else destDir = appendix_directory;
        File targetFile = new File(destDir, fileName);
        if(!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFile;
    }

    public List<String> importGroupCustomerInfo(File importFile) {
        Integer count = 0;
        List<String> report = new ArrayList<String>();
        try {
            Workbook book = Workbook.getWorkbook(importFile);
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            // 得到单元格
            for (int j = 1; j < sheet.getRows(); j++) {
                WCustomer customer = new WCustomer();
                customer.setIsDisabled(false);
                customer.setIsActivated(1);
                customer.setRemark("2017届参观团数据");
                customer.setCreatedTime(new Date());
                customer.setUpdateTime(new Date());
                for (int i = 0; i < 4; i++) {
                    Cell cell = sheet.getCell(i, j);
                    switch (i) {
                        case 0:	//公司名
                            customer.setCompany(cell.getContents());
                            break;
                        case 1:	//姓名
                            customer.setFirstName(cell.getContents());
                            break;
                        case 2:	//职位
                            customer.setPosition(cell.getContents());
                            break;
                        case 3:	//国家
                            String countryValue = cell.getContents().trim();
                            if(StringUtils.isNotEmpty(countryValue)){
                                customer.setCountry(Integer.parseInt(countryValue));
                            }
                            break;
                        default:
                            break;
                    }
                }
                customerInfoManagerService.saveImportGroupMemberInfo(customer);
                count ++;
            }
            report.add("共导入:" + count + "条数据");
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    /**
     * 分页查询境外客商问卷调查数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryForeignCustomerFeedBackByPage")
    public QueryCustomerFeedBackResponse queryForeignCustomerFeedBackByPage(@ModelAttribute QueryCustomerFeedBackRequest request) {
        QueryCustomerFeedBackResponse response = new QueryCustomerFeedBackResponse();
        try {
            response = customerInfoManagerService.queryForeignCustomerFeedBackByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customer feed back data error.", e);
        }
        return response;
    }

    /**
     * 显示境外客商问卷调查详细页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "directToForeignCustomerFeedBackInfo")
    public ModelAndView directToForeignCustomerFeedBackInfo(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", id);
        TVisitorFeedBack visitorFeedBack = customerInfoManagerService.loadForeignCustomerFeedBackInfoById(id);
        modelAndView.addObject("visitorFeedBack", visitorFeedBack);
        modelAndView.setViewName("user/customer/foreignCustomerFeedBackInfo");
        return modelAndView;
    }

    /**
     * 查询客商问卷调查题目
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryForeignCustomerFeedBackTypeList")
    public QueryCustomerFeedBackResponse queryForeignCustomerFeedBackTypeList(@ModelAttribute QueryTagRequest request) {
        QueryCustomerFeedBackResponse response = new QueryCustomerFeedBackResponse();
        List<TVisitorFeedBackType> visitorFeedBackTypeList = visitorFeedBackTypeService.loadAllVisitorFeedBackType();
        if(visitorFeedBackTypeList != null && visitorFeedBackTypeList.size()>0){
            response.setResultCode(0);
            response.setRows(visitorFeedBackTypeList);
            response.setTotal(visitorFeedBackTypeList.size());
        }else{
            response.setResultCode(1);
            response.setDescription("问卷调查数据题目为空");
        }
        return response;
    }

    /**
     * 根据问卷调查类型进行统计
     *
     * @param feedbackId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "countForeignCustomerFeedBackByFeedBackId")
    public CountVisitorFeedBakcInfoResponse countForeignCustomerFeedBackByFeedBackId(@RequestParam("feedbackId") Integer feedbackId,
                                                                                     @RequestParam("rankNum") Integer rankNum) {
        CountVisitorFeedBakcInfoResponse countVisitorFeedBakcInfoResponse = new CountVisitorFeedBakcInfoResponse();
        TVisitorFeedBackType visitorFeedBackType = visitorFeedBackTypeService.loadTVisitorFeedBackTypeById(feedbackId);
        int showFeedBackSize = rankNum;
        int countFeedBackTotalSize = 0;
        int behindShowFeedBackSize = 0;
        if(visitorFeedBackType != null){
            if(1 == visitorFeedBackType.getQuestion_type() || 2 == visitorFeedBackType.getQuestion_type()){
                List<CountVisitorFeedBackInfo> countVisitorFeedBackInfoArrayListResult = new ArrayList<CountVisitorFeedBackInfo>();
                List<CountVisitorFeedBackInfo> countVisitorFeedBackInfoArrayList = visitorFeedBackService.loadTVisitorFeedBackByAnswerName(visitorFeedBackType.getQuestion_alian());
                if(showFeedBackSize == -1){
                    showFeedBackSize = countVisitorFeedBackInfoArrayList.size();
                }
                for(int k=0;k<countVisitorFeedBackInfoArrayList.size();k++){
                    CountVisitorFeedBackInfo countVisitorFeedBackInfo = countVisitorFeedBackInfoArrayList.get(k);
                    if(StringUtils.isNotEmpty(countVisitorFeedBackInfo.getValue())){
                        countFeedBackTotalSize += Integer.parseInt(countVisitorFeedBackInfo.getValue());
                        if(k<showFeedBackSize){
                            behindShowFeedBackSize += Integer.parseInt(countVisitorFeedBackInfo.getValue());
                        }
                    }
                }
                if(countVisitorFeedBackInfoArrayList != null){
                    if(countVisitorFeedBackInfoArrayList.size()>showFeedBackSize){
                        for(int i=0;i<showFeedBackSize;i++){
                            if(countVisitorFeedBackInfoArrayList.get(i).getName().equalsIgnoreCase("5")){
                                countVisitorFeedBackInfoArrayList.get(i).setName("Strong Agree");
                            }else if(countVisitorFeedBackInfoArrayList.get(i).getName().equalsIgnoreCase("4")){
                                countVisitorFeedBackInfoArrayList.get(i).setName("Agree");
                            }else if(countVisitorFeedBackInfoArrayList.get(i).getName().equalsIgnoreCase("3")){
                                countVisitorFeedBackInfoArrayList.get(i).setName("Neither");
                            }else if(countVisitorFeedBackInfoArrayList.get(i).getName().equalsIgnoreCase("2")){
                                countVisitorFeedBackInfoArrayList.get(i).setName("DisAgree");
                            }else if(countVisitorFeedBackInfoArrayList.get(i).getName().equalsIgnoreCase("1")){
                                countVisitorFeedBackInfoArrayList.get(i).setName("Strong Disagree");
                            }
                            countVisitorFeedBackInfoArrayListResult.add(countVisitorFeedBackInfoArrayList.get(i));
                        }
                        CountVisitorFeedBackInfo countVisitorFeedBackInfoOther = new CountVisitorFeedBackInfo();
                        countVisitorFeedBackInfoOther.setName("其它");
                        countVisitorFeedBackInfoOther.setValue(String.valueOf(countFeedBackTotalSize - behindShowFeedBackSize));
                        countVisitorFeedBackInfoArrayListResult.add(countVisitorFeedBackInfoOther);
                        JSONArray resultJson = JSONArray.fromObject(countVisitorFeedBackInfoArrayListResult);
                        countVisitorFeedBakcInfoResponse.setResult(resultJson.toString());
                        countVisitorFeedBakcInfoResponse.setTotal(countFeedBackTotalSize);
                    }else{
                        for(CountVisitorFeedBackInfo countVisitorFeedBackInfo:countVisitorFeedBackInfoArrayList){
                            if(countVisitorFeedBackInfo.getName().equalsIgnoreCase("5")){
                                countVisitorFeedBackInfo.setName("Strong Agree");
                            }else if(countVisitorFeedBackInfo.getName().equalsIgnoreCase("4")){
                                countVisitorFeedBackInfo.setName("Agree");
                            }else if(countVisitorFeedBackInfo.getName().equalsIgnoreCase("3")){
                                countVisitorFeedBackInfo.setName("Neither");
                            }else if(countVisitorFeedBackInfo.getName().equalsIgnoreCase("2")){
                                countVisitorFeedBackInfo.setName("DisAgree");
                            }else if(countVisitorFeedBackInfo.getName().equalsIgnoreCase("1")){
                                countVisitorFeedBackInfo.setName("Strong Disagree");
                            }
                            countVisitorFeedBackInfoArrayListResult.add(countVisitorFeedBackInfo);
                        }
                        JSONArray resultJson = JSONArray.fromObject(countVisitorFeedBackInfoArrayListResult);
                        countVisitorFeedBakcInfoResponse.setResult(resultJson.toString());
                        countVisitorFeedBakcInfoResponse.setTotal(countFeedBackTotalSize);
                    }
                }else{
                    countVisitorFeedBakcInfoResponse.setResultCode(1);
                    countVisitorFeedBakcInfoResponse.setDescription("没有相关问卷调查数据");
                }
            }else if(3 == visitorFeedBackType.getQuestion_type()){
                List<CountVisitorFeedBackInfo> countVisitorFeedBackInfoArrayListResult = new ArrayList<CountVisitorFeedBackInfo>();
                feedBackCountTreeMap = new TreeMap<String, Integer>();
                feedBackCountTreeMap.clear();
                String hql = "select " + visitorFeedBackType.getQuestion_alian() + " from t_visitor_Feedback ";
                List<Map<String, Object>> countFeedBackList = jdbcTemplate.queryForList(hql);
                for (int i = 0; i < countFeedBackList.size(); i++) {
                    Map exhibitorMap = countFeedBackList.get(i);
                    Iterator it = exhibitorMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        Object value = entry.getValue();
                        if(value != null){
                            countProssString(value.toString());
                        }
                    }
                }

                Set<Map.Entry<String, Integer>> set = feedBackCountTreeMap.entrySet();
                Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
                int showIndex = 0;
                while (iterator.hasNext()){
                    if(rankNum != -1 && showIndex >= rankNum){
                        break;
                    }
                    Map.Entry<String, Integer> entry = iterator.next();
                    String value = entry.getValue().toString();
                    String key = entry.getKey();
                    CountVisitorFeedBackInfo countVisitorFeedBackInfo = new CountVisitorFeedBackInfo();
                    countVisitorFeedBackInfo.setName(key);
                    countVisitorFeedBackInfo.setValue(value);
                    countVisitorFeedBackInfoArrayListResult.add(countVisitorFeedBackInfo);
                    showIndex ++ ;
                }
                JSONArray resultJson = JSONArray.fromObject(countVisitorFeedBackInfoArrayListResult);
                countVisitorFeedBakcInfoResponse.setResult(resultJson.toString());
                countVisitorFeedBakcInfoResponse.setTotal(countFeedBackTotalSize);
            }
        }else{
            countVisitorFeedBakcInfoResponse.setResultCode(1);
            countVisitorFeedBakcInfoResponse.setDescription("问卷调查数据类型不存在");
        }
        return countVisitorFeedBakcInfoResponse;
    }

    private TreeMap<String, Integer> countProssString(java.lang.String str) {
        java.lang.String[] strArray = str.split("\\|");
        if(StringUtils.isNotEmpty(strArray[0].trim())){
            java.lang.String[] value = strArray[0].trim().split(";");
            for(int i=0;i<value.length;i++){
                if (!feedBackCountTreeMap.containsKey(value[i].trim())) {
                    feedBackCountTreeMap.put(value[i].trim(), 1);
                } else {
                    int count = feedBackCountTreeMap.get(value[i].trim()) + 1;
                    feedBackCountTreeMap.put(value[i].trim(), count);
                }
            }
        }else{
            System.out.println("===值为空===");
        }
        return feedBackCountTreeMap;
    }

    public static TreeMap<String, Integer> Pross(String str) {
        String[] strArray = str.split("\\|");
        TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
        if(StringUtils.isNotEmpty(strArray[0].trim())){
            String[] value = strArray[0].trim().split(";");
            for(int i=0;i<value.length;i++){
                if (!tm.containsKey(value[i])) {
                    tm.put(value[i], 1);
                } else {
                    int count = tm.get(value[i]) + 1;
                    tm.put(value[i], count);
                }
            }
        }else{
            System.out.println("===值为空===");
        }

        return tm;
    }

    public static void main(String[] args) {
        TreeMap<String, Integer> tm = Pross("Making purchases; Discovering new products; Networking; Education | ");
        System.out.println(tm);

        TreeMap<String, Integer> tm1 = Pross("Making purchases; Discovering new products; Networking; Education; Other | Stone cutting machines");
        System.out.println(tm1);
    }
}
