package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dao.ExhibitorGroupDao;
import com.zhenhappy.ems.dao.ExhibitorInfoDao;
import com.zhenhappy.ems.dto.ProductType;
import com.zhenhappy.ems.dto.ProductTypeCheck;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.entity.TExhibitorBooth;
import com.zhenhappy.ems.manager.entity.TVisitor_Info_Survey;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBack;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorGroupInfo;
import com.zhenhappy.ems.manager.service.*;
import com.zhenhappy.ems.manager.sys.Constants;
import com.zhenhappy.ems.manager.util.CreateZip;
import com.zhenhappy.ems.manager.util.JXLExcelView;
import com.zhenhappy.ems.manager.util.ZipCompressor;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.system.SystemConfig;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by wujianbin on 2014-08-26.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class ImportExportAction extends BaseAction {

	private static Logger log = Logger.getLogger(ImportExportAction.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
    @Autowired
    private ExhibitorManagerService exhibitorManagerService;
	@Autowired
	private ExhibitorService exhibitorService;
	@Autowired
	private CustomerInfoManagerService customerInfoManagerService;
	@Autowired
	private WVisaManagerService wVisaManagerService;
	@Autowired
	private TVisaManagerService tVisaManagerService;
    @Autowired
    private ImportExportService importExportService;
    @Autowired
	private FreeMarkerConfigurer freeMarker;// 注入FreeMarker模版封装框架
    @Autowired
    private SystemConfig systemConfig;
	@Autowired
	private ExhibitorInfoDao exhibitorInfoDao;
	@Autowired
	private ContactManagerService contactManagerService;
	@Autowired
	private ExhibitorGroupDao exhibitorGroupDao;

	/**
	 * 导入展商账号
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="upload/exhibitors", method={RequestMethod.POST,RequestMethod.GET})
	public List<String> importExhibitors(@RequestParam MultipartFile file,
										 @ModelAttribute ImportExhibitorsRequest request) throws IOException {
		File importFile = upload(file, "\\import", FilenameUtils.getBaseName(file.getOriginalFilename()) + new Date().getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
		List<String> report = importExportService.importExhibitor(importFile, request);
//        FileUtils.deleteQuietly(importFile); // 删除临时文件
		return report;
	}

	/**
	 * 根据展团导出信息
	 * @param groupid
	 * @return
	 */
	@RequestMapping("/exportExhibitorGroupToZip")
	public ModelAndView exportExhibitorGroupToZip(@RequestParam(value = "groupid", defaultValue = "") Integer[] groupid,
												  HttpServletRequest request, HttpServletResponse response) throws Exception {
		String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
		String randomFile = UUID.randomUUID().toString();
		String destDir = appendix_directory + "\\tmp\\" + randomFile;
		FileUtils.forceMkdir(new File(destDir)); // 创建临时文件夹
		if(groupid[0] == -1){
			exportExhibitorGroupTransaction(null, destDir);
		}else{
			exportExhibitorGroupTransaction(groupid, destDir);
		}
		CreateZip.zipGroupToFile(destDir, randomFile);
		return download(destDir, randomFile, request, response);
	}

    /**
     * 导出展商列表到Excel
     * @param eids
     * @return
     */
    @RequestMapping(value = "exportExhibitorsToExcel", method = RequestMethod.POST)
    public ModelAndView exportExhibitorsToExcel(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
        Map model = new HashMap();
        List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
        if(eids[0] == -1) exhibitors = exhibitorManagerService.loadAllExhibitors();
        else exhibitors = exhibitorManagerService.loadSelectedExhibitors(eids);
        List<QueryExhibitorInfo> queryExhibitorInfos = importExportService.exportExhibitor(exhibitors);
        model.put("list", queryExhibitorInfos);
        String[] titles = new String[] { "展位号", "展区", "展位面积", "公司中文名", "公司英文名", "电话", "传真", "邮箱", "网址", "中文地址", "英文地址", "邮编", "产品分类", "主营产品(中文)", "主营产品(英文)", "公司简介", "公司亮点", "发票抬头", "公司纳税人识别号" };
        model.put("titles", titles);
        String[] columns = new String[] { "boothNumber", "area", "exhibitor_area", "company", "companyEn", "phone", "fax", "email", "website", "address", "addressEn", "zipcode", "productType", "mainProduct", "mainProductEn", "mark", "company_lighnts", "invoiceTitle", "invoiceNo" };
        model.put("columns", columns);
        Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,40,20,20};
        model.put("columnWidths", columnWidths);
        model.put("fileName", "展商基本信息.xls");
        model.put("sheetName", "展商基本信息");
        return new ModelAndView(new JXLExcelView(), model);
    }

	/**
	 * 导出国内客商列表到Excel
	 * @param cids
	 * @return
	 */
	@RequestMapping(value = "exportInlandCustomersToExcel", method = RequestMethod.POST)
	public ModelAndView exportInlandCustomersToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids) {
		Map model = new HashMap();
		List<WCustomer> customers = new ArrayList<WCustomer>();
		if(cids[0] == -1)
			customers = customerInfoManagerService.loadAllInlandCustomer();
		else
			customers = customerInfoManagerService.loadSelectedCustomers(cids);
		List<ExportCustomerInfo> exportCustomer = importExportService.exportCustomer(customers, 1);
		model.put("list", exportCustomer);
		String[] titles = new String[] { "预约登记号", "公司", "地址", "联系人", "职位", "性别", "手机", "邮箱", "座机", "传真", "网址", "登记时间","修改时间", "备注" };
		model.put("titles", titles);
		String[] columns = new String[] { "checkingNo", "company", "address", "name", "position", "sex","phone", "email", "tel", "faxString",  "website", "createdTime", "updateTime", "remark" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "客商基本信息.xls");
		model.put("sheetName", "客商基本信息");
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 导出国外客商列表到Excel
	 * @param cids
	 * @return
	 */
	@RequestMapping(value = "exportForeignCustomersToExcel", method = RequestMethod.POST)
	public ModelAndView exportForeignCustomersToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids) {
		Map model = new HashMap();
		List<WCustomer> customers = new ArrayList<WCustomer>();
		if(cids[0] == -1)
			customers = customerInfoManagerService.loadAllForeignCustomer();
		else
			customers = customerInfoManagerService.loadSelectedCustomers(cids);
		List<ExportCustomerInfo> exportCustomer = importExportService.exportCustomer(customers, 2);
		model.put("list", exportCustomer);
		//String[] titles = new String[] { "国家", "姓名", "邮箱" };
		//model.put("titles", titles);
		//String[] columns = new String[] { "countryString", "name", "email" };
		String[] titles = new String[] { "预约登记号", "国家", "公司中文名", "性别+姓名+职位", "地址", "邮箱", "网址", "城市", "手机", "电话", "传真", "登记时间","修改时间","备注" };
		model.put("titles", titles);
		String[] columns = new String[] { "checkingNo","countryString", "company", "name", "address", "email", "website", "city", "phone", "tel", "faxString", "createdTime", "updateTime", "remark" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "客商基本信息.xls");
		model.put("sheetName", "客商基本信息");
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 导出参观团列表到Excel
	 * @param cids
	 * @return
	 */
	@RequestMapping(value = "exportCustomerGroupToExcel", method = RequestMethod.POST)
	public ModelAndView exportCustomerGroupToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids) {
		Map model = new HashMap();
		List<TVisitorGroupInfo> visitorGroupInfoList = new ArrayList<TVisitorGroupInfo>();
		if(cids[0] == -1)
			visitorGroupInfoList = customerInfoManagerService.loadAllCustomerGroup();
		else
			visitorGroupInfoList = customerInfoManagerService.loadSelectedCustomerGruoup(cids);
		List<ExportCustomerGroupInfo> exportCustomer = importExportService.exportCustomerGroup(visitorGroupInfoList);
		model.put("list", exportCustomer);
		String[] titles = new String[] { "参观团名称", "是否团长", "预登记确认号", "姓名", "公司", "邮箱", "职位", "手机", "地址", "注册时间", "更新时间", "邮件数量", "短信数量" };
		model.put("titles", titles);
		String[] columns = new String[] { "groupName", "identify", "checkingNo", "memberName", "company", "email","position", "mobile", "address", "createTime",  "updateTime", "emailNum", "msgNum" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{40,10,20,20,40,40,20,30,40,40,40,10,10};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "参观团信息.xls");
		model.put("sheetName", "参观团信息");
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 根据eid查询展商基本信息
	 * @param eid
	 * @return
	 */
	@Transactional
	public TExhibitorInfo loadExhibitorInfoByEid(Integer eid) {
		if(eid != null){
			List<TExhibitorInfo> exhibitorInfo = exhibitorInfoDao.queryByHql("from TExhibitorInfo where eid=?", new Object[]{ eid });
			return exhibitorInfo.size() > 0 ? exhibitorInfo.get(0) : null;
		}else return null;
	}

    /**
	 * 导出展位信息到Excel
	 * @return
	 */
    @RequestMapping("/exportBoothInfoToExcel_2")
    public ModelAndView exportBoothInfoToExcel_2() {
        Map model = new HashMap();
        List<TExhibitor> exhibitors = exhibitorManagerService.loadAllExhibitors();
        List<QueryExhibitorInfoT> queryExhibitorInfos = new ArrayList<QueryExhibitorInfoT>();
        if(exhibitors != null){
            for(TExhibitor exhibitor:exhibitors){
				TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(exhibitor.getEid());
                String boothNum = exhibitorManagerService.loadBoothNum(exhibitor.getEid());
                String boothNums[] = boothNum.split(",");
                if(boothNums.length > 1) {
                    for(String booth:boothNums){
                        QueryExhibitorInfoT queryExhibitorInfoT = new QueryExhibitorInfoT();
                        queryExhibitorInfoT.setEid(exhibitor.getEid());
                        queryExhibitorInfoT.setBoothNumber(booth.trim());
                        /*if(StringUtils.isNotEmpty(exhibitor.getCompany()))
							queryExhibitorInfoT.setCompany(exhibitor.getCompany());
                        else
							queryExhibitorInfoT.setCompany(exhibitor.getCompanye());*/
						if(StringUtils.isNotEmpty(exhibitorInfo.getCompany()))
							queryExhibitorInfoT.setCompany(exhibitorInfo.getCompany());
						else
							queryExhibitorInfoT.setCompany(exhibitorInfo.getCompanyEn());
                        queryExhibitorInfos.add(queryExhibitorInfoT);
                    }
                }else if(boothNums.length == 1) {
                    QueryExhibitorInfoT queryExhibitorInfoT = new QueryExhibitorInfoT();
                    queryExhibitorInfoT.setEid(exhibitor.getEid());
                    queryExhibitorInfoT.setBoothNumber(boothNum.trim());
                    /*if(StringUtils.isNotEmpty(exhibitor.getCompany()))
						queryExhibitorInfoT.setCompany(exhibitor.getCompany());
                    else
						queryExhibitorInfoT.setCompany(exhibitor.getCompanye());*/
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompany()))
						queryExhibitorInfoT.setCompany(exhibitorInfo.getCompany());
					else
						queryExhibitorInfoT.setCompany(exhibitorInfo.getCompanyEn());
                    queryExhibitorInfos.add(queryExhibitorInfoT);
                }
            }
        }
        model.put("list", queryExhibitorInfos);
        String[] titles = new String[] { "ID", "展位号", "公司名" };
        model.put("titles", titles);
        String[] columns = new String[] { "eid", "boothNumber", "company" };
        model.put("columns", columns);
        Integer[] columnWidths = new Integer[]{20,20,20};
        model.put("columnWidths", columnWidths);
        model.put("fileName", "展位信息.xls");
        model.put("sheetName", "展位信息");
        return new ModelAndView(new JXLExcelView(), model);
    }

    /**
     * 导出展位信息到Excel
     * @return
     */
    @RequestMapping("/exportBoothInfoToExcel_1")
    public ModelAndView exportBoothInfoToExcel_1() {
        Map model = new HashMap();
        // 构造数据
        List<TExhibitor> exhibitors = exhibitorManagerService.loadAllExhibitors();
        List<QueryExhibitorInfoT> queryExhibitorInfos = new ArrayList<QueryExhibitorInfoT>();
        if(exhibitors != null){
            for(TExhibitor exhibitor:exhibitors){
				TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(exhibitor.getEid());
                String boothNum = exhibitorManagerService.loadBoothNum(exhibitor.getEid());
                String boothNums[] = boothNum.split(",");
                if(boothNums.length > 1) {
                    for(String booth:boothNums){
                        QueryExhibitorInfoT queryExhibitorInfoT = new QueryExhibitorInfoT();
                        queryExhibitorInfoT.setEid(exhibitor.getEid());
                        queryExhibitorInfoT.setBoothNumber(booth.trim());
                        /*if(StringUtils.isNotEmpty(exhibitor.getCompany()))
							queryExhibitorInfoT.setCompany(exhibitor.getCompany());
                        else
							queryExhibitorInfoT.setCompany(exhibitor.getCompanye());*/
						if(StringUtils.isNotEmpty(exhibitorInfo.getCompany()))
							queryExhibitorInfoT.setCompany(exhibitorInfo.getCompany());
						else
							queryExhibitorInfoT.setCompany(exhibitorInfo.getCompanyEn());
                        queryExhibitorInfos.add(queryExhibitorInfoT);
                    }
                }else if(boothNums.length == 1) {
                    QueryExhibitorInfoT queryExhibitorInfoT = new QueryExhibitorInfoT();
                    queryExhibitorInfoT.setEid(exhibitor.getEid());
                    queryExhibitorInfoT.setBoothNumber(boothNum.trim());
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompany()))
						queryExhibitorInfoT.setCompany(exhibitorInfo.getCompany());
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn()))
						queryExhibitorInfoT.setCompanye(exhibitorInfo.getCompanyEn());
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompanyT()))
						queryExhibitorInfoT.setCompanyt(exhibitorInfo.getCompanyT());
                    /*if(StringUtils.isNotEmpty(exhibitor.getCompany()))
						queryExhibitorInfoT.setCompany(exhibitor.getCompany());
                    if(StringUtils.isNotEmpty(exhibitor.getCompanye()))
						queryExhibitorInfoT.setCompanye(exhibitor.getCompanye());
                    if(StringUtils.isNotEmpty(exhibitor.getCompanyt()))
						queryExhibitorInfoT.setCompanyt(exhibitor.getCompanyt());*/
                    queryExhibitorInfos.add(queryExhibitorInfoT);
                }
            }
        }
        model.put("list", queryExhibitorInfos);
        String[] titles = new String[] { "ID", "展位号", "公司中文名", "公司繁体名", "公司英文名" };
        model.put("titles", titles);
        String[] columns = new String[] { "eid", "boothNumber", "company", "companyt", "companye" };
        model.put("columns", columns);
        Integer[] columnWidths = new Integer[]{20,20,20,20,20};
        model.put("columnWidths", columnWidths);
        model.put("fileName", "展位信息.xls");
        model.put("sheetName", "展位信息");
        return new ModelAndView(new JXLExcelView(), model);
    }

    /**
     * 导出展位号+企业楣牌到Excel
     * @param eids
     * @return
     */
    @RequestMapping(value = "exportBoothNumAndMeipaiToExcel", method = RequestMethod.POST)
	public ModelAndView exportBoothNumAndMeipaiToExcel(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
    	Map model = new HashMap();
        List<QueryBoothNumAndMeipai> boothNumAndMeipais = new ArrayList<QueryBoothNumAndMeipai>();
        if(eids[0] == -1)
			boothNumAndMeipais = exhibitorManagerService.loadBoothNumAndMeipai(null);
        else
			boothNumAndMeipais = exhibitorManagerService.loadBoothNumAndMeipai(eids);
        model.put("list", boothNumAndMeipais);
        String[] titles = new String[] { "所属人", "展位号", "公司中文名称", "公司英文名称", "合同编号", "企业楣牌中文", "企业楣牌英文", "展位面积" };
		model.put("titles", titles);
		String[] columns = new String[] { "tagName", "boothNumber", "companyZh", "companyEn", "contractID", "meipai", "meipaiEn", "areaNum" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "展位号+企业楣牌.xls");
		model.put("sheetName", "展位号+企业楣牌");
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 导出展商参展人员列表到Excel
	 * @param eids
	 * @return
	 */
	@RequestMapping(value = "exportExhibitorJoinersToExcel", method = RequestMethod.POST)
	public ModelAndView exportExhibitorJoinersToExcel(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
		Map model = new HashMap();
		List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
		if(eids[0] == -1)
			exhibitors = exhibitorManagerService.loadAllExhibitors();
		else
			exhibitors = exhibitorManagerService.loadSelectedExhibitors(eids);

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
	 * 导出非展团境外参展人员列表到Excel
	 * @param eids
	 * @return
	 */
	@RequestMapping(value = "exportOutOfGroupExhibitorJoinersToExcel", method = RequestMethod.POST)
	public ModelAndView exportOutOfGroupExhibitorJoinersToExcel(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
		Map model = new HashMap();
		List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
		if(eids[0] == -1)
			exhibitors = exhibitorManagerService.loadAllOutlandExhibitorsOutOfGroup();
		else
			exhibitors = exhibitorManagerService.loadSelectedOutlandExhibitorsOutOfGroup(eids);

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
	 * 导出展商邀请涵邮件到Excel
	 * @param eids
	 * @return
	 */
	@RequestMapping(value = "exportAllExhibitorInvitationsToExcel", method = RequestMethod.POST)
	public ModelAndView exportAllExhibitorInvitationsToExcel(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
		Map model = new HashMap();
		List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
		if(eids[0] == -1) exhibitors = exhibitorManagerService.loadAllExhibitors();
		else exhibitors = exhibitorManagerService.loadSelectedExhibitors(eids);

		List<Map<String, Object>> exportExhibitorInvitations = jdbcTemplate.queryForList("select * from [t_email_send_detail]");
		model.put("list", exportExhibitorInvitations);
		String[] titles = new String[] { "Email" };
		model.put("titles", titles);
		String[] columns = new String[] { "address" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{20};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "展商邀请涵邮件.xls");
		model.put("sheetName", "展商邀请涵邮件");
		return new ModelAndView(new JXLExcelView(), model);
	}

    /**
     * 导出展商联系人列表到Excel
     * @param eids
     * @return
     */
    @RequestMapping(value = "exportContactsToExcel", method = RequestMethod.POST)
    public ModelAndView exportContactsToExcel(@RequestParam(value = "eids", defaultValue = "") Integer[] eids) {
        Map model = new HashMap();
        List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
        if(eids[0] == -1) exhibitors = exhibitorManagerService.loadAllExhibitors();
        else exhibitors = exhibitorManagerService.loadSelectedExhibitors(eids);

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
	 * 导出展商VISA信息到Excel
	 * @param vids
	 * @return
	 */
	@RequestMapping(value = "exportTVisasToExcel", method = RequestMethod.POST)
	public ModelAndView exportTVisasToExcel(@RequestParam(value = "vids", defaultValue = "") Integer[] vids) {
		Map model = new HashMap();
		List<TVisa> tVisas = new ArrayList<TVisa>();
		if(vids[0] == -1)
			tVisas = tVisaManagerService.loadAllTVisas();
		else
			tVisas = tVisaManagerService.loadSelectedTVisas(vids);
		List<ExportTVisa> queryExportTVisas = importExportService.exportTVisas(tVisas);
		model.put("list", queryExportTVisas);
		String[] titles = new String[] { "邮寄情况", "发送状态", "展商/客商", "商务局提交系列号", "Passport Name", "Passport No", "出生日期", "国籍", "称呼", "职务", "公司", "地址", "申请地", "停留时间开始", "停留时间结束", "Email",  "网址", "电话", "传真", "要不要邮寄", "快递公司", "快递单号", "Exp date有效期", "提交日期", "Hotel" };
		model.put("titles", titles);
		String[] columns = new String[] { "emailDetail", "sendStatus", "exhibitor", "commericalSeriesNo", "passportName", "passportNo", "birth", "nationality", "gender", "jobTitle", "companyName", "address", "applyFor", "from", "to", "emailInfo", "companyWebsite", "tel", "fax", "needPost", "expressTp", "expressNo", "expDate", "createTime","hotelAddress"};
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{ 20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20 };
		model.put("columnWidths", columnWidths);
		model.put("fileName", "VISA.xls");
		model.put("sheetName", "VISA");
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 导出客商VISA信息到Excel
	 * @param vids
	 * @return
	 */
	@RequestMapping(value = "exportWVisasToExcel", method = RequestMethod.POST)
	public ModelAndView exportWVisasToExcel(@RequestParam(value = "vids", defaultValue = "") Integer[] vids) {
		Map model = new HashMap();
		List<WVisa> wVisas = new ArrayList<WVisa>();
		if(vids[0] == -1)
			wVisas = wVisaManagerService.loadAllWVisas();
		else
			wVisas = wVisaManagerService.loadSelectedWVisas(vids);
		List<ExportWVisa> queryExportWVisas = importExportService.exportWVisas(wVisas);
		model.put("list", queryExportWVisas);
		String[] titles = new String[] { "邮寄情况", "发送状态", "展商/客商", "商务局提交系列号","Passport Name", "Passport No", "出生日期", "国籍", "称呼", "职务", "公司", "地址", "申请地", "停留时间开始", "停留时间结束", "Email", "网址", "电话", "传真", "要不要邮寄", "快递公司", "快递单号", "Exp date有效期", "提交日期","Hotel" };
		model.put("titles", titles);
		String[] columns = new String[] { "emailDetail", "sendStatus", "customer", "commericalSeriesNo", "passportName", "passportNo", "dateOfBirth", "nationality", "gender", "position", "company", "address", "chineseEmbassy", "durationBeginTime", "durationEndTime", "email", "website", "telephone", "fax", "needPost", "expressTp", "expressNo", "expDate", "createTime","hotelAddress" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{ 20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20 };
		model.put("columnWidths", columnWidths);
		model.put("fileName", "VISA.xls");
		model.put("sheetName", "VISA");
		return new ModelAndView(new JXLExcelView(), model);
	}

    /**
     * 导出会刊
     * @param eids
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportTransactionsToZip")
    public ModelAndView exportTransactionsToZip(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) throws Exception {
//    	String dirPath = "D:\\Users\\Foshi\\tmp\\" + UUID.randomUUID();
		//Users/Kristine-Mac/Documents/Workspaces/appendix/stone/manager\tmp\
    	String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
        String randomFile = UUID.randomUUID().toString();
    	String destDir = appendix_directory + "\\tmp\\" + randomFile;
    	FileUtils.forceMkdir(new File(destDir)); // 创建临时文件夹
        if(eids[0] == -1){
			exportExhibitorListTransaction(null, destDir);
            importExportService.copyLogo(null, destDir);
        }else{
			exportExhibitorListTransaction(eids, destDir);
            importExportService.copyLogo(eids, destDir);
        }
	    CreateZip.zipToFile(destDir, randomFile);
    	return download(destDir, randomFile, request, response);
    }

	public String loadBoothNum(Integer eid) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from t_exhibitor_booth where eid=?", eid);
		if (results.size() > 0) {
			return (String) results.get(0).get("booth_number");
		} else {
			return "";
		}
	}

	//导出展团信息
	private void exportExhibitorGroupTransaction(Integer[] groupIds, String dirPath) throws Exception {
		List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
		if(groupIds == null){
			List<TExhibitorGroup> groups = exhibitorGroupDao.queryByHql("from TExhibitorGroup", new Object[]{});
			for (TExhibitorGroup tExhibitorGroup:groups){
				exhibitors = exhibitorManagerService.loadExhibitorByGroupId(tExhibitorGroup.getId());
				File fileTemp = new File(dirPath + "\\" + tExhibitorGroup.getGroupName());
				if(!fileTemp.exists()  && !fileTemp.isDirectory()) {
					fileTemp.mkdir();
				}
				exportTransactions(exhibitors, dirPath + "\\" + tExhibitorGroup.getGroupName());
				copyExhibitorGroupLogo(exhibitors, dirPath + "\\" + tExhibitorGroup.getGroupName());
			}
		}else{
			for (Integer groupId:groupIds){
				TExhibitorGroup tExhibitorGroup = exhibitorGroupDao.query(groupId);
				exhibitors = exhibitorManagerService.loadExhibitorByGroupId(groupId);
				File fileTemp = new File(dirPath + "\\" + tExhibitorGroup.getGroupName());
				if(!fileTemp.exists()  && !fileTemp.isDirectory()) {
					fileTemp.mkdir();
				}
				exportTransactions(exhibitors, dirPath + "\\" + tExhibitorGroup.getGroupName());
				copyExhibitorGroupLogo(exhibitors, dirPath + "\\" + tExhibitorGroup.getGroupName());
			}
		}
	}

	public void copyExhibitorGroupLogo(List<TExhibitor> exhibitors, String destDir) throws IOException {
		for(TExhibitor exhibitor:exhibitors){
			TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(exhibitor.getEid());
			if(exhibitorInfo != null){
				if(StringUtils.isNotEmpty(exhibitorInfo.getLogo())){
					String boothNumber = loadBoothNum(exhibitor.getEid());
					File srcFile = new File(exhibitorInfo.getLogo().replaceAll("\\\\\\\\", "\\\\").replaceAll("/", "\\\\"));
					if (srcFile.exists() == false) continue;
					File destFile = null;
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn())){
						destFile = new File(destDir + "\\" + exhibitorInfo.getCompanyEn().replaceAll("/", "") + boothNumber.replaceAll("/", "") + "." + FilenameUtils.getExtension(exhibitorInfo.getLogo().replaceAll("/", "\\\\\\\\")));
					}else{
						destFile = new File(destDir + "\\" + exhibitorInfo.getCompany().replaceAll("/", "") + boothNumber.replaceAll("/", "") + "." + FilenameUtils.getExtension(exhibitorInfo.getLogo().replaceAll("/", "\\\\\\\\")));
					}
					if(destFile != null) FileUtils.copyFile(srcFile, destFile);
				}
			}
		}
	}

	//导出会刊
	private void exportExhibitorListTransaction(Integer[] eids, String dirPath) throws Exception {
		List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
		if(eids == null){
			exhibitors = exhibitorManagerService.loadAllExhibitors();
		}else{
			exhibitors = exhibitorManagerService.loadSelectedExhibitors(eids);
		}
		exportTransactions(exhibitors, dirPath);
	}

    private void exportTransactions(List<TExhibitor> exhibitors, String dirPath) throws Exception {
    	if(exhibitors.size() > 0){
    		for(TExhibitor exhibitor:exhibitors){
        		TExhibitorInfo exhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(exhibitor.getEid());
        		String boothNumber = exhibitorManagerService.loadBoothNum(exhibitor.getEid());
        		Transaction transaction = new Transaction();
        		if(exhibitorInfo != null){
					/*if((StringUtils.isNotEmpty(exhibitor.getCompany()) || StringUtils.isNotEmpty(exhibitor.getCompanye())) && StringUtils.isNotEmpty(boothNumber)){*/
					if((StringUtils.isNotEmpty(exhibitorInfo.getCompany()) || StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn())) && StringUtils.isNotEmpty(boothNumber)){
						transaction.setBoothNumber(boothNumber.trim());
						if(StringUtils.isNotEmpty(exhibitorInfo.getCompany())) transaction.setCompany(exhibitorInfo.getCompany().trim());
						else transaction.setCompany(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn())) transaction.setCompanye(exhibitorInfo.getCompanyEn().trim());
						else transaction.setCompanye(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getAddress())) transaction.setAddress(exhibitorInfo.getAddress().trim());
						else transaction.setAddress(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getAddressEn())) transaction.setAddressEn(exhibitorInfo.getAddressEn().trim());
						else transaction.setAddressEn(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getPhone())) transaction.setPhone(exhibitorInfo.getPhone().trim());
						else transaction.setPhone(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getFax())) transaction.setFax(exhibitorInfo.getFax().trim());
						else transaction.setFax(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getWebsite())) transaction.setWebsite(exhibitorInfo.getWebsite().trim());
						else transaction.setWebsite(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getEmail())) transaction.setEmail(exhibitorInfo.getEmail().trim());
						else transaction.setEmail(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getMainProduct())) transaction.setMainProduct(exhibitorInfo.getMainProduct().trim());
						else transaction.setMainProduct(null);
						if(StringUtils.isNotEmpty(exhibitorInfo.getMainProductEn())) transaction.setMainProductEn(exhibitorInfo.getMainProductEn().trim());
						else transaction.setMainProductEn(null);
						List<String> conditions = new ArrayList<String>();
						List<String> conditionsEn = new ArrayList<String>();
						List<String> conditionsOther = new ArrayList<String>();
						List<ProductType> productTypes = exhibitorService.loadAllProductTypes();
						List<ProductTypeCheck> productTypeChecks = exhibitorService.loadAllProductTypesWithCheck(exhibitorInfo.getEinfoid());
						for(ProductTypeCheck productTypeCheck:productTypeChecks){
							for(ProductType productType:productTypes){
								if(productTypeCheck.getParentTypeId() != null){
									if(productType.getId().intValue() == productTypeCheck.getParentTypeId().intValue()){
										for(ProductType type:productType.getChildrenTypes()){
											if(type.getId().intValue() == productTypeCheck.getSubTypeId().intValue()){
												if(productTypeCheck.getIsOther() != null){
													if(productTypeCheck.getIsOther().intValue() == 1 && StringUtils.isNotEmpty(productTypeCheck.getOtherDescription())){
														conditionsOther.add(productTypeCheck.getOtherDescription());
													}
												}else{
													conditions.add(type.getTypeName());
													conditionsEn.add(type.getTypeNameEN());
												}
											}
										}
									}
								}
							}
						}
						String conditionsProduct = StringUtils.join(conditions, "，");
						String conditionsProductEn = StringUtils.join(conditionsEn, ", ");
						String conditionsProductOther = StringUtils.join(conditionsOther, ", ");
						if(StringUtils.isNotEmpty(conditionsProduct)) transaction.setProduct(conditionsProduct);
						else transaction.setProduct(null);
						if(StringUtils.isNotEmpty(conditionsProductEn)) transaction.setProductEn(conditionsProductEn);
						else transaction.setProductEn(null);
						if(StringUtils.isNotEmpty(conditionsProductOther)) transaction.setProductOther(conditionsProductOther);
						else transaction.setProductOther(null);
					}
					String filePath = "";
					if(exhibitorInfo.getCompanyEn() != null){
						if(StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn()))
							filePath = dirPath + "\\" + exhibitorInfo.getCompanyEn().replaceAll("/", "") + boothNumber.replaceAll("/", "") + ".txt";
						else
							filePath = dirPath + "\\" + exhibitorInfo.getCompany().replaceAll("/", "") + boothNumber.replaceAll("/", "") + ".txt";
						importExportService.WriteStringToFile(getTransactionText(transaction), filePath);
					}
        		}else if((StringUtils.isNotEmpty(exhibitorInfo.getCompany()) || StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn())) && StringUtils.isNotEmpty(boothNumber)){
            		transaction.setBoothNumber(boothNumber.trim());
            		ModifyExhibitorInfoRequest modifyExhibitorInfoRequest = new ModifyExhibitorInfoRequest();
            		if(StringUtils.isNotEmpty(exhibitorInfo.getCompany())) {
            			transaction.setCompany(exhibitorInfo.getCompany().trim());
						System.out.printf("eid=" + exhibitor.getEid() + ",einfoid=" + exhibitorInfo.getEinfoid() + "-->没有公司名也没有展位号");
						//modifyExhibitorInfoRequest.setCompany(exhibitor.getCompany().trim());
            		}
	        		else transaction.setCompany(null);
	        		if(StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn())) {
	        			transaction.setCompanye(exhibitorInfo.getCompanyEn().trim());
						System.out.printf("eid=" + exhibitor.getEid() + ",einfoid=" + exhibitorInfo.getEinfoid() + "-->没有公司名也没有展位号");
	        			//modifyExhibitorInfoRequest.setCompanyEn(exhibitor.getCompanye().trim());
	        		}
	        		else transaction.setCompanye(null);
            		transaction.setAddress(null);
            		transaction.setPhone(null);
            		transaction.setFax(null);
            		transaction.setWebsite(null);
            		transaction.setEmail(null);
            		transaction.setMainProduct(null);
					transaction.setProduct(null);
					System.out.printf("eid=" + exhibitor.getEid() + ",einfoid=" + exhibitorInfo.getEinfoid() + "-->没有公司名也没有展位号");
            		//exhibitorManagerService.modifyExhibitorInfo(modifyExhibitorInfoRequest, exhibitor.getEid(), 1);
    			}else{
    				continue;
    			}
//        		String filePath = "";
//				if(StringUtils.isNotEmpty(exhibitor.getCompanye())) filePath = dirPath + "\\" + exhibitor.getCompanye().replaceAll("/", "") + boothNumber.replaceAll("/", "") + ".txt";
//				else filePath = dirPath + "\\" + exhibitor.getCompany().replaceAll("/", "") + boothNumber.replaceAll("/", "") + ".txt";
//        		importExportService.WriteStringToFile(getTransactionText(transaction), filePath);
//	        		System.out.println("导出" + exhibitor.getCompany() + "成功");
        	}
    	}
//    	System.out.println("全部会刊信息导出完成");
    }

	private void exportInvoiceApplyForInscrease(Integer[] eids, String dirPath) throws Exception {
		List<TInvoiceApplyExtend> invoiceApplyExtendArrayList = new ArrayList<TInvoiceApplyExtend>();
		//查询开具增值发票对应的展商
		if(eids == null){
			invoiceApplyExtendArrayList = exhibitorManagerService.loadAllInvoiceApplyByInvoiceFlag();
		}else{
			invoiceApplyExtendArrayList = exhibitorManagerService.loadSelectedInvoiceApplyByInvoiceFlag(eids);
		}
		if(invoiceApplyExtendArrayList != null && invoiceApplyExtendArrayList.size() > 0){
			for(TInvoiceApplyExtend invoiceApplyExtend:invoiceApplyExtendArrayList){
				TExhibitorInfo exhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(invoiceApplyExtend.getEid());
				String boothNumber = exhibitorManagerService.loadBoothNum(invoiceApplyExtend.getEid());
				ExhibitorInvitationTransaction transaction = new ExhibitorInvitationTransaction();
				if(exhibitorInfo != null){
					String filePath = "";
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompany()) && exhibitorInfo.getCompany() != null){
						transaction.setBoothNumber(boothNumber.trim());
						if(StringUtils.isNotEmpty(exhibitorInfo.getCompany())) {
							transaction.setCompanyZh(exhibitorInfo.getCompany().trim());
							transaction.setInvoice_company(invoiceApplyExtend.getInvoice_company());
							transaction.setInvoice_taxpayer_no(invoiceApplyExtend.getInvoice_taxpayer_no());
							transaction.setInvoice_bank_name(invoiceApplyExtend.getInvoice_bank_name());
							transaction.setInvoice_bank_account(invoiceApplyExtend.getInvoice_bank_account());
							transaction.setInvoice_company_address(invoiceApplyExtend.getInvoice_company_address());
							transaction.setInvoice_company_tel(invoiceApplyExtend.getInvoice_company_tel());
							transaction.setInvoice_company_contact(invoiceApplyExtend.getInvoice_company_contact());
							transaction.setInvoice_general_taxpayer_flag(invoiceApplyExtend.getInvoice_general_taxpayer_flag());
							transaction.setInvoice_general_tax_type(invoiceApplyExtend.getInvoice_general_tax_type());
							String dirPathValue = "";
							if(StringUtils.isNotEmpty(exhibitorInfo.getCompany())){
								dirPathValue = dirPath + exhibitorInfo.getCompany();
							}else{
								dirPathValue = dirPath + exhibitorInfo.getCompanyEn();
							}
							//判断目录是否存在，如果不存在，就先创建
							File fileTemp = new File(dirPathValue);
							if(!fileTemp.exists()  && !fileTemp.isDirectory()) {
								fileTemp.mkdir();
							}
							filePath = dirPathValue + "\\" + boothNumber.replaceAll("/", "") + ".txt";
							/*if(StringUtils.isNotEmpty(exhibitorInfo.getCompany())){
								filePath = dirPath + "\\" + exhibitorInfo.getCompany().replaceAll("/", "") + boothNumber.replaceAll("/", "") + ".txt";
							}else{
								filePath = dirPath + "\\" + exhibitorInfo.getCompanyEn().replaceAll("/", "") + boothNumber.replaceAll("/", "") + ".txt";
							}*/
							if(new File(filePath).exists()) {
								new File(filePath).createNewFile();
							}
							importExportService.WriteStringToFile(getExhibitorInvitationTransactionText(transaction), filePath);
						}
						else {
							transaction.setCompanyZh("");
						}
					}
				}else{
					continue;
				}
			}
		}
	}

	/**
	 * 导出所有展商增值税专用发票
	 * @param eids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExhibitorInvitationToZipBackup")
	public ModelAndView exportExhibitorInvitationToZipBackup(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
													   HttpServletRequest request,
													   HttpServletResponse response) throws Exception {
		String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
		//String randomFile = UUID.randomUUID().toString();
		String randomFile = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\exhibitorInvoice.zip";
		String randomFile1 = "exhibitorInvoice";
		//String destDir = appendix_directory + "\\exhibitor_invisitor\\" + randomFile;
		String destDir = appendix_directory + "\\exhibitor_invisitor\\out\\";
		String downloadDir = appendix_directory + "\\exhibitor_invisitor\\out\\exhibitorInvoice.zip";
		FileUtils.deleteDirectory(new File(destDir)); 	//删除现有文件
		FileUtils.forceMkdir(new File(destDir)); 		//创建新文件夹
		if(eids[0] == -1){
			exportInvoiceApplyForInscrease(null, destDir);
			importExportService.copyExhibitorInvoiceApplyImage(null, destDir);
		}else{
			exportInvoiceApplyForInscrease(eids, destDir);
			importExportService.copyExhibitorInvoiceApplyImage(eids, destDir);
		}
		zipCompressor(eids, randomFile, destDir);
		//CreateZip.zipToFile(destDir, randomFile);
		//return download(destDir, randomFile1, request, response);
		return downloadInvoiceFile(downloadDir, destDir, request, response);
	}

	/**
	 * 导出所有展商增值税专用发票图片
	 * @param eids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExhibitorInvitationImageToZip")
	public ModelAndView exportExhibitorInvitationImageToZip(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
													   HttpServletRequest request,
													   HttpServletResponse response) throws Exception {
		String appendix_directory = systemConfig.getVal(Constants.appendix_directory).replaceAll("\\\\\\\\", "\\\\");
		String randomFile = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\exhibitorInvoice.zip";
		String destDir = appendix_directory + "\\exhibitor_invisitor\\out";
		String downloadDir = appendix_directory + "\\exhibitor_invisitor\\out\\exhibitorInvoice.zip";
		String randomFile1 = "exhibitorInvoice";

		//首先删除临时文件
		FileUtils.deleteDirectory(new File(destDir + "\\"));
		List<TInvoiceApplyExtend> invoiceApplyExtendArrayList = new ArrayList<TInvoiceApplyExtend>();
		if(eids[0] == -1){
			invoiceApplyExtendArrayList = exhibitorManagerService.loadAllInvoiceApplyByInvoiceFlag();
			importExportService.copyExhibitorInvoiceApplyImage(null, destDir);
		}else{
			invoiceApplyExtendArrayList = exhibitorManagerService.loadSelectedInvoiceApplyByInvoiceFlag(eids);
			importExportService.copyExhibitorInvoiceApplyImage(eids, destDir);
		}
		//zipCompressor(eids, randomFile, destDir);
		CreateZip.zipToFile(destDir, randomFile1);
		return download(destDir, randomFile1, request, response);
		/*Map model = new HashMap();
		List<ExportInvoiceApply> exportCustomer = importExportService.exportInvoiceApply(invoiceApplyExtendArrayList);
		model.put("list", exportCustomer);
		String[] titles = new String[] { "合同编号","开票名称","税号", "地址","联系人","电话", "开户行","账号", "服务名称", "数量", "金额", "税率", "备注" };
		model.put("titles", titles);
		String[] columns = new String[] { "contractId","invoiceName","invoiceNo", "address", "contact","telphone", "bank", "account", "serviceName", "num",  "moneyTotal", "tax", "remark" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{10,40,25,40,20,20,30,30,10,10,10,10,10};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "展商增值税专用发票.xls");
		model.put("sheetName", "展商增值税专用发票");*/

		//return downloadInvoiceFileEx(downloadDir, destDir, request, response, invoiceApplyExtendArrayList);
		//return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 导出所有展商增值税专用发票
	 * @param eids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExhibitorInvitationToZip")
	public ModelAndView exportExhibitorInvitationToZip(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
													   HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<TInvoiceApplyExtend> invoiceApplyExtendArrayList = new ArrayList<TInvoiceApplyExtend>();
		if(eids[0] == -1){
			invoiceApplyExtendArrayList = exhibitorManagerService.loadAllInvoiceApply();
		}else{
			invoiceApplyExtendArrayList = exhibitorManagerService.loadSelectedInvoiceApplyByOnlyInvoiceFlag(eids);
		}

		Map model = new HashMap();
		List<ExportInvoiceApply> exportCustomer = importExportService.exportInvoiceApply(invoiceApplyExtendArrayList);
		model.put("list", exportCustomer);
		String[] titles = new String[] { "合同编号","展位号", "发票类型", "开票名称","税号", "地址","联系人","电话", "开户行","账号", "服务名称", "数量", "金额", "税率", "备注" };
		model.put("titles", titles);
		String[] columns = new String[] { "contractId","boothNo", "invoiceType", "invoiceName","invoiceNo", "address", "contact","telphone", "bank", "account", "serviceName", "num",  "moneyTotal", "tax", "remark" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{10,10,20,40,25,40,20,20,30,30,10,10,10,10,10};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "展商发票信息.xls");
		model.put("sheetName", "展商发票信息");
		return new ModelAndView(new JXLExcelView(), model);
	}

	public ModelAndView downloadInvoiceFileEx(String randomFile, String destDir, HttpServletRequest request,
											  HttpServletResponse response, List<TInvoiceApplyExtend> invoiceApplyExtendArrayList) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String realName = URLEncoder.encode(randomFile, "UTF-8"); //设置下载文件名字
        /*
         * @see http://support.microsoft.com/default.aspx?kbid=816868
         */
		if (realName.length() > 150) {
			String guessCharset = "gb2312"; /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
			realName = new String(realName.getBytes(guessCharset), "ISO8859-1");
		}
		//String fileName = destDir + "\\" + zipName + ".zip";  //获取完整的文件名
		System.out.println(randomFile);
		long fileLength = new File(randomFile).length();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + realName);
		response.setHeader("Content-Length", String.valueOf(fileLength));
		bis = new BufferedInputStream(new FileInputStream(randomFile));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bos.flush();
		bis.close();
		bos.close();
		bos = null;
		//FileUtils.deleteDirectory(new File(randomFile)); // 删除临时文件
		FileUtils.deleteDirectory(new File(destDir));
		return null;
	}

	@RequestMapping("/download")
	public ModelAndView downloadInvoiceFile(String randomFile, String destDir, HttpServletRequest request, HttpServletResponse response) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String realName = URLEncoder.encode(randomFile, "UTF-8"); //设置下载文件名字
        /*
         * @see http://support.microsoft.com/default.aspx?kbid=816868
         */
		if (realName.length() > 150) {
			String guessCharset = "gb2312"; /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/
			realName = new String(realName.getBytes(guessCharset), "ISO8859-1");
		}
		//String fileName = destDir + "\\" + zipName + ".zip";  //获取完整的文件名
		System.out.println(randomFile);
		long fileLength = new File(randomFile).length();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + realName);
		response.setHeader("Content-Length", String.valueOf(fileLength));
		bis = new BufferedInputStream(new FileInputStream(randomFile));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bos.flush();
		bis.close();
		bos.close();
		bos = null;
		//FileUtils.deleteDirectory(new File(randomFile)); // 删除临时文件
		FileUtils.deleteDirectory(new File(destDir));
		return null;
	}

	private void zipCompressor(Integer[] eids, String dirPath, String filePath) throws Exception {
		List<TInvoiceApplyExtend> invoiceApplyExtendArrayList = new ArrayList<TInvoiceApplyExtend>();
		//查询开具增值发票对应的展商
		if(eids[0] == -1){
			invoiceApplyExtendArrayList = exhibitorManagerService.loadAllInvoiceApplyByInvoiceFlag();
		}else{
			invoiceApplyExtendArrayList = exhibitorManagerService.loadSelectedInvoiceApplyByInvoiceFlag(eids);
		}
		if(invoiceApplyExtendArrayList != null){
			int listSize = invoiceApplyExtendArrayList.size();
			String companyNameArray[] = new String[listSize];
			for(int i=0;i<listSize;i++){
				TInvoiceApplyExtend invoiceApplyExtend = invoiceApplyExtendArrayList.get(i);
				TExhibitorInfo exhibitorInfo = exhibitorManagerService.loadExhibitorInfoByEid(invoiceApplyExtend.getEid());
				if(exhibitorInfo != null){
					if(StringUtils.isNotEmpty(exhibitorInfo.getCompany())){
						companyNameArray[i] = exhibitorInfo.getCompany();
					}else{
						companyNameArray[i] = exhibitorInfo.getCompanyEn();
					}
				}else{
					continue;
				}
			}
			ZipCompressor.compressForManyDirectory(filePath, companyNameArray, dirPath);
		}
	}

    /**
	 * 通过模板构造邮件内容，参数expressNumber将替换模板文件中的${expressNumber}标签。
	 */
	private String getTransactionText(Transaction transaction) throws Exception {
		// 通过指定模板名获取FreeMarker模板实例
		Template template = freeMarker.getConfiguration().getTemplate("transaction/transaction-1.ftl");
		
		// FreeMarker通过Map传递动态数据
		Map<Object, Object> model = new HashMap<Object, Object>();
		model.put("transaction", transaction); // 注意动态数据的key和模板标签中指定的属性相匹配
		
		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		return htmlText;
	}

	/**
	 * 通过模板构造邮件内容，参数expressNumber将替换模板文件中的${expressNumber}标签。
	 */
	private String getExhibitorInvitationTransactionText(ExhibitorInvitationTransaction transaction) throws Exception {
		// 通过指定模板名获取FreeMarker模板实例
		Template template = freeMarker.getConfiguration().getTemplate("transaction/exhibitorInvoiceTransaction.ftl");

		// FreeMarker通过Map传递动态数据
		Map<Object, Object> model = new HashMap<Object, Object>();
		model.put("transaction", transaction); // 注意动态数据的key和模板标签中指定的属性相匹配

		// 解析模板并替换动态数据，最终content将替换模板文件中的${content}标签。
		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		return htmlText;
	}
	
    @RequestMapping("/download")
    public ModelAndView download(String destDir, String zipName, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	/*BufferedInputStream bis = null;
    	BufferedOutputStream bos = null;
    	String realName = URLEncoder.encode(zipName + ".zip", "UTF-8"); //设置下载文件名字
        *//*
         * @see http://support.microsoft.com/default.aspx?kbid=816868 
         *//*
        if (realName.length() > 150) {  
            String guessCharset = "gb2312"; *//*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*//*
            realName = new String(realName.getBytes(guessCharset), "ISO8859-1");   
        }  
    	String fileName = destDir + "\\" + zipName + ".zip";  //获取完整的文件名
    	long fileLength = new File(fileName).length();
		bos = new BufferedOutputStream(response.getOutputStream());
		response.reset();// 清空输出流
    	response.setContentType("application/x-msdownload");
    	response.setHeader("Content-Disposition", "attachment; filename=" + realName);
		//response.setContentLength((int) fileLength);
    	//response.setHeader("Content-Length", String.valueOf(fileLength));
    	bis = new BufferedInputStream(new FileInputStream(fileName));
    	byte[] buff = new byte[2048];
    	int bytesRead;
    	while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
    			bos.write(buff, 0, bytesRead);
    	}
		bos.flush();
		bos.close();
    	bis.close();
		bos = null;
		System.out.println("fileName: " + fileName + ", destDir: " + destDir);*/

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 客户使用保存文件的对话框：
			String fileName = destDir + "\\" + zipName + ".zip";
			String realName = URLEncoder.encode(zipName + ".zip", "UTF-8");
			response.setContentType("application/x-msdownload");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment; filename=" + realName);
			// 通知客户文件的MIME类型：
			bis = new BufferedInputStream(new FileInputStream(fileName));
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[10240];
			int bytesRead;
			int i = 0;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
				i++;
			}
			bos.flush();
		} catch (Exception e) {
			FileUtils.deleteDirectory(new File(destDir + "\\")); // 删除临时文件
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					FileUtils.deleteDirectory(new File(destDir + "\\")); // 删除临时文件
					e.printStackTrace();
				}
				bis = null;
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					FileUtils.deleteDirectory(new File(destDir + "\\")); // 删除临时文件
					e.printStackTrace();
				}
				bos = null;
			}
		}
		FileUtils.deleteDirectory(new File(destDir + "\\")); // 删除临时文件
		return null;
    }
    
    @RequestMapping("/upload")
    public File upload(@RequestParam MultipartFile file, String destDir, String fileName){
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

	/**
	 * 导出所有客商问卷调查到Excel
	 * @param cids
	 * @return
	 */
	@RequestMapping(value = "exportAllCustomerSurveyToExcel", method = RequestMethod.POST)
	public ModelAndView exportAllCustomerSurveyToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids) {
		Map model = new HashMap();
		List<TVisitor_Info_Survey> customers = customerInfoManagerService.loadAllCustomerSurvey();
		/*if(cids[0] == -1)
			customers = customerInfoManagerService.loadAllCustomerSurvey();
		else
			customers = customerInfoManagerService.loadSelectedCustomers(cids);*/
		List<ExportCustomerSurvey> exportCustomer = importExportService.exportCustomerSurvey(customers);
		model.put("list", exportCustomer);
		String[] titles = new String[] { "ID","客商ID","姓名", "公司","手机","邮箱", "WSC", "问卷1", "问卷2", "问卷3", "问卷4","问卷5","问卷6","问卷7","问卷8", "问卷9","问卷10", "备注1", "备注2","邀请邮件","邀请人","邮箱标题", "CreatedIP","CreatedTime","UpdatedIP","UpdateTime","IsDisabled" };
		model.put("titles", titles);
		String[] columns = new String[] { "id","wCustomerInfoID","customerName", "company", "telphone", "email", "wsc",  "q1", "q2", "q3", "q4","q5","q6","q7","q8", "q9","q10", "remark1", "remark2","inviterEmail","inviterName","emailSubject", "createdIP","createdTime","updatedIP","updateTime","isDisabled" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{6,6,20,30,15,30,60,30,30,30,30,30,30,30,30,30,30,30,30,20,20,20,20,20,20,20,20};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "客商问卷调查数据.xls");
		model.put("sheetName", "客商问卷调查数据");
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 根据年份和时间导出客商列表到Excel
	 * @param fieldYear
	 * @param fieldTime
	 * @param inlandOrForeign
	 * @return
	 */
	@RequestMapping(value = "exportCustomersByYearOrTimeToExcel", method = RequestMethod.POST)
	public ModelAndView exportCustomersByYearOrTimeToExcel(@ModelAttribute QueryCustomerRequest request,
														   @RequestParam(value = "fieldYear") String fieldYear,
														   @RequestParam(value = "fieldTime") String fieldTime,
														   @RequestParam(value = "inlandOrForeign") Integer inlandOrForeign) {
		Map model = new HashMap();
		List<WCustomer> customers = new ArrayList<WCustomer>();
		customers = customerInfoManagerService.loadCustomerByYearOrTime(request,fieldYear, fieldTime, inlandOrForeign);
		List<ExportCustomerInfo> exportCustomer = importExportService.exportCustomer(customers, inlandOrForeign);
		if(inlandOrForeign == 1){
			model.put("list", exportCustomer);
			String[] titles = new String[] { "预约登记号", "公司", "地址", "联系人", "职位", "性别", "手机", "邮箱", "座机", "传真", "网址", "登记时间","修改时间", "备注" };
			model.put("titles", titles);
			String[] columns = new String[] { "checkingNo", "company", "address", "name", "position", "sex","phone", "email", "tel", "faxString",  "website", "createdTime", "updateTime", "remark" };
			model.put("columns", columns);
			Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20};
			model.put("columnWidths", columnWidths);
			model.put("fileName", "国内客商基本信息.xls");
			model.put("sheetName", "国内客商基本信息");
		}else {
			model.put("list", exportCustomer);
			String[] titles = new String[] { "预约登记号", "国家", "公司中文名", "性别+姓名+职位", "地址", "邮箱", "网址", "城市", "手机", "电话", "传真", "登记时间","修改时间","备注" };
			model.put("titles", titles);
			String[] columns = new String[] { "checkingNo","countryString", "company", "name", "address", "email", "website", "city", "phone", "tel", "faxString", "createdTime", "updateTime", "remark" };
			model.put("columns", columns);
			Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20};
			model.put("columnWidths", columnWidths);
			model.put("fileName", "国外客商基本信息.xls");
			model.put("sheetName", "国外客商基本信息");
		}
		return new ModelAndView(new JXLExcelView(), model);
	}

	/**
	 * 导出所有展商展位确认涵
	 * @param eids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportExhibitorBoothConfirmToZip")
	public ModelAndView exportExhibitorBoothConfirmToZip(@RequestParam(value = "eids", defaultValue = "") Integer[] eids,
													   HttpServletRequest request,
													   HttpServletResponse response) throws Exception {
		List<TExhibitorInfo> exhibitorInfoList = new ArrayList<TExhibitorInfo>();
		PDFReaderUtil pdfReaderUtil = new PDFReaderUtil();

		String destDir = pdfReaderUtil.exhibitorInvitationSavePath;
		String randomFile = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\boothconfirm\\boothconfirm";
		String downloadDir = "boothconfirm";
		FileUtils.deleteDirectory(new File(destDir)); 	//删除现有文件
		FileUtils.forceMkdir(new File(destDir)); 		//创建新文件夹
		try {
			exhibitorInfoList = exhibitorService.loadExhibitorInfoList(eids);
			ExhibitorInvisitorEmail exhibitorInvisitorEmail = new ExhibitorInvisitorEmail();

			if(exhibitorInfoList.size()>0){
				for( TExhibitorInfo exhibitorInfo:exhibitorInfoList){
					TExhibitor tExhibitor = exhibitorService.loadExhibitorByEid(exhibitorInfo.getEid());
					TExhibitorBooth tExhibitorBooth = exhibitorManagerService.queryBoothByEid(exhibitorInfo.getEid());
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

					pdfReaderUtil.writePDF(pdfReaderUtil.exhibitorInvitationInputPath,
							pdfReaderUtil.exhibitorInvitationSavePath + exhibitorInvisitorEmail.getCompanyName() + ".pdf",
							exhibitorInvisitorEmail.getCompanyName(), exhibitorInvisitorEmail.getUserName(), exhibitorInvisitorEmail.getPassword(),
							pdfReaderUtil.exhibitorSelectPath,
							exhibitorInvisitorEmail.getBoothNumber(), exhibitorInvisitorEmail.getExhibitorArea(),
							exhibitorInvisitorEmail.getRawSpaceOrShellScheme());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
		}

		CreateZip.zipToFile(destDir, downloadDir);
		//zipCompressor(eids, randomFile, destDir);
		return download(destDir, downloadDir, request, response);
	}

	/**
	 * 导出境外客商问卷调查到Excel
	 * @param cids
	 * @return
	 */
	@RequestMapping(value = "exportForeignCustomerFeedBackToExcel", method = RequestMethod.POST)
	public ModelAndView exportForeignCustomerFeedBackToExcel(@RequestParam(value = "cids", defaultValue = "") Integer[] cids) {
		Map model = new HashMap();
		List<TVisitorFeedBack> visitorFeedBackList = new ArrayList<TVisitorFeedBack>();
		if(cids[0] == -1)
			visitorFeedBackList = customerInfoManagerService.loadAllForeignFeedBackData();
		else
			visitorFeedBackList = customerInfoManagerService.loadSelectedForeignFeedBackData(cids);
		//List<ExportCustomerSurvey> exportCustomer = importExportService.exportCustomerSurvey(visitorFeedBackList);
		model.put("list", visitorFeedBackList);
		String[] titles = new String[] { "ID","姓名", "q1","a1","q2", "a2", "q3", "a3", "q4", "a4","q5","a5","q6","a6", "q7","a7", "q8", "a8",
				"q9","a9","q10", "a10","q11","a11","q12","a12","q13","a13","q14","a14","q15","a15","q16","a16","q17","a17","q18","a18",
				"q19","a19","q20","a20","remark", "createTime", "readed"};
		model.put("titles", titles);
		String[] columns = new String[] { "id","userName","q1","a1","q2", "a2", "q3", "a3", "q4", "a4","q5","a5","q6","a6", "q7","a7", "q8", "a8",
				"q9","a9","q10", "a10","q11","a11","q12","a12","q13","a13","q14","a14","q15","a15","q16","a16","q17","a17","q18","a18",
				"q19","a19","q20","a20","remark", "createTime", "readed" };
		model.put("columns", columns);
		Integer[] columnWidths = new Integer[]{5,8,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,
				15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15,25,15};
		model.put("columnWidths", columnWidths);
		model.put("fileName", "境外客商问卷调查数据.xls");
		model.put("sheetName", "境外客商问卷调查数据");
		return new ModelAndView(new JXLExcelView(), model);
	}
}
