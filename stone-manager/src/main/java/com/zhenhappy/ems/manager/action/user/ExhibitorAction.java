package com.zhenhappy.ems.manager.action.user;

import java.io.File;
import java.io.OutputStream;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.zhenhappy.ems.dao.JoinerDao;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.ems.manager.dao.xicecmap.XicecMapDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.dto.backupinfo.QueryExhibitorBackupRequest;
import com.zhenhappy.ems.manager.dto.backupinfo.QueryProductOrContactOrJoinerRequest;
import com.zhenhappy.ems.manager.dto.xicecmap.QueryXicecMapIntetionRequest;
import com.zhenhappy.ems.manager.dto.xicecmap.QueryXicecMapIntetionResponse;
import com.zhenhappy.ems.manager.entity.backupinfo.*;
import com.zhenhappy.ems.manager.entity.xicecmap.ReserverExhibitorInfoAndBooth;
import com.zhenhappy.ems.manager.entity.xicecmap.SelloutExhibitorInfoAndBooth;
import com.zhenhappy.ems.manager.entity.xicecmap.TXicecMapIntetion;
import com.zhenhappy.ems.manager.exception.DuplicateTagException;
import com.zhenhappy.ems.manager.service.ImportExportService;
import com.zhenhappy.ems.manager.service.TVisaManagerService;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.util.JXLExcelView;
import com.zhenhappy.ems.service.InvoiceExtendService;
import com.zhenhappy.ems.service.managerrole.TUserInfoService;
import com.zhenhappy.util.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.entity.TExhibitorBooth;
import com.zhenhappy.ems.manager.entity.TExhibitorTerm;
import com.zhenhappy.ems.manager.exception.DuplicateUsernameException;
import com.zhenhappy.ems.manager.service.ExhibitorManagerService;
import com.zhenhappy.ems.manager.service.TagManagerService;
import com.zhenhappy.ems.service.CountryProvinceService;
import com.zhenhappy.ems.service.MeipaiService;
import com.zhenhappy.system.SystemConfig;

/**
 * Created by wujianbin on 2014-04-22.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class ExhibitorAction extends BaseAction {

	private static Logger log = Logger.getLogger(ExhibitorAction.class);

    @Autowired
    private ExhibitorManagerService exhibitorManagerService;
    @Autowired
    private TagManagerService tagManagerService;
    @Autowired
    private MeipaiService meipaiService;
    @Autowired
    private InvoiceExtendService invoiceService;
    @Autowired
    private CountryProvinceService countryProvinceService;
    @Autowired
    private ImportExportAction importExportAction;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private JoinerDao joinerDao;
    @Autowired
    private ImportExportService importExportService;
    @Autowired
    private XicecMapDao xicecMapDao;
    @Autowired
    private TUserInfoService userInfoService;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 分页查询展商列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorsByPage")
    public QueryExhibitorResponse queryExhibitorsByPage(@ModelAttribute QueryExhibitorRequest request) {
        QueryExhibitorResponse response = null;
        try {
        	response = exhibitorManagerService.queryExhibitorsByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query exhibitors error.", e);
        }
        return response;
    }
    
    /**
     * 查询展商列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitors")
    public List<TExhibitor> queryExhibitors() {
    	List<TExhibitor> response = new ArrayList<TExhibitor>();
        try {
        	response = exhibitorManagerService.loadAllExhibitors();
        } catch (Exception e) {
            log.error("query exhibitors error.", e);
        }
        return response;
    }
    
    /**
     * 根据eid查询展商
     * @param eid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorByEid")
    public TExhibitor queryExhibitorByEid(@RequestParam("eid") Integer eid) {
    	TExhibitor response = new TExhibitor();
        try {
        	response = exhibitorManagerService.loadExhibitorByEid(eid);
        } catch (Exception e) {
            log.error("query exhibitors error.", e);
        }
        return response;
    }

    @RequestMapping(value = "exhibitor")
    public ModelAndView directToCompany(@RequestParam("eid") Integer eid) {
        ModelAndView modelAndView = new ModelAndView("user/exhibitor/company");
        modelAndView.addObject("eid", eid);
        modelAndView.addObject("exhibitor", exhibitorManagerService.loadExhibitorByEid(eid));
        modelAndView.addObject("term", exhibitorManagerService.getExhibitorTermByEid(eid));
        modelAndView.addObject("booth", exhibitorManagerService.queryBoothByEid(eid));
        modelAndView.addObject("currentTerm", exhibitorManagerService.queryCurrentTermNumber());
        modelAndView.addObject("exhibitorInfo", exhibitorManagerService.loadExhibitorInfoByEid(eid));
        /*石材展需求开始*/
//        modelAndView.addObject("exhibitorMeipai", meipaiService.getMeiPaiByEid(eid));
        /*石材展需求结束*/
        modelAndView.addObject("invoice", invoiceService.getByEid(eid));
        return modelAndView;
    }
    
    /**
     * 添加展商账号
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addExhibitor", method = RequestMethod.POST)
    public BaseResponse addExhibitorAccount(@ModelAttribute AddExhibitorRequest request,
                                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	exhibitorManagerService.addExhibitor(request, principle.getAdmin().getId());
        } catch (DuplicateUsernameException e) {
            response.setResultCode(2);
            response.setDescription(e.getMessage());
        } catch (Exception e) {
            log.error("add exhibitor account error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 修改展商账号
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitor", method = RequestMethod.POST)
    public BaseResponse modifyExhibitorAccount(@ModelAttribute ModifyExhibitorRequest request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            exhibitorManagerService.modifyExhibitorAccount(request, principle.getAdmin().getId());
        } catch (DuplicateUsernameException e) {
            response.setResultCode(2);
            response.setDescription(e.getMessage());
        } catch (Exception e) {
            log.error("modify exhibitor account error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    @ResponseBody
    @RequestMapping(value = "activeExhibitor", method = RequestMethod.POST)
    public BaseResponse activeExhibitor(@ModelAttribute ActiveExhibitorRequest request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            TExhibitorTerm term = new TExhibitorTerm();
            term.setEid(request.getEid());
            if (request.getId() != null) {
                term.setId(request.getId());
                term.setUpdateUser(principle.getAdmin().getId());
            } else {
                term.setCreateUser(principle.getAdmin().getId());
            }
            term.setCreateTime(new Date());
            //set base data
            term.setBoothNumber(request.getBoothNumber());
            term.setJoinTerm(request.getTerm());
            term.setIsDelete(0);
            term.setMark(request.getMark());
            exhibitorManagerService.activeExhibitor(term);
        } catch (Exception e) {
            log.error("active exhibitor error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 绑定展位号
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "bindBooth", method = RequestMethod.POST)
    public BaseResponse bindBooth(@ModelAttribute BindBoothRequest request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            TExhibitorBooth booth = new TExhibitorBooth();
            if (request.getId() != null) {
                booth.setId(request.getId());
                booth.setUpdateUser(principle.getAdmin().getId());
                booth.setUpdateTime(new Date());
            } else {
                booth.setCreateTime(new Date());
                booth.setCreateUser(principle.getAdmin().getId());
            }
            booth.setEid(request.getEid());
            //set base data
            booth.setBoothNumber(request.getBoothNumber().trim());
            if(request.getExhibitionArea() == null || "".equals(request.getExhibitionArea())){
            	booth.setExhibitionArea(request.getBoothNumber().trim().substring(0,2) + "厅");
            }else{
                if(request.getExhibitionArea().contains("厅")) booth.setExhibitionArea(request.getExhibitionArea());
                else  booth.setExhibitionArea(request.getExhibitionArea() + "厅");
            }
            booth.setMark(request.getMark());
            exhibitorManagerService.bindBoothInfo(booth);
        } catch (Exception e) {
            log.error("bind exhibitor booth number error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 绑定展位号
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyBoothMark", method = RequestMethod.POST)
    public BaseResponse modifyBoothMark(@ModelAttribute BindBoothRequest request, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            TExhibitorBooth booth = exhibitorManagerService.queryBoothByEid(request.getEid());
            if(booth != null){
                booth.setMark(request.getMark());
                hibernateTemplate.update(booth);
            }
        } catch (Exception e) {
            log.error("modify booth type error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 批量修改所属人
     * @param eids
     * @param tag
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitorsTag", method = RequestMethod.POST)
    public BaseResponse modifyExhibitorsTag(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
                                            @RequestParam("tag") Integer tag,
                                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eids != null && tag != null){
        		exhibitorManagerService.modifyExhibitorsTag(eids, tag, principle.getAdmin().getId());
        	}
        } catch (Exception e) {
            log.error("modify exhibitors tag error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 批量修改展团
     * @param eids
     * @param group
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitorsGroup", method = RequestMethod.POST)
    public BaseResponse modifyExhibitorsGroup(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
    										  @RequestParam("group") Integer group, 
    										  @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eids != null && group != null){
        		exhibitorManagerService.modifyExhibitorsGroup(eids, group, principle.getAdmin().getId());
        	}
        } catch (Exception e) {
            log.error("modify exhibitors group error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 批量修改展区
     * @param eids
     * @param area
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitorsArea", method = RequestMethod.POST)
    public BaseResponse modifyExhibitorsArea(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
                                             @RequestParam("area") Integer area, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eids != null && area != null){
        		exhibitorManagerService.modifyExhibitorsArea(eids, area, principle.getAdmin().getId());
        	}
        } catch (Exception e) {
            log.error("modify exhibitors area error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 批量注销展商
     * @param eids
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "disableExhibitors", method = RequestMethod.POST)
    public BaseResponse disableExhibitors(@RequestParam(value = "eids", defaultValue = "") Integer[] eids, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eids != null){
        		exhibitorManagerService.disableExhibitors(eids, principle.getAdmin().getId());
        	}
        } catch (Exception e) {
            log.error("disable exhibitor error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 批量启用展商
     * @param eids
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "enableExhibitors", method = RequestMethod.POST)
    public BaseResponse enableExhibitors(@RequestParam(value = "eids", defaultValue = "") Integer[] eids, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eids != null){
        		exhibitorManagerService.enableExhibitor(eids, principle.getAdmin().getId());
        	}
        } catch (Exception e) {
            log.error("enable exhibitor error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 批量删除展商
     * @param eids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteExhibitors", method = RequestMethod.POST)
    public BaseResponse deleteExhibitors(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eids == null) throw new Exception();
        	exhibitorManagerService.deleteExhibitorByEids(eids);
        } catch (Exception e) {
        	log.error("delete exhibitors error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 查询所有国家
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAllCountry", method = RequestMethod.POST)
    public List<WCountry> queryAllCountry(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
    	List<WCountry> country = new ArrayList<WCountry>();
    	try {
        	country = countryProvinceService.loadAllCountry();
        } catch (Exception e) {
            log.error("query country error.", e);
        }
        return country;
    }
    
    /**
     * 查询所有省份
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAllProvince", method = RequestMethod.POST)
    public List<WProvince> queryAllProvince(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
    	List<WProvince> province = new ArrayList<WProvince>();
    	try {
    		province = countryProvinceService.loadAllProvince();
        } catch (Exception e) {
            log.error("query province error.", e);
        }
        return province;
    }
    
    /**
     * 通过countryId查询省份
     * @param countryId
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryProvinceByCountryId", method = RequestMethod.POST)
    public List<WProvince> queryProvinceByCountryId(@RequestParam("countryId") Integer countryId, @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
    	List<WProvince> province = new ArrayList<WProvince>();
        try {
        	province = countryProvinceService.loadProvinceByCountryId(countryId);
        } catch (Exception e) {
            log.error("query province by countryId error.", e);
        }
        return province;
    }
    
    /**
     * 修改展商基本信息
     * @param request
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "modifyExhibitorInfo", method = RequestMethod.POST)
    public BaseResponse modifyExhibitorInfo(@ModelAttribute ModifyExhibitorInfoRequest request, 
								    		@RequestParam("eid") Integer eid,
								    		@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
        	if(eid != null){
        		exhibitorManagerService.modifyExhibitorInfo(request, eid, principle.getAdmin().getId());
        	}else{
        		throw new Exception();
        	}
        } catch (Exception e) {
            log.error("modify exhibitor info error.", e);
            response.setResultCode(1);
        }
        return response;
    }
    
    /**
     * 上传Logo并修改展商基本信息
     * @param logoFile
     * @param request
     * @param eid
     * @param principle
     * @return
     */
    @ResponseBody
    @RequestMapping(value="upload/modifyInfo", method={RequestMethod.POST,RequestMethod.GET})
    public BaseResponse uploadModifyInfo(@RequestParam MultipartFile logoFile,
						    		  	 @ModelAttribute ModifyExhibitorInfoRequest request, 
						    		  	 @RequestParam("eid") Integer eid,
						    		  	 @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle){
    	BaseResponse response = new BaseResponse();
        try {
        	if(eid != null){
        		String oldLogoPath = exhibitorManagerService.loadExhibitorInfoByEid(eid).getLogo();
        		File logo = importExportAction.upload(logoFile, null, null);
        		if(StringUtils.isNotEmpty(oldLogoPath)){
        			File oldLogo = new File(oldLogoPath);
        			if(oldLogo.exists() == false) FileUtils.deleteQuietly(oldLogo);
        		}
        		request.setLogo(logo.getPath());
        		modifyExhibitorInfo(request, eid, principle);
        	}else{
        		throw new Exception();
        	}
        } catch (Exception e) {
            log.error("modify exhibitor info error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 显示Logo
     * @param response
     * @param eid
     */
    @RequestMapping(value = "showLogo", method = RequestMethod.GET)
    public void showLogo(HttpServletResponse response, @RequestParam("eid") Integer eid) {
        try {
            String logoFileName = exhibitorManagerService.loadExhibitorInfoByEid(eid).getLogo();
            if (StringUtils.isNotEmpty(logoFileName)) {
                File logo = new File(logoFileName);
                if (!logo.exists()) return;
				OutputStream outputStream = response.getOutputStream();
                FileUtils.copyFile(logo, outputStream);
                outputStream.close();
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示发票图片
     * @param response
     * @param eid
     */
    @RequestMapping(value = "showInvoiceImage", method = RequestMethod.GET)
    public void showInvoiceImage(HttpServletResponse response, @RequestParam("eid") Integer eid) {
        try {
            String logoFileName = invoiceService.getByEid(eid).getInvoice_image_address();
            if (StringUtils.isNotEmpty(logoFileName)) {
                OutputStream outputStream = response.getOutputStream();
                File logo = new File(logoFileName);
                if (!logo.exists()) {
                    return;
                }
                FileUtils.copyFile(new File(logoFileName), outputStream);
                outputStream.close();
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出所有参展人员列表
     * @param principle
     */
    @RequestMapping(value = "exportJoinerList", method = RequestMethod.POST)
    public ModelAndView exportJoinerList(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        Map model = new HashMap();
        List<TExhibitor> exhibitors = exhibitorManagerService.loadAllExhibitorList();

        List<ExportExhibitorJoiner> exportExhibitorJoiners = importExportService.exportExhibitorJoiners(exhibitors);
        model.put("list", exportExhibitorJoiners);
        String[] titles = new String[] { "展位号", "公司中文名", "公司英文名", "名字", "职位", "电话", "邮箱" };
        model.put("titles", titles);
        String[] columns = new String[] { "boothNumber", "company", "companye", "name", "position", "telphone", "email" };
        model.put("columns", columns);
        Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20};
        model.put("columnWidths", columnWidths);
        model.put("fileName", "展商参展人员信息.xls");
        model.put("sheetName", "展商参展人员信息");
        return new ModelAndView(new JXLExcelView(), model);

    }

    /**
     * 导出展会所有国内参展人员列表
     * @param principle
     */
    @RequestMapping(value = "exportCurrentInlandJoinersListToExcel", method = RequestMethod.POST)
    public ModelAndView exportCurrentInlandJoinersListToExcel(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        Map model = new HashMap();
        List<TExhibitor> exhibitors = exhibitorManagerService.loadAllInlandActiveExhibitorList();

        List<ExportExhibitorJoiner> exportExhibitorJoiners = importExportService.exportExhibitorJoiners(exhibitors);
        model.put("list", exportExhibitorJoiners);
        String[] titles = new String[] { "展位号", "公司中文名", "公司英文名", "姓名", "职位", "电话", "邮箱" };
        model.put("titles", titles);
        String[] columns = new String[] { "boothNumber", "company", "companye", "name", "position", "telphone", "email" };
        model.put("columns", columns);
        Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20};
        model.put("columnWidths", columnWidths);
        model.put("fileName", "展会所有参展人员信息.xls");
        model.put("sheetName", "展会所有参展人员信息");
        return new ModelAndView(new JXLExcelView(), model);

    }

    /**
     * 导出所有联系人员列表
     * @param principle
     */
    @RequestMapping(value = "exportContactList", method = RequestMethod.POST)
    public ModelAndView exportContactList(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        Map model = new HashMap();
        List<TExhibitor> exhibitors = exhibitorManagerService.loadAllExhibitorList();

        List<ExportContact> exportContacts = importExportService.exportContacts(exhibitors);
        model.put("list", exportContacts);
        String[] titles = new String[] { "展位号", "公司中文名", "公司英文名", "名字", "职位", "电话", "邮箱", "地址" };
        model.put("titles", titles);
        String[] columns = new String[] { "boothNumber", "company", "companye", "name", "position", "phone", "email", "address" };
        model.put("columns", columns);
        Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20};
        model.put("columnWidths", columnWidths);
        model.put("fileName", "展商联系人信息.xls");
        model.put("sheetName", "展商联系人信息");
        return new ModelAndView(new JXLExcelView(), model);
    }

    /**
     * 重置参展人员列表
     * @param principle
     */
    @ResponseBody
    @RequestMapping(value = "resetJoinerListToDefault", method = RequestMethod.POST)
    public BaseResponse resetJoinerListToDefault(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            List<TExhibitorJoiner> joiners = joinerDao.queryByHql("from TExhibitorJoiner", new Object[]{});
            for(TExhibitorJoiner joiner: joiners){
                joiner.setIsDelete(1);
                joiner.setAdminUpdateTime(new Date());
                joinerDao.update(joiner);
            }
            response.setResultCode(0);
        } catch (Exception e) {
            response.setResultCode(1);
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 分页查询展位预向列表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryXicecMapIntetionByPage")
    public QueryXicecMapIntetionResponse queryXicecMapIntetionByPage(@ModelAttribute QueryXicecMapIntetionRequest request) {
        QueryXicecMapIntetionResponse response = new QueryXicecMapIntetionResponse();
        try {
            response = exhibitorManagerService.queryXicecMapIntetionByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customers error.", e);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "addBoothIntetion", method = RequestMethod.POST)
    public BaseResponse addBoothIntetion(@ModelAttribute QueryXicecMapIntetionRequest request,
                                         @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            boolean result = exhibitorManagerService.isSellOutByBoothNum(request.getBooth_num());
            if(result){
                throw new DuplicateTagException("展位号已经卖出去，请重新填写");
            }else{
                TXicecMapIntetion tXicecMapIntetion = new TXicecMapIntetion();
                tXicecMapIntetion.setBooth_num(request.getBooth_num());
                tXicecMapIntetion.setTag(request.getTag());
                xicecMapDao.create(tXicecMapIntetion);
            }
        } catch (DuplicateTagException e) {
            response.setResultCode(2);
            response.setDescription("展位号已经卖出去，请重新填写");
        } catch (Exception e) {
            log.error("add booth intetion error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "modifyBoothIntetion", method = RequestMethod.POST)
    public BaseResponse modifyBoothIntetion(@ModelAttribute QueryXicecMapIntetionRequest request,
                                            @ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        BaseResponse response = new BaseResponse();
        try {
            boolean result = exhibitorManagerService.isSellOutByBoothNum(request.getBooth_num());
            if(result){
                throw new DuplicateTagException("展位号已经卖出去，请重新填写");
            }else{
                TXicecMapIntetion tXicecMapIntetion = xicecMapDao.query(request.getId());
                if(tXicecMapIntetion != null){
                    tXicecMapIntetion.setBooth_num(request.getBooth_num());
                    tXicecMapIntetion.setTag(request.getTag());
                    xicecMapDao.update(tXicecMapIntetion);
                }
            }
        } catch (Exception e) {
            log.error("modify booth intetion error.", e);
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 查询预定的展位信息
     * @param principle
     */
    @ResponseBody
    @RequestMapping(value = "getReserveExhibitorInfoAndBoothNum", method = RequestMethod.POST)
    public List<ReserverExhibitorInfoAndBooth> getReserveExhibitorInfoAndBoothNum(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        List<ReserverExhibitorInfoAndBooth> reserverExhibitorInfoAndBoothList = new ArrayList<ReserverExhibitorInfoAndBooth>();
        try {
            List<TXicecMapIntetion> tXicecMapIntetionList = xicecMapDao.queryByHql("from TXicecMapIntetion ", new Object[]{});
            for(TXicecMapIntetion tXicecMapIntetion:tXicecMapIntetionList){
                String tagName = "";
                if(tXicecMapIntetion.getTag() != null){
                    TUserInfo userInfo = userInfoService.findOneUserInfo(tXicecMapIntetion.getTag());
                    if(userInfo != null){
                        tagName = userInfo.getName();
                    }
                }
                ReserverExhibitorInfoAndBooth reserverExhibitorInfoAndBooth = new ReserverExhibitorInfoAndBooth(tXicecMapIntetion.getBooth_num(), tagName);
                reserverExhibitorInfoAndBoothList.add(reserverExhibitorInfoAndBooth);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserverExhibitorInfoAndBoothList;
    }

    /**
     * 根据本届展位查询对应的展商信息
     * @param principle
     */
    @ResponseBody
    @RequestMapping(value = "getSelloutExhibitorInfoAndBoothNum", method = RequestMethod.POST)
    public List<SelloutExhibitorInfoAndBooth> getSelloutExhibitorInfoAndBoothNum(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle) {
        List<SelloutExhibitorInfoAndBooth> selloutExhibitorInfoAndBoothList = new ArrayList<SelloutExhibitorInfoAndBooth>();
        try {
            List<TExhibitor> tExhibitorList = exhibitorManagerService.loadAllExhibitors();
            for(TExhibitor tExhibitor:tExhibitorList){
                TExhibitorBooth tExhibitorBooth = exhibitorManagerService.queryBoothByEid(tExhibitor.getEid());
                if(tExhibitorBooth.getBoothNumber() != null){
                    String boothNumberValue = tExhibitorBooth.getBoothNumber().replace(", ",",");
                    if(boothNumberValue.indexOf("-") > 0 && boothNumberValue.split("-")[1].length() > 2){
                        List<SelloutExhibitorInfoAndBooth> selloutExhibitorInfoAndBoothListTemp = new ArrayList<SelloutExhibitorInfoAndBooth>();
                        selloutExhibitorInfoAndBoothListTemp = converExhibitorInfoAndBoothList(tExhibitor, boothNumberValue);
                        selloutExhibitorInfoAndBoothList.addAll(selloutExhibitorInfoAndBoothListTemp);
                    }else if(boothNumberValue.indexOf(",") > 0){
                        String[] boothArray = boothNumberValue.split(",");
                        for(String booth: boothArray){
                            TExhibitorInfo tExhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(tExhibitor.getEid());
                            SelloutExhibitorInfoAndBooth selloutExhibitorInfoAndBooth = new SelloutExhibitorInfoAndBooth(booth.trim(),
                                    ((tExhibitor.getCountry() == null || (tExhibitor.getCountry() != null && tExhibitor.getCountry() == 44))?tExhibitorInfo.getCompany():tExhibitorInfo.getCompanyEn()));
                            selloutExhibitorInfoAndBoothList.add(selloutExhibitorInfoAndBooth);
                        }
                    }else{
                        TExhibitorInfo tExhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(tExhibitor.getEid());
                        SelloutExhibitorInfoAndBooth selloutExhibitorInfoAndBooth = new SelloutExhibitorInfoAndBooth(boothNumberValue,
                                ((tExhibitor.getCountry() == null || (tExhibitor.getCountry() != null && tExhibitor.getCountry() == 44))?tExhibitorInfo.getCompany():tExhibitorInfo.getCompanyEn()));
                        selloutExhibitorInfoAndBoothList.add(selloutExhibitorInfoAndBooth);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selloutExhibitorInfoAndBoothList;
    }

    private List<SelloutExhibitorInfoAndBooth> converExhibitorInfoAndBoothList(TExhibitor tExhibitor, String boothNum) {
        List<SelloutExhibitorInfoAndBooth> converExhibitorInfoAndBoothList = new ArrayList<SelloutExhibitorInfoAndBooth>();
        String[] boothArray = boothNum.split("-");
        if(boothArray.length>1){
            String firstStr = boothArray[0];
            String lastStr = boothArray[1];
            String commomStr = "";
            String firstNum = "";
            String lastNum = "";
            char[] firstArray = firstStr.toCharArray();
            for (int i = firstArray.length-1; i>=0; i--) {
                if (firstArray[i] >= 'a' && firstArray[i] <= 'z' || firstArray[i] >= 'A' && firstArray[i] <= 'Z') {
                    commomStr = firstStr.substring(0,i+1);
                    firstNum = firstStr.substring(i+1, firstArray.length);
                    break;
                }
            }

            char[] lastArray = lastStr.toCharArray();
            for (int i = lastArray.length-1; i>=0; i--) {
                if (lastArray[i] >= 'a' && lastArray[i] <= 'z' || lastArray[i] >= 'A' && lastArray[i] <= 'Z') {
                    lastNum = lastStr.substring(i+1, lastArray.length);
                    break;
                }
            }


            if(StringUtil.isNotEmpty(firstNum) && StringUtil.isNotEmpty(lastNum)){
                int first = Integer.parseInt(firstNum);
                int last = Integer.parseInt(lastNum);
                TExhibitorInfo tExhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(tExhibitor.getEid());
                for(int k=first;k<=last;k++){
                    StringBuffer sb = new StringBuffer();
                    sb.append(commomStr + k);
                    if(sb.toString().length() != firstStr.length()){
                        sb = new StringBuffer();
                        sb.append(commomStr + "0" + k);
                    }
                    SelloutExhibitorInfoAndBooth selloutExhibitorInfoAndBooth = new SelloutExhibitorInfoAndBooth(sb.toString(),
                            ((tExhibitor.getCountry() == null || (tExhibitor.getCountry() != null && tExhibitor.getCountry() == 44))?tExhibitorInfo.getCompany():tExhibitorInfo.getCompanyEn()));
                    converExhibitorInfoAndBoothList.add(selloutExhibitorInfoAndBooth);
                }
            }
        }
        return converExhibitorInfoAndBoothList;
    }

    /**
     * 根据展位号取消预定的展位信息
     * @param boothNum
     */
    @ResponseBody
    @RequestMapping(value = "cancelReserveExhibitorInfoAndBoothNum", method = RequestMethod.POST)
    public BaseResponse cancelReserveExhibitorInfoAndBoothNum(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle,
                                                              @RequestParam("boothNum") String boothNum) {
        BaseResponse response = new BaseResponse();
        try {
            List<TXicecMapIntetion> tXicecMapIntetionList = xicecMapDao.queryByHql("from TXicecMapIntetion where booth_num=?", new Object[]{boothNum});
            for(TXicecMapIntetion tXicecMapIntetion:tXicecMapIntetionList){
                xicecMapDao.delete(tXicecMapIntetion);
            }
            response.setResultCode(0);
        }catch (Exception e) {
            response.setResultCode(1);
        }
        return response;
    }

    /**
     * 根据展位号添加预定的展位信息
     * @param boothNum
     */
    @ResponseBody
    @RequestMapping(value = "addReserveExhibitorInfoAndBoothNum")
    public ReserverExhibitorInfoAndBooth addReserveExhibitorInfoAndBoothNum(@ModelAttribute(ManagerPrinciple.MANAGERPRINCIPLE) ManagerPrinciple principle,
                                                           @RequestParam("boothNum") String boothNum) {
        ReserverExhibitorInfoAndBooth reserverExhibitorInfoAndBooth = new ReserverExhibitorInfoAndBooth();
        try {
            TXicecMapIntetion tXicecMapIntetion = new TXicecMapIntetion();
            tXicecMapIntetion.setBooth_num(boothNum);
            tXicecMapIntetion.setTag(principle.getAdmin().getId());
            xicecMapDao.create(tXicecMapIntetion);
            reserverExhibitorInfoAndBooth.setBoothNum(boothNum);
            reserverExhibitorInfoAndBooth.setTagName(principle.getAdmin().getName());
        }catch (Exception e) {
        }
        return reserverExhibitorInfoAndBooth;
    }

    /**
     * 分页查询备份展商数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAllExhibitorBackupInfosByPage")
    public QueryExhibitorResponse queryAllExhibitorBackupInfosByPage(@ModelAttribute QueryExhibitorBackupRequest request) {
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        try {
            response = exhibitorManagerService.queryAllExhibitorBackupInfosByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query exhibitor backup info error.", e);
        }
        return response;
    }

    @RequestMapping(value = "directorExhibitorBackupInfo")
    public ModelAndView directorExhibitorBackupInfo(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/databackup/exhibitorBackupDetailInfo");
        TExhibitorBackupInfo tExhibitorBackupInfo = exhibitorManagerService.loadExhibitorBackupInfoById(id);
        modelAndView.addObject("id", tExhibitorBackupInfo.getId());
        modelAndView.addObject("tExhibitorBackupInfo", tExhibitorBackupInfo);
        /*modelAndView.addObject("term", exhibitorManagerService.getExhibitorTermByEid(tExhibitorBackupInfo.getEid()));
        modelAndView.addObject("booth", exhibitorManagerService.queryBoothByEid(tExhibitorBackupInfo.getEid()));
        modelAndView.addObject("currentTerm", exhibitorManagerService.queryCurrentTermNumber());
        modelAndView.addObject("exhibitorInfo", exhibitorManagerService.loadExhibitorInfoByEid(tExhibitorBackupInfo.getEid()));
        modelAndView.addObject("invoice", invoiceService.getByEid(tExhibitorBackupInfo.getEid()));*/
        return modelAndView;
    }

    /**
     * 显示展商备份Logo
     * @param response
     * @param id
     */
    @RequestMapping(value = "showExhibitorBackupInvoiceImage", method = RequestMethod.GET)
    public void showExhibitorBackupInvoiceImage(HttpServletResponse response, @RequestParam("id") Integer id) {
        try {
            TExhibitorBackupInfo tExhibitorBackupInfo = exhibitorManagerService.loadExhibitorBackupInfoById(id);
            if(tExhibitorBackupInfo != null){
                String logoFileName = tExhibitorBackupInfo.getExhibitor_invoice_apply_invoice_image_address();
                if (StringUtils.isNotEmpty(logoFileName)) {
                    OutputStream outputStream = response.getOutputStream();
                    File logo = new File(logoFileName);
                    if (!logo.exists()) {
                        return;
                    }
                    FileUtils.copyFile(new File(logoFileName), outputStream);
                    outputStream.close();
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            log.error("query exhibitor backup invoice image error.", e);
            e.printStackTrace();
        }
    }

    /**
     * 显示备份展商Logo
     * @param response
     * @param id
     */
    @RequestMapping(value = "showExhibitorBackupLogo", method = RequestMethod.GET)
    public void showExhibitorBackupLogo(HttpServletResponse response, @RequestParam("id") Integer id) {
        try {
            TExhibitorBackupInfo tExhibitorBackupInfo = exhibitorManagerService.loadExhibitorBackupInfoById(id);
            if(tExhibitorBackupInfo != null){
                String logoFileName = tExhibitorBackupInfo.getExhibitor_info_logo();
                if (StringUtils.isNotEmpty(logoFileName)) {
                    File logo = new File(logoFileName);
                    if (!logo.exists()) return;
                    OutputStream outputStream = response.getOutputStream();
                    FileUtils.copyFile(logo, outputStream);
                    outputStream.close();
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 备份展商数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "backupExhibitorData", method = RequestMethod.POST)
    public BaseResponse backupExhibitorData() {
        BaseResponse response = new BaseResponse();
        try {
            exhibitorManagerService.backupExhibitorData();
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("backup exhibitor info error.", e);
        }
        return response;
    }

    /**
     * 根据id查询展商备份对应产品列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorBackupProductList", method = RequestMethod.POST)
    public QueryExhibitorResponse queryExhibitorBackupProductList(@ModelAttribute QueryProductOrContactOrJoinerRequest request) {
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TProductBackupInfo> tProductBackupInfoList = exhibitorManagerService.loadProductBackupInfoById(request.getId());
            response.setRows(tProductBackupInfoList);
            response.setResultCode(0);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response = new QueryExhibitorResponse();
            log.error("query exhibitor backup product list error.",e);
        }
        return response;
    }

    /**
     * 根据id查询展商备份对应联系人列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorBackupContactList", method = RequestMethod.POST)
    public QueryExhibitorResponse queryExhibitorBackupContactList(@ModelAttribute QueryProductOrContactOrJoinerRequest request) {
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TContactBackupInfo> tContactBackupInfoList = exhibitorManagerService.loadContactBackupInfoById(request.getId());
            response.setRows(tContactBackupInfoList);
            response.setResultCode(0);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response = new QueryExhibitorResponse();
            log.error("query exhibitor backup contact list error.", e);
        }
        return response;
    }

    /**
     * 根据id查询展商备份对应参展人员列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorBackupJoinerList", method = RequestMethod.POST)
    public QueryExhibitorResponse queryExhibitorBackupJoinerList(@ModelAttribute QueryProductOrContactOrJoinerRequest request) {
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TExhibitorJoinerBackupInfo> tExhibitorJoinerBackupInfoList = exhibitorManagerService.loadExhibitorJoinerBackupInfoById(request.getId());
            response.setRows(tExhibitorJoinerBackupInfoList);
            response.setResultCode(0);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response = new QueryExhibitorResponse();
            log.error("query exhibitor backup joiner list error.", e);
        }
        return response;
    }

    /**
     * 根据id查询展商备份对应VISA列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorBackupVisaList", method = RequestMethod.POST)
    public QueryExhibitorResponse queryExhibitorBackupVisaList(@ModelAttribute QueryProductOrContactOrJoinerRequest request) {
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        try {
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            List<TVisaBackupInfo> tVisaBackupInfoList = exhibitorManagerService.loadExhibitorVisaBackupInfoByExhibitorInfoBackupId(request.getId());
            response.setRows(tVisaBackupInfoList);
            response.setResultCode(0);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response = new QueryExhibitorResponse();
            log.error("query exhibitor backup visa list error.", e);
        }
        return response;
    }

    /**
     * 显示护照
     * @param response
     * @param vid
     */
    @RequestMapping(value = "showVisaBackupPassportPage", method = RequestMethod.GET)
    public void showVisaBackupPassportPage(HttpServletResponse response, @RequestParam("vid") Integer vid) {
        try {
            TVisaBackupInfo tVisaBackupInfo = exhibitorManagerService.loadExhibitorVisaBackupInfoById(vid);
            if(tVisaBackupInfo != null){
                String logoFileName = tVisaBackupInfo.getPassportPage();
                if (StringUtils.isNotEmpty(logoFileName)) {
                    if(logoFileName.toLowerCase().contains(".pdf")) response.setContentType("application/pdf");
                    File logo = new File(logoFileName);
                    if (!logo.exists()) return;
                    OutputStream outputStream = response.getOutputStream();
                    FileUtils.copyFile(logo, outputStream);
                    outputStream.close();
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示营业执照
     * @param response
     * @param vid
     */
    @RequestMapping(value = "showVisaBackupBusinessPage", method = RequestMethod.GET)
    public void showVisaBackupBusinessPage(HttpServletResponse response, @RequestParam("vid") Integer vid) {
        try {
            TVisaBackupInfo tVisaBackupInfo = exhibitorManagerService.loadExhibitorVisaBackupInfoById(vid);
            if(tVisaBackupInfo != null){
                String logoFileName = tVisaBackupInfo.getBusinessLicense();
                if (StringUtils.isNotEmpty(logoFileName)) {
                    if(logoFileName.toLowerCase().contains(".pdf")) response.setContentType("application/pdf");
                    File logo = new File(logoFileName);
                    if (!logo.exists()) return;
                    OutputStream outputStream = response.getOutputStream();
                    FileUtils.copyFile(logo, outputStream);
                    outputStream.close();
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ID获取具体的VISA信息
     * @param id
     * @return
     */
    @RequestMapping(value = "tVisaBackupDetailInfo")
    public ModelAndView directToTVisaBackupDetailInfo(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/visa/tvisaInfo");
        modelAndView.addObject("id", id);
        TVisaBackupInfo tVisaBackupInfo = exhibitorManagerService.loadExhibitorVisaBackupInfoById(id);
        modelAndView.addObject("visaInfo", tVisaBackupInfo);
        if(tVisaBackupInfo != null){
            TExhibitorJoinerBackupInfo tExhibitorJoinerBackupInfo = exhibitorManagerService.loadExhibitorJoinerBackupInfoByJoinerId(tVisaBackupInfo.getJoinerId());
            modelAndView.addObject("joinerInfo", tExhibitorJoinerBackupInfo);
        }
        return modelAndView;
    }
}