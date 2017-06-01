package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.entity.TExhibitor;
import com.zhenhappy.ems.entity.TVisitorInfo;
import com.zhenhappy.ems.entity.WCustomer;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.service.CustomerInfoManagerService;
import com.zhenhappy.ems.manager.service.ExhibitorManagerService;
import com.zhenhappy.ems.manager.service.ImportExportService;
import com.zhenhappy.ems.manager.util.JXLExcelView;
import com.zhenhappy.util.report.EchartMapResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangxd on 2016/4/5.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class DataReportAction extends BaseAction {

    private static Logger log = Logger.getLogger(DataReportAction.class);

    @Autowired
    private ImportExportService importExportService;
    @Autowired
    private ExhibitorManagerService exhibitorManagerService;
    @Autowired
    private CustomerInfoManagerService customerInfoManagerService;

    @Autowired
    JavaMailSender mailSender;// 注入Spring封装的javamail，Spring的xml中已让框架装配

    //数据报表网页
    @RequestMapping(value = "tReport")
    public ModelAndView directToDataReport() {
        ModelAndView modelAndView = new ModelAndView("user/report/dataReport");
        return modelAndView;
    }

    //数据报表网页
    @RequestMapping(value = "tDistribute")
    public ModelAndView directToDataDistribute() {
        ModelAndView modelAndView = new ModelAndView("user/report/dataDistribute");
        return modelAndView;
    }

    /**
     * 分页查询国内客商用于报表
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryExhibitorForReport")
    public EchartMapResponse queryExhibitorForReport(@RequestParam(value = "cids", defaultValue = "") Integer[] cids,
                                                           @ModelAttribute QueryCustomerRequest request) {
        EchartMapResponse response = new EchartMapResponse();
        try {
            response = customerInfoManagerService.queryExhibitorForReport(request, cids[0]);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query inland customers report error.", e);
        }
        return response;
    }

    /**
     * 分页查询展商/客商用于报表
     * @param source 0表示：展商，   1表示：客商
     * @param owner 空表示：全部，   数字表示：对应所属者
     * @param region  0表示：国内    1表示：全球
     * @param dimen  0表示：月份    1表示：天数
     * @param begindate   起始日期
     * @param enddate   截止日期
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryDataReportEx")
    public EchartMapResponse queryDataReportEx(@RequestParam(value = "source", defaultValue = "0") Integer[] source,
                                               @RequestParam(value = "owner", defaultValue = "-1") Integer owner,
                                               @RequestParam(value = "province", defaultValue = "-1") Integer province,
                                               @RequestParam(value = "region", defaultValue = "") Integer[] region,
                                               @RequestParam("dimen") Integer[] dimen,
                                               @RequestParam("startdate") String begindate,
                                               @RequestParam("enddate") String enddate,
                                               @ModelAttribute QueryCustomerRequest request) {
        EchartMapResponse response = new EchartMapResponse();
        try {
            if(source[0] == 1) {
                response = customerInfoManagerService.queryDataReportEx(request, owner, province, source[0], region[0], dimen[0], begindate, enddate);
            } else {
                response = customerInfoManagerService.queryDataReportEx1(request, owner, province, source[0], region[0], dimen[0], begindate, enddate);
            }
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query inland customers report error.", e);
        }
        return response;
    }

    /**
     * 导出查询展商/客商用于报表
     * @param source 0表示：展商，   1表示：客商
     * @param begindate   起始日期
     * @param enddate   截止日期
     * @return
     */
    @RequestMapping(value = "exportDataReportInfo", method = RequestMethod.POST)
    public ModelAndView exportDataReportInfo(@RequestParam(value = "source", defaultValue = "0") Integer[] source,
                                             @RequestParam(value = "owner", defaultValue = "-1") Integer owner,
                                             @RequestParam(value = "province", defaultValue = "-1") Integer province,
                                             @RequestParam("startdate") String begindate,
                                             @RequestParam("enddate") String enddate) {
        Map model = new HashMap();
        if(source[0] == 0){
            List<TExhibitor> exhibitors = exhibitorManagerService.loadAllExhibitorsByDate(owner, province, begindate, enddate);
            List<QueryExhibitorInfo> queryExhibitorInfos = importExportService.exportExhibitor(exhibitors);
            model.put("list", queryExhibitorInfos);
            String[] titles = new String[] { "展位号", "公司中文名", "公司英文名", "电话", "传真", "邮箱", "网址", "中文地址", "英文地址", "邮编", "产品分类", "主营产品(中文)", "主营产品(英文)", "公司简介", "发票抬头", "公司纳税人识别号" };
            model.put("titles", titles);
            String[] columns = new String[] { "boothNumber", "company", "companyEn", "phone", "fax", "email", "website", "address", "addressEn", "zipcode", "productType", "mainProduct", "mainProductEn", "mark", "invoiceTitle", "invoiceNo" };
            model.put("columns", columns);
            Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20};
            model.put("columnWidths", columnWidths);
            model.put("fileName", "展商基本信息.xls");
            model.put("sheetName", "展商基本信息");
        }
        else {
            List<WCustomer> visitorInfos = customerInfoManagerService.loadAllExhibitorsByDate(province, strToDate(begindate), strToDate(enddate));
            List<ExportCustomerInfo> exportCustomer = importExportService.exportCustomer(visitorInfos, -1);
            model.put("list", exportCustomer);
            String[] titles = new String[] { "公司中文名", "姓名", "性别", "职位", "国家", "城市", "邮箱", "手机", "电话", "传真", "网址", "地址", "备注" };
            model.put("titles", titles);
            String[] columns = new String[] { "company", "name", "sex", "position", "countryString", "city", "email", "phone", "tel", "faxString",  "website", "address", "remark" };
            model.put("columns", columns);
            Integer[] columnWidths = new Integer[]{20,20,20,20,20,20,20,20,20,20,20,20,20};
            model.put("columnWidths", columnWidths);
            model.put("fileName", "客商基本信息.xls");
            model.put("sheetName", "客商基本信息");
        }
        return new ModelAndView(new JXLExcelView(), model);
    }

    public Date strToDate(String str){
        SimpleDateFormat format = null;
        Date date = null;
        try {
            format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
