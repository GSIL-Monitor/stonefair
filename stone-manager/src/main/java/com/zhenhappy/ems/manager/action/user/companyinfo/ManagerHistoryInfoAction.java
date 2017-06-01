package com.zhenhappy.ems.manager.action.user.companyinfo;

import com.zhenhappy.ems.dao.CustomerApplyEmailInfoDao;
import com.zhenhappy.ems.dao.TagDao;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.entity.Email;
import com.zhenhappy.ems.entity.TTag;
import com.zhenhappy.ems.entity.TVisitorTemplate;
import com.zhenhappy.ems.entity.VApplyEmail;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dao.companyinfo.HistoryCustomerInfoDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.dto.companyinfo.*;
import com.zhenhappy.ems.manager.entity.companyinfo.THistoryCustomer;
import com.zhenhappy.ems.manager.exception.DuplicateUsernameException;
import com.zhenhappy.ems.manager.service.CustomerTemplateService;
import com.zhenhappy.ems.manager.service.TagManagerService;
import com.zhenhappy.ems.manager.service.companyinfo.HistoryCustomerService;
import com.zhenhappy.ems.manager.sys.Constants;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.util.JXLExcelView;
import com.zhenhappy.ems.service.EmailMailService;
import com.zhenhappy.ems.service.managerrole.TUserInfoService;
import com.zhenhappy.ems.service.managerrole.TUserRoleService;
import com.zhenhappy.system.SystemConfig;
import com.zhenhappy.util.EmailPattern;
import com.zhenhappy.util.Page;
import com.zhenhappy.util.ReadWriteEmailAndMsgFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangxd on 2016-05-23.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class ManagerHistoryInfoAction extends BaseAction {

    private static Logger log = Logger.getLogger(ManagerHistoryInfoAction.class);
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private HistoryCustomerService historyCustomerService;
    @Autowired
    private CustomerTemplateService customerTemplaeService;
    @Autowired
    private CustomerApplyEmailInfoDao customerApplyEmailInfoDao;
    @Autowired
    private EmailMailService mailService;
    @Autowired
    private TUserRoleService roleService;
    @Autowired
    private TUserInfoService userInfoService;
    @Autowired
    private TagManagerService tagManagerService;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private HistoryCustomerInfoDao historyCustomerInfoDao;

    //国内历史客商数据
    @RequestMapping(value = "historyInlandCustomer")
    public String historyInlandCustomerIndex(){
        return "/user/companyinfo/inlandCustomer";
    }

    //国外历史客商数据
    @RequestMapping(value = "historyForeignCustomer")
    public String historyForeignCustomerIndex(){
        return "/user/companyinfo/foreignCustomer";
    }

    //门襟系统数据
    @RequestMapping(value = "placketSystemData")
    public String placketSystemData(){
        return "/user/companyinfo/placketSystemData";
    }

    //分享管理
    @RequestMapping(value = "managerShareInfo")
    public String managerShareInfo(){
        return "/user/companyinfo/managerShareInfo";
    }

    //高德地图试验
    @RequestMapping(value = "mapTest")
    public String mapTest(){
        return "/user/companyinfo/mapTest";
    }

    //会展地图试验
    @RequestMapping(value = "xicecmapTest")
    public String xicecmapTest(){
        return "/user/companyinfo/xicecTest";
    }

    /**
     * 导入客商信息
     * @param file
     * @param inlandOrForeign 1表示xlsx格式，即国内；2表示xls即国外
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="companyinfo/importCustomer", method={RequestMethod.POST,RequestMethod.GET})
    public ImportHistoryCustomerResponse importExhibitors(@RequestParam MultipartFile file,
                                                          @RequestParam Integer inlandOrForeign,
                                                          @RequestParam Integer isForceIncludeFlagValue,
                                                          @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) throws IOException {
        File importFile = callCenterUpload(file, "\\import", FilenameUtils.getBaseName(file.getOriginalFilename()) + new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        if(userInfo != null){
            ImportHistoryCustomerResponse report = historyCustomerService.importHistoryCustomer(importFile, userInfo1, inlandOrForeign, isForceIncludeFlagValue);
            return report;
        }else {
            return null;
        }
    }

    /**
     * 导入门襟系统数据
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="companyinfo/placketSystemData", method={RequestMethod.POST,RequestMethod.GET})
    public ImportHistoryCustomerResponse importPlacketSystemData(@RequestParam MultipartFile file,
                                                          @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) throws IOException {
        File importFile = callCenterUpload(file, "\\import", FilenameUtils.getBaseName(file.getOriginalFilename()) + new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        if(userInfo != null){
            ImportHistoryCustomerResponse report = historyCustomerService.importPlacketSystemData(importFile, userInfo1);
            return report;
        }else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "showWillImportCustomerInfo")
    public QueryHistoryCustomerResponse showWillImportCustomerInfo(@ModelAttribute QueryHistoryCustomerRequest request) {
        QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
        try {
            response = historyCustomerService.getWillImportCustomerList(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("show will import Customer Info error.", e);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "showIsExistCustomerInfo")
    public QueryHistoryCustomerResponse showIsExistCustomerInfo(@RequestParam(value = "willImportCheckedItems", defaultValue = "") Integer[] willImportCheckedItems,
                                                                @ModelAttribute QueryHistoryCustomerRequest request) {
        QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
        try {
            response = historyCustomerService.getIsExistCustomerList(request, willImportCheckedItems);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("show is exist Customer Info error.", e);
        }
        return response;
    }

    @RequestMapping("callcenter/upload")
    public File callCenterUpload(@RequestParam MultipartFile file, String destDir, String fileName){
        String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
        if(StringUtils.isEmpty(fileName)) fileName = new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        if(StringUtils.isNotEmpty(destDir)) destDir = appendix_directory + destDir;
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

    /**
     * 分页查询国内客商历史数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryHistoryInlandCustomersByPage")
    public QueryHistoryCustomerResponse queryHistoryInlandCustomersByPage(@ModelAttribute QueryHistoryCustomerRequest request,
                                                                          @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        TUserRole userRole = roleService.findOneRole(userInfo.getRoleId());
        try {
            response = historyCustomerService.queryHistoryCustomersByPage(request, userInfo1, userRole, 1);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query history customers error.", e);
        }
        return response;
    }

    /**
     * 分页查询国内客商历史数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryHistoryForeignCustomersByPage")
    public QueryHistoryCustomerResponse queryHistoryForeignCustomersByPage(@ModelAttribute QueryHistoryCustomerRequest request,
                                                                           @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        TUserRole userRole = roleService.findOneRole(userInfo1.getRoleId());
        try {
            response = historyCustomerService.queryHistoryCustomersByPage(request, userInfo1, userRole, 2);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query history customers error.", e);
        }
        return response;
    }

    /**
     * 分页查询国内客商历史数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryHistoryCustomersByPage")
    public QueryHistoryCustomerResponse queryHistoryCustomersByPage(@ModelAttribute QueryHistoryCustomerRequest request,
                                                                    @ModelAttribute Integer inlandOrForeignFlag,
                                                                    @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        QueryHistoryCustomerResponse response = new QueryHistoryCustomerResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        TUserRole userRole = roleService.findOneRole(userInfo1.getRoleId());
        try {
            response = historyCustomerService.queryHistoryCustomersByPage(request, userInfo1, userRole, inlandOrForeignFlag);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query history customers error.", e);
        }
        return response;
    }

    /**
     * 显示客商详细页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "directToHistoryCustomerInfo")
    public ModelAndView directToHistoryCustomerInfo(@RequestParam("id") Integer id, @RequestParam("inOrOut") Integer flag) {
        ModelAndView modelAndView;
        if(flag == 1){
            modelAndView = new ModelAndView("user/companyinfo/historyInlandCustomerInfo");
        } else {
            modelAndView = new ModelAndView("user/companyinfo/historyForeignCustomerInfo");
        }
        modelAndView.addObject("id", id);
        modelAndView.addObject("inOrOut", flag);
        modelAndView.addObject("customer", historyCustomerService.loadHistoryCustomerInfoById(id));
        return modelAndView;
    }

    /**
     * 修改客商账号
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyHistoryCustomerInfo", method = RequestMethod.POST)
    public BaseResponse modifyHistoryCustomerInfo(@ModelAttribute ModifyHistoryCustomer request,
                                                  @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        try {
            EmailPattern pattern = new EmailPattern();
            if(pattern.isEmailPattern(request.getEmail())) {
                List<THistoryCustomer> tHistoryCustomerList = historyCustomerService.loadHistoryCustomerByEmail(request.getEmail());
                if(tHistoryCustomerList == null){
                    historyCustomerService.modifyHistoryCustomerAccount(request,userInfo1);
                } else {
                    response.setDescription("邮箱不能重复");
                    response.setResultCode(3);
                    return response;
                }
            } else {
                response.setResultCode(3);
                response.setDescription("请输入有效的邮箱格式");
                return response;
            }
            if(request.getCountry() == null && !pattern.isMobileNO(request.getTelphone())) {
                response.setResultCode(2);
                response.setDescription("请输入有效的手机号码");
                return response;
            }
        } catch (DuplicateUsernameException e) {
            response.setResultCode(2);
            response.setDescription(e.getMessage());
        } catch (Exception e) {
            log.error("modify history customer account error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 插入客商资料
     * @param tids
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertHistoryCustomerInfo", method = RequestMethod.POST)
    public BaseResponse insertHistoryCustomerInfo(@RequestParam(value = "tids", defaultValue = "") Integer[] tids,
                                                  @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            if(tids == null) throw new Exception();
            historyCustomerService.insertCustomerInfoByTids(tids);
        } catch (Exception e) {
            log.error("remove customer info error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 忽略选中要插入的客商资料
     * @param tids
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ignoreHistoryCustomerInfo", method = RequestMethod.POST)
    public BaseResponse ignoreHistoryCustomerInfo(@RequestParam(value = "tids", defaultValue = "") Integer[] tids,
                                                  @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            if(tids == null) throw new Exception();
            historyCustomerService.ignoreCustomerInfoByTids(tids);
        } catch (Exception e) {
            log.error("remove customer info error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 删除客商资料
     * @param tids
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteHistoryCustomerInfo", method = RequestMethod.POST)
    public BaseResponse deleteHistoryCustomerInfo(@RequestParam(value = "tids", defaultValue = "") Integer[] tids,
                                                  @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        try {
            if(tids == null) throw new Exception();
            boolean flag1 = historyCustomerService.refreshIsExistCustomerList(tids, userInfo1);
            boolean flag2 = historyCustomerService.removeCustomerInfoByTids(tids, userInfo1);
            if(flag1 || flag2){
                response.setResultCode(0);
            }else{
                response.setResultCode(1);
            }
        } catch (Exception e) {
            log.error("remove customer info error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 修改历史重复客商信息
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="modifyHistoryRepeatCustomerInfo", method={RequestMethod.POST})
    public BaseResponse modifyHistoryRepeatCustomerInfo(@ModelAttribute ModifyHistoryCustomer request,
                                                        @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) throws IOException {
        BaseResponse response = new BaseResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        try {
            historyCustomerService.refreshRepeatCustomerList(request, userInfo1);
        } catch (Exception e) {
            log.error("remove customer info error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    //type:  1：表示给客商发信息完整邮件；2：表示给客商发邀请邮件
    @ResponseBody
    @RequestMapping(value = "emailAllHistoryInlandStoneCustomers", method = RequestMethod.POST)
    public BaseResponse emailAllHistoryInlandStoneCustomers(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                            @RequestParam(value = "type") Integer type,
                                                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse baseResponse = new BaseResponse();
        List<THistoryCustomer> customers = new ArrayList<THistoryCustomer>();
        List<TVisitorTemplate> customerTemplatesList = new ArrayList<TVisitorTemplate>();
        try {
            Email email = new Email();
            if(cids[0] == -1){
                customers = historyCustomerService.loadAllHistoryInlandCustomer();
            }
            else {
                customers = historyCustomerService.loadSelectedHistoryCustomers(cids);
            }
            if(customers.size()>0) {
                customerTemplatesList = customerTemplaeService.loadAllCustomerTemplate();
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
                //ReadWriteEmailAndMsgFile.creatTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                for(int i=0;i<customers.size();i++) {
                    THistoryCustomer customer = customers.get(i);
                    EmailPattern pattern = new EmailPattern();
                    if(customer != null && pattern.isEmailPattern(customer.getEmail())) {
                        /*SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
                        Date date = new Date();
                        String str = bartDateFormat.format(date);
                        ReadWriteEmailAndMsgFile.setFileContentIsNull();
                        ReadWriteEmailAndMsgFile.readTxtFile(ReadWriteEmailAndMsgFile.stoneEmailFileName);
                        ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给邮箱为：" + customer.getEmail() + "账号发邮件。", ReadWriteEmailAndMsgFile.stoneEmailFileName);*/
                        //log.info("======给境内邮箱为：" + customer.getEmail() + "账号发邮件======");
                        if(type == 1) {
                            email.setFlag(0);//展会观众
                            email.setEmailType(0);
                        } else if(type == 2) {
                            email.setFlag(1);//专业采购商
                            email.setEmailType(1);
                        }
                        /*email.setFlag(1);//专业采购商
                        email.setEmailType(1);*/
                        //email.setEmailType(customer.getIsProfessional());
                        //email.setCheckingNo(customer.getCheckingNo());
                        email.setCustomerId(customer.getId());
                        email.setCountry(customer.getCountry() == 44 ? 0:1);
                        email.setUseTemplate(false);
                        email.setCompany(customer.getCompany());
                        email.setName(customer.getContact());
                        email.setRegID(String.valueOf(customer.getId()));
                        //email.setName(customer.getFirstName());
                        if(customer.getPosition() == null || customer.getPosition() == ""){
                            email.setPosition("");
                        } else {
                            email.setPosition(customer.getPosition());
                        }
                        //email.setRegID(customer.getCheckingNo());
                        email.setReceivers(customer.getEmail());
                        //email.setReceivers("datea120@163.com");

                        mailService.sendMailByAsyncAnnotationMode(email);
                        customer.setUpdatetime(new Date());
                        historyCustomerInfoDao.update(customer);

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

    /**
     * 导出国内历史客商列表到Excel
     * @param cids
     * @return
     */
    @RequestMapping(value = "exportHistoryInlandCustomersToExcel", method = RequestMethod.POST)
    public ModelAndView exportHistoryInlandCustomersToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        Map model = new HashMap();
        List<THistoryCustomer> customers = new ArrayList<THistoryCustomer>();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        if(userInfo1 != null){
            if(cids[0] == -1){
                if(userInfo1.getRoleId() == 1){
                    customers = historyCustomerService.loadAllHistoryInlandCustomer();
                }else {
                    if(StringUtil.isNotEmpty(userInfo1.getShareId())){
                        customers = historyCustomerService.loadAllHistoryInlandCustomerByowner(userInfo1.getShareId());
                    }
                }
            }
            else{
                customers = historyCustomerService.loadSelectedHistoryInlandCustomers(cids);
            }
            List<ExportHistoryCustomerInfo> exportCustomer = historyCustomerService.exportHistoryInlandCustomer(customers);
            model.put("list", exportCustomer);
            String[] titles = new String[] { "类别", "公司", "地址", "联系人", "职位", "手机", "邮箱", "座机", "传真",  "网址", "备注" };
            model.put("titles", titles);
            String[] columns = new String[] { "cateory", "company", "address", "contact", "position", "telphone", "email", "fixtelphone", "fax", "website", "remark" };
            model.put("columns", columns);
            Integer[] columnWidths = new Integer[]{10,50,50,15,20,20,40,15,15,30,30};
            model.put("columnWidths", columnWidths);
            model.put("fileName", "国内历史客商基本信息.xls");
            model.put("sheetName", "国内历史客商基本信息");
        }
        return new ModelAndView(new JXLExcelView(), model);
    }

    /**
     * 导出国外历史客商列表到Excel
     * @param cids -1：表示所有的历史客商，其它值表示所选的客商
     * @param type 0：表示所有的历史客商资料；1：表示所有历史客商手机；2：表示所有历史客商邮箱
     * @param country -1：表示所有的
     * @return
     */
    @RequestMapping(value = "exportHistoryForeignCustomersToExcel", method = RequestMethod.POST)
    public ModelAndView exportHistoryForeignCustomersToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                             @RequestParam(value = "type", defaultValue = "") Integer[] type,
                                                             @RequestParam(value = "country", defaultValue = "") Integer[] country,
                                                             @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        Map model = new HashMap();
        List<THistoryCustomer> customers = new ArrayList<THistoryCustomer>();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        if(userInfo1 != null){
            if(type[0] == 0) {
                //表示导出所有的历史客商资料
                if(cids[0] == -1){
                    //roleId=6表示国外客理员，可以查看所有的国外普通管理员资料
                    if(userInfo1.getRoleId() == 1 || userInfo1.getRoleId() == 6){
                        if(country[0] == -1){
                            customers = historyCustomerService.loadAllHistoryForeignCustomer();
                        }else {
                            customers = historyCustomerService.loadAllHistoryForeignCustomerByCountryId(country[0]);
                        }
                    }else {
                        if(country[0] == -1){
                            if(StringUtil.isNotEmpty(userInfo1.getShareId())){
                                customers = historyCustomerService.loadAllHistoryForeignCustomerByOwner(userInfo1.getShareId());
                            }
                        }else {
                            if(StringUtil.isNotEmpty(userInfo1.getShareId())){
                                customers = historyCustomerService.loadAllHistoryForeignCustomerByCountryIdAndOwner(country[0], userInfo1.getShareId());
                            }
                        }
                    }
                }
                else {
                    customers = historyCustomerService.loadSelectedHistoryForeignCustomers(cids);
                }
                List<ExportHistoryCustomerInfo> exportCustomer = historyCustomerService.exportHistoryForeignCustomer(customers);
                model.put("sortby","country");
                model.put("list", exportCustomer);
                String[] titles = new String[] { "国家", "公司名", "联系人", "地址", "邮箱", "网站", "备用地址", "备注"};
                model.put("titles", titles);
                String[] columns = new String[] { "countryEx", "company", "contactEx", "address", "email", "website", "backupaddress", "remark" };
                model.put("columns", columns);
                Integer[] columnWidths = new Integer[]{20,25,60,60,40,30,30,20};
                model.put("columnWidths", columnWidths);
                model.put("fileName", "国外历史客商基本信息.xls");
                model.put("sheetName", "国外历史客商基本信息");
            }else if(type[0] == 1) {
                //表示只导出历史客商手机
                if(userInfo1.getRoleId() == 1 || userInfo1.getRoleId() == 6){
                    customers = historyCustomerService.loadAllHistoryForeignCustomer();
                }else {
                    if(StringUtil.isNotEmpty(userInfo1.getShareId())){
                        customers = historyCustomerService.loadAllHistoryForeignCustomerByOwner(userInfo1.getShareId());
                    }
                }
                List<ExportHistoryCustomerInfo> exportCustomer = historyCustomerService.exportHistoryForeignCustomer(customers);
                List<ExportHistoryCustomerInfo> exportTelphoneList = new ArrayList<ExportHistoryCustomerInfo>();
                if(exportCustomer != null && exportCustomer.size()>0){
                    for(int i=0;i<exportCustomer.size();i++){
                        ExportHistoryCustomerInfo exportHistoryCustomerInfo = exportCustomer.get(i);
                        if(StringUtils.isNotEmpty(exportHistoryCustomerInfo.getTelphone())){
                            exportTelphoneList.add(exportHistoryCustomerInfo);
                        }
                    }
                }
                model.put("list", exportTelphoneList);
                String[] titles = new String[] { "手机号"};
                model.put("titles", titles);
                String[] columns = new String[] { "telphone"};
                model.put("columns", columns);
                Integer[] columnWidths = new Integer[]{30};
                model.put("columnWidths", columnWidths);
                model.put("fileName", "国外历史客商所有手机号信息.xls");
                model.put("sheetName", "国外历史客商所有手机号信息");
            }else {
                //表示只导出历史客商邮箱
                if(userInfo1.getRoleId() == 1 || userInfo1.getRoleId() == 6){
                    customers = historyCustomerService.loadAllHistoryForeignCustomer();
                }else {
                    if(StringUtil.isNotEmpty(userInfo1.getShareId())){
                        customers = historyCustomerService.loadAllHistoryForeignCustomerByOwner(userInfo1.getShareId());
                    }
                }
                List<ExportHistoryCustomerInfo> exportCustomer = historyCustomerService.exportHistoryForeignCustomer(customers);
                List<ExportHistoryCustomerInfo> exportEmailList = new ArrayList<ExportHistoryCustomerInfo>();
                if(exportCustomer != null && exportCustomer.size()>0){
                    for(int i=0;i<exportCustomer.size();i++){
                        ExportHistoryCustomerInfo exportHistoryCustomerInfo = exportCustomer.get(i);
                        if(StringUtils.isNotEmpty(exportHistoryCustomerInfo.getEmail())){
                            exportEmailList.add(exportHistoryCustomerInfo);
                        }
                    }
                }
                model.put("list", exportEmailList);
                String[] titles = new String[] { "邮箱"};
                model.put("titles", titles);
                String[] columns = new String[] { "email"};
                model.put("columns", columns);
                Integer[] columnWidths = new Integer[]{30};
                model.put("columnWidths", columnWidths);
                model.put("fileName", "国外历史客商所有邮箱信息.xls");
                model.put("sheetName", "国外历史客商所有邮箱信息");
            }
        }
        return new ModelAndView(new JXLExcelView(), model);
    }

    /**
     * 判断资料库所属人
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadInfoOwnerList")
    public QueryTagResponse loadInfoOwnerList(@ModelAttribute EasyuiRequest request,
                                  @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        QueryTagResponse response = new QueryTagResponse();
        TUserInfo userInfo = (TUserInfo) principle.getAdmin();
        TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
        if(userInfo1 != null){
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());

            List<TTag> tags = new ArrayList<TTag>();
            if(userInfo1.getRoleId() == 1){
                tags = tagManagerService.loadAllTags();
            }else {
                String shareId = userInfo.getShareId();
                if(StringUtils.isNotEmpty(shareId)){
                    tags = new ArrayList<TTag>();
                    String[] tagArray = shareId.split(",");
                    for(int i=0;i<tagArray.length;i++){
                        TTag tag = tagManagerService.loadTagById(Integer.parseInt(tagArray[i]));
                        if(tag != null){
                            tags.add(tag);
                        }
                    }
                } else {
                    response.setDescription("该用户没有对应资料库");
                    response.setResultCode(1);
                }
            }
            response.setResultCode(0);
            response.setRows(tags);
            response.setTotal(page.getTotalCount());
        }else {
            response.setDescription("该用户没有对应资料库");
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 分页查询所有标签及对应的分享对象
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryTagsAndShared")
    public QueryTagAndSharedResponse queryTagsAndShared(@ModelAttribute QueryTagRequest request) {
        QueryTagAndSharedResponse response = new QueryTagAndSharedResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TTag> tags = tagDao.queryPageByHQL("select count(*) from TTag", "from TTag", new Object[]{}, page);
            List<TagAndShareInfo> tagAndShareInfoList = new ArrayList<TagAndShareInfo>();
            if(tags != null && tags.size()>0){
                for(int i=0;i<tags.size();i++){
                    TTag tag = tags.get(i);
                    TagAndShareInfo tagAndShareInfo = new TagAndShareInfo();
                    BeanUtils.copyProperties(tag, tagAndShareInfo);
                    TUserInfo tUserInfo = userInfoService.findOneUserInfoByOwnerId(tag.getId());
                    if(tUserInfo != null){
                        if(StringUtils.isNotEmpty(tUserInfo.getShareId())){
                            String[] sharesArray = tUserInfo.getShareId().split(",");
                            StringBuffer shareNameBuffer = new StringBuffer();
                            StringBuffer shareIdBuffer = new StringBuffer();
                            for(int k=0;k<sharesArray.length;k++){
                                String shareValue = sharesArray[k];
                                if(Integer.parseInt(shareValue) != tag.getId()){
                                    TTag tagTemp = tagManagerService.loadTagById(Integer.parseInt(shareValue));
                                    shareNameBuffer.append(tagTemp.getName() + "     ");
                                    shareIdBuffer.append(tagTemp.getId() + ",");
                                }
                            }
                            int index = shareIdBuffer.toString().lastIndexOf(",");
                            if(index >0){
                                String lastShareId = shareIdBuffer.toString().substring(0,index);
                                tagAndShareInfo.setSharers(shareNameBuffer.toString());
                                tagAndShareInfo.setSharerIds(lastShareId);
                            }else{
                                tagAndShareInfo.setSharerIds("");
                            }
                        }else {
                            tagAndShareInfo.setSharers("");
                            tagAndShareInfo.setSharerIds("");
                        }
                    }else {
                        tagAndShareInfo.setSharers("");
                        tagAndShareInfo.setSharerIds("");
                    }
                    tagAndShareInfoList.add(tagAndShareInfo);
                }
            }
            response.setResultCode(0);
            response.setRows(tagAndShareInfoList);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query tags error.", e);
        }
        return response;
    }

    /**
     * 设置资料分享者
     * @param sharerId  表示要设置资料库用户
     * @param oldSharers 表示老的分享者
     * @param newSharers 表示新的分享者
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyTagShare", method = RequestMethod.POST)
    public BaseResponse modifyTagShare(@RequestParam("sharerId") Integer sharerId,
                                       @RequestParam("oldSharers") String oldSharers,
                                       @RequestParam("newSharers") String newSharers,
                                       @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            TUserInfo tUserInfo = userInfoService.findOneUserInfoByOwnerId(sharerId);
            if(tUserInfo != null){
                if(StringUtils.isNotEmpty(newSharers)){
                    String[] newShares = newSharers.split(",");

                    StringBuffer newShareIdBuffer = new StringBuffer();
                    if(tUserInfo.getOwnerId() != null){
                        newShareIdBuffer.append(tUserInfo.getOwnerId() + ",");
                    }
                    List<TTag> tags = tagDao.queryByHql("from TTag", new Object[]{});
                    if(newShares.length>0){
                        for(int i=0;i<newShares.length;i++){
                            if(tags != null && tags.size()>0){
                                for(int k=0;k<tags.size();k++){
                                    TTag tag = tags.get(k);
                                    if(tag.getName().equals(String.valueOf(newShares[i]))){
                                        newShareIdBuffer.append(tag.getId() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    int index = newShareIdBuffer.toString().lastIndexOf(",");
                    String contactValue = newShareIdBuffer.toString().substring(0,index);
                    tUserInfo.setShareId(contactValue);
                    userInfoService.updateUserInfo(tUserInfo);
                }
            }
        } catch (Exception e) {
            log.error("modify tag share error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 忽略国外历史客商列表到Excel
     * @param cids -1：表示所有的历史客商，其它值表示所选的客商
     * @param type 1：表示国内历史客商资料；2：表示国外历史客商手机；
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "insertHistoryForeignCustomersToExcel", method = RequestMethod.POST)
    public BaseResponse insertHistoryForeignCustomersToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                             @RequestParam(value = "type", defaultValue = "") Integer[] type,
                                                             @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            List<THistoryCustomer> customers = new ArrayList<THistoryCustomer>();
            TUserInfo userInfo = (TUserInfo) principle.getAdmin();
            TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
            if(userInfo1 != null){
                if(type[0] == 1) {
                    //表示导出所有的历史客商资料
                    if(cids[0] == -1){
                        //表示是新增属性值都为空的对象
                        customers = historyCustomerService.loadAllHistoryInlandCustomerByowner(String.valueOf(userInfo1.getOwnerId()));
                    }
                    else {
                        //表示拷贝选中此对象的属性值
                        customers = historyCustomerService.loadSelectedHistoryInlandCustomers(cids);
                    }
                }else if(type[0] == 2) {
                    if(cids[0] == -1){
                        //表示是新增属性值都为空的对象
                        customers = historyCustomerService.loadAllHistoryForeignCustomerByOwner(String.valueOf(userInfo1.getOwnerId()));
                    }
                    else {
                        //表示拷贝选中此对象的属性值
                        customers = historyCustomerService.loadSelectedHistoryForeignCustomers(cids);
                    }
                }
            }
            historyCustomerService.ignoreCustomerInfoByTids(customers,userInfo1);
            response.setResultCode(0);
        }catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 忽略国外历史客商列表到Excel
     * @param cids -1：表示所有的历史客商，其它值表示所选的客商
     * @param type 1：表示国内历史客商资料；2：表示国外历史客商手机；
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "ignoreHistoryForeignCustomersToExcel", method = RequestMethod.POST)
    public BaseResponse ignoreHistoryForeignCustomersToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                             @RequestParam(value = "type", defaultValue = "") Integer[] type,
                                                             @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            List<THistoryCustomer> customers = new ArrayList<THistoryCustomer>();
            TUserInfo userInfo = (TUserInfo) principle.getAdmin();
            TUserInfo userInfo1 = userInfoService.findOneUserInfo(userInfo.getId());
            if(userInfo1 != null){
                if(type[0] == 1) {
                    //表示导出所有的历史客商资料
                    if(cids[0] == -1){
                        customers = historyCustomerService.loadAllHistoryInlandCustomerByowner(String.valueOf(userInfo1.getOwnerId()));
                    }
                    else {
                        customers = historyCustomerService.loadSelectedHistoryInlandCustomers(cids);
                    }
                }else if(type[0] == 2) {
                    if(cids[0] == -1){
                        customers = historyCustomerService.loadAllHistoryForeignCustomerByOwner(String.valueOf(userInfo1.getOwnerId()));
                    }
                    else {
                        customers = historyCustomerService.loadSelectedHistoryForeignCustomers(cids);
                    }
                }
            }
            historyCustomerService.ignoreCustomerInfoByTids(customers,userInfo1);
            response.setResultCode(0);
        }catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }
}
