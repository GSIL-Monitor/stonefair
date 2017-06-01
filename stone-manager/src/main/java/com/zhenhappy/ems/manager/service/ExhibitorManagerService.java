package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.dao.ExhibitorClassDao;
import com.zhenhappy.ems.dao.ExhibitorDao;
import com.zhenhappy.ems.dao.ExhibitorInfoDao;
import com.zhenhappy.ems.dao.TInvoiceApplyDao;
import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.dto.QueryProductType2Response;
import com.zhenhappy.ems.entity.*;
import com.zhenhappy.ems.manager.dao.ExhibitorBoothDao;
import com.zhenhappy.ems.manager.dao.backupinfo.*;
import com.zhenhappy.ems.manager.dao.xicecmap.XicecMapDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.dto.backupinfo.QueryExhibitorBackup;
import com.zhenhappy.ems.manager.dto.backupinfo.QueryExhibitorBackupRequest;
import com.zhenhappy.ems.manager.dto.backupinfo.QueryProductOrContactOrJoinerRequest;
import com.zhenhappy.ems.manager.dto.xicecmap.QueryXicecMapIntetionRequest;
import com.zhenhappy.ems.manager.dto.xicecmap.QueryXicecMapIntetionResponse;
import com.zhenhappy.ems.manager.entity.TExhibitorBooth;
import com.zhenhappy.ems.manager.entity.TExhibitorTerm;
import com.zhenhappy.ems.manager.entity.backupinfo.*;
import com.zhenhappy.ems.manager.entity.xicecmap.TXicecMapIntetion;
import com.zhenhappy.ems.manager.exception.DuplicateUsernameException;
import com.zhenhappy.ems.manager.tag.StringUtil;
import com.zhenhappy.ems.manager.util.JChineseConvertor;
import com.zhenhappy.ems.service.ExhibitorService;
import com.zhenhappy.ems.service.InvoiceService;
import com.zhenhappy.ems.service.MeipaiService;
import com.zhenhappy.util.Page;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wujianbin on 2014-04-22.
 */
@Service
public class ExhibitorManagerService extends ExhibitorService {

	private static Logger log = Logger.getLogger(ExhibitorManagerService.class);

    @Autowired
    private ExhibitorDao exhibitorDao;
    @Autowired
    private TInvoiceApplyDao invoiceApplyDao;
    @Autowired
    private ExhibitorInfoDao exhibitorInfoDao;
    @Autowired
    private ExhibitorClassDao exhibitorClassDao;
    @Autowired
    private ContactManagerService contactService;
    @Autowired
    private JoinerManagerService joinerManagerService;
    @Autowired
    private TermManagerService termManagerService;
    @Autowired
    private MeipaiService meipaiService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductManagerService productManagerService;
    @Autowired
    private ExhibitorBoothDao exhibitorBoothDao;
    @Autowired
    private XicecMapDao xicecMapDao;
    @Autowired
    private TVisaManagerService tVisaManagerService;
    //备份数据
    @Autowired
    private ExhibitorBackupInfoDao exhibitorBackupInfoDao;
    @Autowired
    private ProductBackupInfoDao productBackupInfoDao;
    @Autowired
    private ContactBackupInfoDao contactBackupInfoDao;
    @Autowired
    private ExhibitorJoinerBackupInfoDao exhibitorJoinerBackupInfoDao;
    @Autowired
    private VisaBackupInfoDao visaBackupInfoDao;
    @Autowired
    private TagManagerService tagManagerService;

    /**
     * 分页获取展商列表
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
	@Transactional
    public QueryExhibitorResponse queryExhibitorsByPage(QueryExhibitorRequest request) throws UnsupportedEncodingException {
		List<String> conditions = new ArrayList<String>();
        if (request.getTag() != null) {
            conditions.add(" e.tag = " + request.getTag().intValue() + " ");
        }
        if (request.getGroup() != null) {
            conditions.add(" e.group = " + request.getGroup().intValue() + " ");
        }
        if (StringUtils.isNotEmpty(request.getBoothNumber())) {
            conditions.add(" e.eid in (select eid from TExhibitorBooth where boothNumber like '%" + new String(request.getBoothNumber().getBytes("ISO-8859-1"),"GBK") + "%') ");
        }
        if (StringUtils.isNotEmpty(request.getCompany())) {
            conditions.add(" (i.company like '%" + request.getCompany() + "%' OR i.company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"GBK") + "%' OR i.company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (StringUtils.isNotEmpty(request.getCompanye())) {
            conditions.add(" (i.companyEn like '%" + request.getCompanye() + "%' OR i.companyEn like '%" + new String(request.getCompanye().getBytes("ISO-8859-1"),"GBK") + "%' OR i.companyEn like '%" + new String(request.getCompanye().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (StringUtils.isNotEmpty(request.getContractId())) {
            conditions.add(" (e.contractId like '%" + request.getContractId() + "%' OR e.contractId like '%" + new String(request.getContractId().getBytes("ISO-8859-1"),"GBK") + "%' OR e.contractId like '%" + new String(request.getContractId().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (request.getArea() != null) {
            conditions.add(" e.area = " + request.getArea().intValue() + " ");
        }
        if (request.getCountry() != null) {
            conditions.add(" e.country = " + request.getCountry().intValue() + " ");
        }
        if (request.getProvince() != null) {
            conditions.add(" e.province = " + request.getProvince().intValue() + " ");
        }
        if (request.getIsLogout() != null) {
            conditions.add(" e.isLogout = " + request.getIsLogout().intValue() + " ");
        }
        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlNoOrder = " where " + conditionsSql;
        }
        conditions.add(" e.eid=b.eid and e.eid=i.eid");
        conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlOrder = "";
        if(StringUtils.isNotEmpty(request.getSort()) && StringUtils.isNotEmpty(request.getOrder())){
            if(!"null".equals(request.getOrder())){
                if("boothNumber".equals(request.getSort())){
                    conditionsSqlOrder = " where " + conditionsSql + " order by b." + request.getSort() + " " + request.getOrder() + " ";
                }else{
                    //conditionsSqlOrder = " where " + conditionsSql + " order by e." + request.getSort() + " " + request.getOrder() + " ";
                    //排序空值任何情况下都最后
                    conditionsSqlOrder = " where " + conditionsSql + " order by case when (e." + request.getSort() + "='' or e." + request.getSort() + " is null) then 1 else 0 end , e." + request.getSort() + " " + request.getOrder() + " ";
                }
            }else{
                conditionsSqlOrder = " where " + conditionsSql + " order by e.createTime ASC ";
            }
        }else{
            conditionsSqlOrder = " where " + conditionsSql + " order by e.createTime ASC ";
        }
        Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        if(StringUtils.isNotEmpty(conditionsSqlNoOrder)) {
            conditionsSqlNoOrder = conditionsSqlNoOrder + " and e.eid=i.eid";
        } else {
            conditionsSqlNoOrder = " where e.eid=i.eid";
        }
        List<QueryExhibitor> exhibitors = exhibitorDao.queryPageByHQL("select count(*) from TExhibitor e, TExhibitorInfo i " + conditionsSqlNoOrder,
                "select new com.zhenhappy.ems.manager.dto.QueryExhibitor(e.eid, e.username, e.password, e.area, i.company, i.companyEn, e.country, e.province, e.isLogout, e.isLogin, e.tag, e.group, b.boothNumber, e.exhibitionArea, e.contractId) "
                        + "from TExhibitor e, TExhibitorBooth b, TExhibitorInfo i " + conditionsSqlOrder, new Object[]{}, page);
        for(QueryExhibitor exhibitor:exhibitors){
            TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(exhibitor.getEid());
            if(exhibitorInfo == null){
                System.out.println("eid=" + exhibitor.getEid() + "公司中文名=" +  exhibitor.getCompany() + "公司英文名=" +  exhibitor.getCompanye() + "\n" +  "没有展商详细信息");
            }else{
                exhibitor.setInfoFlag(selectColor(exhibitor, exhibitorInfo));
                if(exhibitor.getIsLogout() == 0 && null != exhibitor.getIsLogin() && exhibitor.getIsLogin() == 0){
                    exhibitor.setInfoFlag(5);
                }
            }
        }
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        response.setResultCode(0);
        response.setRows(exhibitors);
        response.setTotal(page.getTotalCount());
        return response;
    }

    /**
     * 判断列表颜色
     * @return
     */
    @Transactional
    public int selectColor(QueryExhibitor exhibitor, TExhibitorInfo exhibitorInfo) {
        if(exhibitor.getIsLogout() == 0){
            if (exhibitorInfo.getUpdateTime() != null) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (exhibitorInfo != null) {
                        if ((StringUtils.isNotEmpty(exhibitorInfo.getCompany()) || StringUtils.isNotEmpty(exhibitorInfo.getCompanyEn())) &&
                                StringUtils.isNotEmpty(exhibitorInfo.getPhone()) &&
                                StringUtils.isNotEmpty(exhibitorInfo.getFax()) &&
                                StringUtils.isNotEmpty(exhibitorInfo.getEmail()) &&
                                (StringUtils.isNotEmpty(exhibitorInfo.getAddress()) || StringUtils.isNotEmpty(exhibitorInfo.getAddressEn())) &&
                                (StringUtils.isNotEmpty(exhibitorInfo.getMainProduct())
                                        || StringUtils.isNotEmpty(exhibitorInfo.getMainProductEn()))) {
                            if (StringUtils.isNotEmpty(queryExhibitorClassByEinfoid(exhibitorInfo.getEinfoid()))) {
                                List<TExhibitorJoiner> joiners = joinerManagerService.loadExhibitorJoinerByEid(exhibitorInfo.getEid());
                                if(joiners.size()>0){
                                    //账号信息完整-蓝色
                                    return 1;
                                } else {
                                    //人员列表缺失-橙色
                                    return 3;
                                }
                                /*if (exhibitorInfo.getUpdateTime().before(sdf.parse("2015-09-01 00:00:00"))) {
                                    //在2015-08-01 00:00:00之前
                                    return 1;
                                } else {
                                    //在2015-09-01 00:00:00之后
                                    if (joiners.size() > 0) {
                                        if (exhibitor.getArea() != null) {
                                            if (exhibitor.getArea() == 1) {
                                                TInvoiceApply invoice = invoiceService.getByEid(exhibitorInfo.getEid());
                                                if (invoice != null) {
                                                    if (StringUtils.isNotEmpty(invoice.getInvoiceNo()) && StringUtils.isNotEmpty(invoice.getTitle())) {
                                                        return 4;
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }*/
                            }else {
                                //必填项缺失
                                return 2;
                            }
                        } else {
                            //基本信息缺失-红色
                            return 2;
                        }
                        /*else if (StringUtils.isNotEmpty(exhibitorInfo.getEmail()) && StringUtils.isNotEmpty(exhibitorInfo.getPhone())) {
                            if (StringUtils.isNotEmpty(queryExhibitorClassByEinfoid(exhibitorInfo.getEinfoid()))) {
                                //非必填项缺失-橙色
                                return 3;
                            }
                            return 2;
                        }
                        return 2;*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * 查询所有展商列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadAllExhibitors() {
//        List<TExhibitor> exhibitors = exhibitorDao.query();
		List<TExhibitor> exhibitors = exhibitorDao.queryByHql("from TExhibitor where isLogout = 0", new Object[]{});
		return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 查询所有非展团境外展商参展人员列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadAllOutlandExhibitorsOutOfGroup() {
        List<TExhibitor> exhibitors = exhibitorDao.queryByHql("from TExhibitor where isLogout = 0 and area=2 and group=null ", new Object[]{});
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 根据eids查询非展团境外展商参展人员列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadSelectedOutlandExhibitorsOutOfGroup(Integer[] eids) {
        List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
        for (Integer eid:eids){
            TExhibitor exhibitor = loadExhibitorByEid(eid);
            if (exhibitor != null) exhibitors.add(exhibitor);
        }
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 查询增值税专用发票
     * @return
     */
    @Transactional
    public List<TInvoiceApplyExtend> loadAllInvoiceApplyByInvoiceFlag() {
        List<TInvoiceApplyExtend> invoiceApplyList = invoiceApplyDao.queryByHql("from TInvoiceApplyExtend where invoice_flag = 3 or invoice_flag = 2 ", new Object[]{});
        return invoiceApplyList.size() > 0 ? invoiceApplyList : null;
    }

    /**
     * 查询所有发票
     * @return
     */
    @Transactional
    public List<TInvoiceApplyExtend> loadAllInvoiceApply() {
        List<TInvoiceApplyExtend> invoiceApplyList = invoiceApplyDao.queryByHql("from TInvoiceApplyExtend ", new Object[]{});
        return invoiceApplyList.size() > 0 ? invoiceApplyList : null;
    }

    /**
     * 根据eid查询增发票信息
     * @param eid
     * @return
     */
    @Transactional
    public TInvoiceApplyExtend loadInvoiceApplyExtendByOnlyEid(Integer eid) {
        List<TInvoiceApplyExtend> invoiceApplyList = invoiceApplyDao.queryByHql("from TInvoiceApplyExtend where eid=?", new Object[]{eid});
        return invoiceApplyList.size() > 0 ? invoiceApplyList.get(0) : null;
    }

    /**
     * 根据eids查询发票
     * @return
     */
    @Transactional
    public List<TInvoiceApplyExtend> loadSelectedInvoiceApplyByOnlyInvoiceFlag(Integer[] eids) {
        List<TInvoiceApplyExtend> invoiceApplyExtendArrayList = new ArrayList<TInvoiceApplyExtend>();
        for (Integer eid:eids){
            TInvoiceApplyExtend invoiceApplyExtend = loadInvoiceApplyExtendByOnlyEid(eid);
            if (invoiceApplyExtend != null)
                invoiceApplyExtendArrayList.add(invoiceApplyExtend);
        }
        return invoiceApplyExtendArrayList.size() > 0 ? invoiceApplyExtendArrayList : null;
    }

    /**
     * 根据eids查询增值税专用发票
     * @return
     */
    @Transactional
    public List<TInvoiceApplyExtend> loadSelectedInvoiceApplyByInvoiceFlag(Integer[] eids) {
        List<TInvoiceApplyExtend> invoiceApplyExtendArrayList = new ArrayList<TInvoiceApplyExtend>();
        for (Integer eid:eids){
            TInvoiceApplyExtend invoiceApplyExtend = loadInvoiceApplyExtendByEid(eid);
            if (invoiceApplyExtend != null)
                invoiceApplyExtendArrayList.add(invoiceApplyExtend);
        }
        return invoiceApplyExtendArrayList.size() > 0 ? invoiceApplyExtendArrayList : null;
    }

    /**
     * 根据eid查询增值税专用发票
     * @param eid
     * @return
     */
    @Transactional
    public TInvoiceApplyExtend loadInvoiceApplyExtendByEid(Integer eid) {
        List<TInvoiceApplyExtend> invoiceApplyList = invoiceApplyDao.queryByHql("from TInvoiceApplyExtend where (invoice_flag = 3 or invoice_flag = 2) and eid=?", new Object[]{eid});
        return invoiceApplyList.size() > 0 ? invoiceApplyList.get(0) : null;
    }

    /**
     * 根据eid查询增值税专用发票
     * @param eid
     * @return
     */
    @Transactional
    public TInvoiceApplyExtend getInvoiceApplyExtendByEid(Integer eid) {
        List<TInvoiceApplyExtend> invoiceApplyList = invoiceApplyDao.queryByHql("from TInvoiceApplyExtend where eid=?", new Object[]{eid});
        return invoiceApplyList.size() > 0 ? invoiceApplyList.get(0) : null;
    }

    /**
     * 查询所有展商列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadAllExhibitorList() {
        List<TExhibitor> exhibitors = exhibitorDao.queryByHql("from TExhibitor", new Object[]{});
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 查询所有国内已激活展商列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadAllInlandActiveExhibitorList() {
        List<TExhibitor> exhibitors = exhibitorDao.queryByHql("from TExhibitor where isLogout=0 and area=1", new Object[]{});
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 根据日期范围查询展商列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadAllExhibitorsByDate(Integer owner, Integer province, String begDate, String endDate) {
        List<String> conditions = new ArrayList<String>();
        if(province != -1){
            conditions.add(" province = " + province + " ");
        }
        if(owner != -1) {
            conditions.add(" tag = " + owner + " ");
        }
        conditions.add(" createTime between \'" +  begDate + "\' and \'" + endDate + "\'");
        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = " where " + conditionsSql;
        String sqlStr = "from TExhibitor " + conditionsSqlNoOrder;

        System.out.println("sqlStr: " + sqlStr);
        List<TExhibitor> exhibitors = exhibitorDao.queryByHql(sqlStr, new Object[]{});
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 根据eids查询展商列表
     * @return
     */
    @Transactional
    public List<TExhibitor> loadSelectedExhibitors(Integer[] eids) {
//        List<TExhibitor> exhibitors = exhibitorDao.loadExhibitorsByEids(eids);
//		List<TExhibitor> exhibitors = exhibitorDao.queryByHql("from TExhibitor where isLogout = 0 && eid=?", new Object[]{ eids });
//        return exhibitors.size() > 0 ? exhibitors : null;
        List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
        for (Integer eid:eids){
            TExhibitor exhibitor = loadExhibitorByEid(eid);
            if (exhibitor != null) exhibitors.add(exhibitor);
        }
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 根据eid查询展商
     * @param eid
     * @return
     */
    @Transactional
    public TExhibitor loadExhibitorByEid(Integer eid) {
        return exhibitorDao.query(eid);
    }

    /**
     * 通过展团查询展商
     * @param groupId
     */
    @Transactional
    public List<TExhibitor> loadExhibitorByGroupId(Integer groupId) {
    	List<TExhibitor> exhibitors = exhibitorDao.queryByHql("from TExhibitor where group=?", new Object[]{groupId});
    	return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 根据username查询展商
     * @param username
     * @return
     */
    @Transactional
    public TExhibitor loadAllExhibitorByUsername(String username) {
    	if(StringUtils.isNotEmpty(username)){
    		List<TExhibitor> exhibitors = getHibernateTemplate().find("from TExhibitor where username = ?", new Object[]{username});
        	return exhibitors.size() > 0 ? exhibitors.get(0) : null;
    	}else return null;
    }

    /**
     * 根据username查询展商
     * @param username
     * @param exceptEid 排除指定的eid
     * @return
     */
    @Transactional
    public TExhibitor loadAllExhibitorByUsername(String username, Integer exceptEid) {
    	if(StringUtils.isNotEmpty(username)){
			List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
			if(exceptEid != null){
				exhibitors = getHibernateTemplate().find("from TExhibitor where username = ? and eid <> ?", new Object[]{username, exceptEid.intValue()});
			}else{
				exhibitors = getHibernateTemplate().find("from TExhibitor where username = ?", new Object[]{username});
			}
    		return exhibitors.size() > 0 ? exhibitors.get(0) : null;
    	}else return null;
    }

    /**
     * 根据company查询展商
     * @param company
     * @return
     */
    @Transactional
    public TExhibitorInfo loadAllExhibitorByCompany(String company) {
		if(StringUtils.isNotEmpty(company)){
			List<TExhibitorInfo> exhibitors = getHibernateTemplate().find("from TExhibitorInfo where company = ?", new Object[]{company});
    		return exhibitors.size() > 0 ? exhibitors.get(0) : null;
    	}else return null;
    }

    /**
     * 根据company查询展商
     * @param company
     * @param exceptEid 排除指定的eid
     * @return
     */
    @Transactional
    public TExhibitorInfo loadAllExhibitorByCompany(String company, Integer exceptEid) {
		if(StringUtils.isNotEmpty(company)){
			List<TExhibitorInfo> exhibitors = new ArrayList<TExhibitorInfo>();
			if(exceptEid != null){
				exhibitors = getHibernateTemplate().find("from TExhibitorInfo where company = ? and eid <> ?", new Object[]{company, exceptEid.intValue()});
			}else{
				exhibitors = getHibernateTemplate().find("from TExhibitorInfo where company = ?", new Object[]{company});
			}
    		return exhibitors.size() > 0 ? exhibitors.get(0) : null;
    	}else return null;
    }

    /**
     * 根据companye查询展商
     * @param companye
     * @return
     */
    @Transactional
    public TExhibitorInfo loadAllExhibitorByCompanye(String companye) {
    	if(StringUtils.isNotEmpty(companye)){
			List<TExhibitorInfo> exhibitors = getHibernateTemplate().find("from TExhibitorInfo where companyEn = ?", new Object[]{companye});
    		return exhibitors.size() > 0 ? exhibitors.get(0) : null;
    	}else return null;
    }

    /**
     * 根据companye查询展商
     * @param companye
     * @param exceptEid 排除指定的eid
     * @return
     */
    @Transactional
    public TExhibitorInfo loadAllExhibitorByCompanye(String companye, Integer exceptEid) {
    	if(StringUtils.isNotEmpty(companye)){
			List<TExhibitorInfo> exhibitors = new ArrayList<TExhibitorInfo>();
			if(exceptEid != null){
				exhibitors = getHibernateTemplate().find("from TExhibitorInfo where companyEn = ? and eid <> ?", new Object[]{companye, exceptEid.intValue()});
			}else{
				exhibitors = getHibernateTemplate().find("from TExhibitorInfo where companyEn = ?", new Object[]{companye});
			}
    		return exhibitors.size() > 0 ? exhibitors.get(0) : null;
    	}else return null;
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
    	}else {
            return null;
        }
    }

    /**
     * 查询所有展商列表
     * @return
     */
    @Transactional
    public List<TExhibitorInfo> loadAllExhibitorInfo() {
//        List<TExhibitor> exhibitors = exhibitorDao.query();
        List<TExhibitorInfo> exhibitors = exhibitorInfoDao.queryByHql("from TExhibitorInfo", new Object[]{});
        return exhibitors.size() > 0 ? exhibitors : null;
    }

    /**
     * 根据展商列表查询展商基本信息列表
     * @param exhibitors
     * @return
     */
    @Transactional
    public List<TExhibitorInfo> loadExhibitorInfoByExhibitors(List<TExhibitor> exhibitors) {
    	if(exhibitors != null){
    		List<TExhibitorInfo> exhibitorInfos = new ArrayList<TExhibitorInfo>();
        	for(TExhibitor exhibitor:exhibitors){
        		TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(exhibitor.getEid());
        		if(exhibitorInfo != null){
        			/*exhibitorInfo.setCompany(exhibitor.getCompany());
            		exhibitorInfo.setCompanyEn(exhibitor.getCompanye());*/
            		exhibitorInfos.add(exhibitorInfo);
        		}
        	}
        	return exhibitorInfos.size() > 0 ? exhibitorInfos : null;
    	}else return null;
    }

    /**
     * 根据eids删除商基本信息
     * @param eids
     */
    @Transactional
    public void deleteExhibitorInfoByEid(Integer[] eids) {
    	if(eids.length > 0){
	    	for(Integer eid:eids){
	    		TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(eid);
	    		if(exhibitorInfo != null) getHibernateTemplate().delete(exhibitorInfo);
	    	}
    	}
    }

    /**
     * 根据eids查询所有展位号+楣牌
     * @param eids
     * @return
     */
    @Transactional
    public List<QueryBoothNumAndMeipai> loadBoothNumAndMeipai(Integer[] eids){
    	List<TExhibitor> exhibitors = new ArrayList<TExhibitor>();
    	if(eids == null)
            exhibitors = loadAllExhibitors();
    	else
            exhibitors = loadSelectedExhibitors(eids);
    	List<QueryBoothNumAndMeipai> boothNumAndMeipais = new ArrayList<QueryBoothNumAndMeipai>();
		for(TExhibitor exhibitor:exhibitors){
			TExhibitorBooth booth = queryBoothByEid(exhibitor.getEid());
			if(booth != null){
				QueryBoothNumAndMeipai boothNumAndMeipai = new QueryBoothNumAndMeipai();
                //所属人
                TTag tag = tagManagerService.loadTagById(exhibitor.getTag());
                if(tag != null){
                    boothNumAndMeipai.setTagName(tag.getName());
                }
                //展位号
                if(StringUtils.isNotEmpty(booth.getBoothNumber()))
                    boothNumAndMeipai.setBoothNumber(booth.getBoothNumber());
                else
                    boothNumAndMeipai.setBoothNumber("");
                //公司中/英文名
                TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(exhibitor.getEid());
                boothNumAndMeipai.setCompanyZh(exhibitorInfo.getCompany());
                boothNumAndMeipai.setCompanyEn(exhibitorInfo.getCompanyEn());
                //合同编号
                boothNumAndMeipai.setContractID(exhibitor.getContractId());
                /*boothNumAndMeipai.setCompany(exhibitor.getCompany());*/
				boothNumAndMeipai.setEid(exhibitor.getEid());
                /*石材展需求开始*/
                //企业楣牌中英文名
                TExhibitorInfo info = loadExhibitorInfoByEid(exhibitor.getEid());
                if(info != null){
                    if(StringUtils.isNotEmpty(info.getMeipai()))
                        boothNumAndMeipai.setMeipai(info.getMeipai());
                    else
                        boothNumAndMeipai.setMeipai("");
                    if(StringUtils.isNotEmpty(info.getMeipaiEn()))
                        boothNumAndMeipai.setMeipaiEn(info.getMeipaiEn());
                    else
                        boothNumAndMeipai.setMeipaiEn("");
                }
                //展位面积
                boothNumAndMeipai.setAreaNum(exhibitor.getExhibitionArea()+"平米");
				if(StringUtils.isEmpty(booth.getBoothNumber()) && StringUtils.isEmpty(info.getMeipai()) && StringUtils.isEmpty(info.getMeipaiEn()))
                    continue;
                else
                    boothNumAndMeipais.add(boothNumAndMeipai);
                /*石材展需求结束*/
			}
		}
		return boothNumAndMeipais;
    }

    /**
     * 根据eid查询展会届数
     * @param eid
     * @return
     */
    @Transactional
    public TExhibitorTerm getExhibitorTermByEid(Integer eid) {
    	if(eid != null){
    		List<TExhibitorTerm> terms = getHibernateTemplate().find("from TExhibitorTerm where eid = ?", new Object[]{eid});
    		String current_term = getJdbcTemplate().queryForObject("select value from t_dictionary where keyname='current_term'", String.class);
    		if(terms.size() > 0){
    			for (TExhibitorTerm term:terms) {
    				if(term.getJoinTerm().toString().equals(current_term)) return term;
    			}
    		}else return null;
    	}
   		return null;
    }

    /**
     * 根据eid获取展位信息
     * @param eid
     * @return
     */
    @Transactional
    public TExhibitorBooth queryBoothByEid(Integer eid) {
        List<TExhibitorBooth> booths = getHibernateTemplate().find("from TExhibitorBooth where eid = ?", new Object[]{ eid });
        return booths.size() > 0 ? booths.get(0) : null;
    }

    /**
     * 根据展位号查询展位信息
     * @param boothNum
     * @return
     */
    @Transactional
    public boolean queryBoothByBoothNumIsActive(String boothNum) {
        boolean flag = false;
        List<TExhibitorBooth> booths = getHibernateTemplate().find("from TExhibitorBooth where boothNumber = ?", new Object[]{boothNum});
        if(booths != null && booths.size()>0){
            for(TExhibitorBooth booth:booths){
                TExhibitor tExhibitor = loadExhibitorByEid(booth.getEid());
                if(tExhibitor != null && tExhibitor.getIsLogout() == 0){
                    return true;
                }
            }
        }
        return flag;
    }

    /**
     * 根据展位号查询展位信息
     * @param boothNum
     * @return
     */
    @Transactional
    public TExhibitorBooth queryBoothByBoothNum(String boothNum) {
        List<TExhibitorBooth> booths = getHibernateTemplate().find("from TExhibitorBooth where boothNumber = ?", new Object[]{boothNum});
        return booths.size() > 0 ? booths.get(0) : null;
    }

    /**
     * 根据展位号查询展位信息
     * @param boothNum
     * @return
     */
    @Transactional
    public TExhibitorBooth queryBoothByBoothNumAndEid(String boothNum, Integer eidValue) {
        List<TExhibitorBooth> booths = getHibernateTemplate().find("from TExhibitorBooth where boothNumber = ? and eid <> ?", new Object[]{boothNum, eidValue});
        return booths.size() > 0 ? booths.get(0) : null;
    }

    /**
     * 查询所有展会届数
     * @return
     */
    @Transactional
    public Integer queryCurrentTermNumber() {
        return getJdbcTemplate().queryForObject("select t.value from t_dictionary t where t.keyname = ?", new Object[]{"current_term"}, Integer.class);
    }

    /**
     * 根据classId查询产品类型
     * @param classId
     * @return
     */
    @Transactional
    public TExhibitorClass loadExhibitorClassByClassId(Integer classId) {
    	List<TExhibitorClass> exhibitorClasses = exhibitorClassDao.queryByHql("from TExhibitorClass where classId=?", new Object[]{classId});
    	return exhibitorClasses.size()>0?exhibitorClasses.get(0):null;
	}

    /**
	 * 根据einfoids查询产品类型
	 * @param einfoids
	 * @return
	 */
	@Transactional
	public List<TExhibitorClass> queryExhibitorClassByClassEinfoids(List<Integer> einfoids) {
    	List<TExhibitorClass> exhibitorClasses = new ArrayList<TExhibitorClass>();
    	for(Integer einfoid:einfoids){
    		List<TExhibitorClass> exhibitorClass = exhibitorClassDao.queryByHql("from TExhibitorClass where einfoId=?", new Object[]{einfoid});
    		exhibitorClasses.addAll(exhibitorClass);
    	}
    	return exhibitorClasses;
	}

	/**
     * 根据eid查询产品类型
     * @param eid
     * @return
     */
    @Transactional
    public List<TExhibitorClass> queryExhibitorClassByEid(Integer eid) {
    	List<TExhibitorClass> exhibitorClass = exhibitorClassDao.queryByHql("from TExhibitorClass where einfoId = ?", new Object[]{eid});
    	return exhibitorClass;
    }

    /**
     * 根据id查询产品类型
     * @param id
     * @return
     */
    @Transactional
    public TExhibitorClass queryExhibitorClassById(Integer id) {
    	TExhibitorClass exhibitorClass = exhibitorClassDao.query(id);
    	return exhibitorClass;
    }

    /**
     * 根据einfoid查询产品类型
     * @param einfoid
     * @return
     */
    @Transactional
    public String queryExhibitorClassByEinfoid(Integer einfoid){
    	List<TExhibitorClass> exhibitorClasses = queryExhibitorClassByEid(einfoid);
    	String productType = "";
    	for(TExhibitorClass exhibitorClass:exhibitorClasses){
    		productType += productManagerService.loadClassNameById(exhibitorClass.getClassId()) + ";";
    	}
    	return productType;
    }

	/**
     * 添加展商账号
     * @param request
     * @param adminId
     * @throws Exception
     */
    @Transactional
    public void addExhibitor(AddExhibitorRequest request, Integer adminId) throws Exception {
    	if(StringUtils.isEmpty(request.getCompanyName().trim()) && StringUtils.isEmpty(request.getCompanyNameE().trim())){
    		throw new DuplicateUsernameException("公司中英文名至少要有一项");
    	}
    	if(loadAllExhibitorByCompany(request.getCompanyName().trim()) != null) throw new DuplicateUsernameException("公司中文名重复");
    	if(loadAllExhibitorByCompanye(request.getCompanyNameE().trim()) != null) throw new DuplicateUsernameException("公司英文名重复");
    	if(request.getBoothNumber() != null && !"".equals(request.getBoothNumber())){
    		if(queryBoothByBoothNumIsActive(request.getBoothNumber())) throw new DuplicateUsernameException("展位号已经存在");
    	}
    	TExhibitor exhibitor = new TExhibitor();
        TExhibitorInfo exhibitorInfo = new TExhibitorInfo();
        exhibitorInfo.setCompany(request.getCompanyName().trim());
        exhibitorInfo.setCompanyEn(request.getCompanyNameE().trim());
        exhibitorInfo.setCompanyT(JChineseConvertor.getInstance().s2t(request.getCompanyName().trim()));
        /*exhibitor.setCompany(request.getCompanyName().trim());
        exhibitor.setCompanye(request.getCompanyNameE().trim());
        exhibitor.setCompanyt(JChineseConvertor.getInstance().s2t(request.getCompanyName().trim()));*/
        exhibitor.setCountry(request.getCountry());
        exhibitor.setProvince(request.getProvince());
        exhibitor.setLevel(request.getLevel());
        exhibitor.setArea(request.getArea());
        exhibitor.setContractId(request.getContractId());
        exhibitor.setExhibitionArea(request.getExhibitionArea());
        if (StringUtils.isNotEmpty(request.getUsername())){
        	if(loadAllExhibitorByUsername(request.getUsername()) != null) throw new DuplicateUsernameException("用户名重复");
        	else{
        		exhibitor.setUsername(request.getUsername());
                exhibitor.setPassword(request.getPassword());
        	}
    	}
        exhibitor.setTag(request.getTag());
        exhibitor.setIsLogout(0);
        exhibitor.setCreateUser(adminId);
        exhibitor.setCreateTime(new Date());
        exhibitor.setSend_invitation_flag(0);
        Integer eid = (Integer) getHibernateTemplate().save(exhibitor);
        /*//---add by wangxd, begin---
        exhibitorInfo.setEid(eid);
        getHibernateTemplate().save(exhibitorInfo);
        //---add by wangxd, end---*/
        ModifyExhibitorInfoRequest modifyExhibitorInfoRequest = new ModifyExhibitorInfoRequest();
        modifyExhibitorInfoRequest.setEid(eid);
        /*modifyExhibitorInfoRequest.setCompany(exhibitor.getCompany());
        modifyExhibitorInfoRequest.setCompanyEn(exhibitor.getCompanye());*/
        modifyExhibitorInfoRequest.setCompany(exhibitorInfo.getCompany());
        modifyExhibitorInfoRequest.setCompanyEn(exhibitorInfo.getCompanyEn());
        modifyExhibitorInfo(modifyExhibitorInfoRequest, eid, adminId);
    	if(eid != null){
    		TExhibitorBooth booth = new TExhibitorBooth();
            booth.setBoothNumber(request.getBoothNumber().trim().replace(" ", ""));
            //2016-11-21由陈丽宽新增的功能需求，调整为手动输入展厅数据
            //booth.setExhibitionArea(request.getBoothNumber().trim().replace(" ", "").substring(0,1) + "厅");
            booth.setExhibitionArea(request.getExhibitionPosition());
            booth.setEid(eid);
            booth.setMark("");
            booth.setCreateTime(new Date());
            booth.setCreateUser(adminId);
            bindBoothInfo(booth);
    	}else{
    		throw new Exception();
    	}
    }

    /**
     * 修改展商账号
     * @param request
     * @param adminId
     * @throws Exception
     */
    @Transactional
    public void modifyExhibitorAccount(ModifyExhibitorRequest request, Integer adminId) throws Exception {
    	if(StringUtils.isEmpty(request.getCompanyName().trim()) && StringUtils.isEmpty(request.getCompanyNameE().trim())){
    		 throw new DuplicateUsernameException("公司中英文名至少要有一项");
    	}
    	if(loadAllExhibitorByCompany(request.getCompanyName().trim(), request.getEid()) != null) throw new DuplicateUsernameException("公司中文名重复");
    	if(loadAllExhibitorByCompanye(request.getCompanyNameE().trim(), request.getEid()) != null) throw new DuplicateUsernameException("公司英文名重复");

        if(StringUtils.isNotEmpty(request.getBoothNumber())) {
            TExhibitorBooth booth = queryBoothByBoothNumAndEid(request.getBoothNumber(), request.getEid());
            if(booth != null){
                TExhibitor tExhibitorTemp = loadExhibitorByEid(booth.getEid());
                if(tExhibitorTemp != null && tExhibitorTemp.getIsLogout() == 0){
                    throw new DuplicateUsernameException(request.getBoothNumber() + "展位号，已经卖出去，请重新确认下此展位号");
                }
            }
        }

        List<TVisa> visas = getHibernateTemplate().find("from TVisa where eid = ?", request.getEid());
        if (visas.size() != 0){
            for (TVisa visa : visas){
                visa.setBoothNo(request.getBoothNumber());
                //只保存对应的值，但更新时间不做修改
                tVisaManagerService.save(visa);
            }
        }

        TExhibitor exhibitor = exhibitorDao.query(request.getEid());
        TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(request.getEid());
    	if(exhibitor != null){
    		if(StringUtils.isNotEmpty(request.getUsername().trim())){
    			if(loadAllExhibitorByUsername(request.getUsername().trim(), request.getEid()) != null) throw new DuplicateUsernameException("用户名重复");
	        	else{
	        		exhibitor.setUsername(request.getUsername().trim());
	                exhibitor.setPassword(request.getPassword().trim());
	        	}
	    	}else{
	    		exhibitor.setUsername(null);
	            exhibitor.setPassword(null);
	    	}
	        exhibitor.setUpdateTime(new Date());
	        exhibitor.setUpdateUser(adminId);
            if(request.getCompanyName() != null && request.getCompanyName().trim() != null){
                exhibitorInfo.setCompany(request.getCompanyName().trim());
            }
            if(request.getCompanyNameE() != null && request.getCompanyNameE().trim() != null) {
                exhibitorInfo.setCompanyEn(request.getCompanyNameE().trim());
            }
	        /*exhibitor.setCompany(request.getCompanyName().trim());
	        exhibitor.setCompanye(request.getCompanyNameE().trim());*/
	        exhibitor.setCountry(request.getCountry());
	        exhibitor.setProvince(request.getProvince());
	        exhibitor.setTag(request.getTag());
	        exhibitor.setArea(request.getArea());
            exhibitor.setContractId(request.getContractId().trim());
            if(StringUtils.isNotEmpty(request.getExhibitionArea()))
                exhibitor.setExhibitionArea(request.getExhibitionArea());
            else
                exhibitor.setExhibitionArea("0");
	        exhibitorDao.update(exhibitor);
            exhibitorInfoDao.update(exhibitorInfo);
    	}
    }

    /**
     * 激活展商
     * @param term
     */
    @Transactional
    public void activeExhibitor(TExhibitorTerm term) {
        if (term.getId() != null) {
            TExhibitorTerm saved = getHibernateTemplate().get(TExhibitorTerm.class, term.getId());
            saved.setJoinTerm(term.getJoinTerm());
            saved.setMark(term.getMark());
            saved.setBoothNumber(term.getBoothNumber());
            saved.setUpdateTime(new Date());
            saved.setUpdateUser(term.getUpdateUser());
            getHibernateTemplate().update(saved);
        } else {
            getHibernateTemplate().save(term);
        }
    }

    /**
     * 绑定展位号
     * @param booth
     */
    @Transactional
    public void bindBoothInfo(TExhibitorBooth booth) {
        if (booth.getId() != null) {
            TExhibitorBooth temp = getHibernateTemplate().get(TExhibitorBooth.class, booth.getId());
            temp.setExhibitionArea(booth.getExhibitionArea());
            temp.setBoothNumber(booth.getBoothNumber());
            temp.setMark(booth.getMark());
            temp.setUpdateUser(booth.getUpdateUser());
            temp.setUpdateTime(new Date());
            getHibernateTemplate().update(temp);
        } else {
            getHibernateTemplate().save(booth);
        }
    }

    /**
     * 根据eids批量修改所属人
     * @param eids
     * @param tag
     * @param adminId
     */
    @Transactional
    public void modifyExhibitorsTag(Integer[] eids, Integer tag, Integer adminId) {
    	if(eids != null){
    		List<TExhibitor> exhibitors = loadSelectedExhibitors(eids);
    		if(exhibitors.size() > 0){
    			for(TExhibitor exhibitor:exhibitors){
            		exhibitor.setTag(tag);
            		exhibitor.setUpdateUser(adminId);
            		exhibitor.setUpdateTime(new Date());
            		getHibernateTemplate().update(exhibitor);
            	}
    		}
    	}
    }

    /**
     * 根据eids批量修改所属人展团
     * @param eids
     * @param group
     * @param adminId
     */
    @Transactional
    public void modifyExhibitorsGroup(Integer[] eids, Integer group, Integer adminId) {
    	if(eids != null){
    		List<TExhibitor> exhibitors = loadSelectedExhibitors(eids);
            if (exhibitors.size() > 0){
    			for(TExhibitor exhibitor:exhibitors){
            		exhibitor.setGroup(group);
            		exhibitor.setUpdateUser(adminId);
            		exhibitor.setUpdateTime(new Date());
            		getHibernateTemplate().update(exhibitor);
            	}
    		}
    	}
    }

    /**
     * 根据eids批量修改展区
     * @param eids
     * @param area
     * @param adminId
     */
    @Transactional
    public void modifyExhibitorsArea(Integer[] eids, Integer area, Integer adminId) {
    	if(eids != null){
    		List<TExhibitor> exhibitors = loadSelectedExhibitors(eids);
            if (exhibitors.size() > 0){
    			for(TExhibitor exhibitor:exhibitors){
            		exhibitor.setArea(area);
            		exhibitor.setUpdateUser(adminId);
            		exhibitor.setUpdateTime(new Date());
            		getHibernateTemplate().update(exhibitor);
            	}
    		}
    	}
    }

    /**
     * 根据eids批量注销展商
     * @param eids
     * @param adminId
     */
    @Transactional
    public void disableExhibitors(Integer[] eids, Integer adminId) {
    	if(eids != null){
            List<TExhibitor> exhibitors = loadSelectedExhibitors(eids);
//            List<TExhibitor> exhibitors = exhibitorDao.loadExhibitorsByEids(eids);
    		if(exhibitors.size() > 0){
    			for(TExhibitor exhibitor:exhibitors){
            		exhibitor.setIsLogout(1);
            		exhibitor.setUpdateUser(adminId);
            		exhibitor.setUpdateTime(new Date());
            		getHibernateTemplate().update(exhibitor);
            	}
    		}
    	}
    }

    /**
     * 根据eids批量启用展商
     * @param eids
     * @param adminId
     */
    @Transactional
    public void enableExhibitor(Integer[] eids, Integer adminId) {
    	if(eids != null){
            for (Integer eid:eids){
                TExhibitor exhibitor = exhibitorDao.query(eid);
                exhibitor.setIsLogout(0);
                exhibitor.setUpdateUser(adminId);
                exhibitor.setUpdateTime(new Date());
                exhibitor.setIsLogin(0);
                getHibernateTemplate().update(exhibitor);
            }
    	}
    }

    /**
     * 修改展商基本信息
     * @param request
     * @param eid
     * @param adminId
     * @throws Exception
     */
    @Transactional
    public void modifyExhibitorInfo(ModifyExhibitorInfoRequest request,Integer eid, Integer adminId) throws Exception {
    	if(eid != null){
    		TExhibitor exhibitor = loadExhibitorByEid(eid);
            /*exhibitor.setCompany(request.getCompany().trim());
            exhibitor.setCompanye(request.getCompanyEn().trim());
            exhibitor.setCompanyt(JChineseConvertor.getInstance().s2t(request.getCompany().trim()));*/
    		if(exhibitor != null){
    			TExhibitorInfo exhibitorInfo = new TExhibitorInfo();
    			if(request.getEinfoid() != null) {
    				//修改
    				exhibitorInfo = loadExhibitorInfoByEid(eid);
                    if(StringUtil.isNotEmpty(request.getCompany())){
                        exhibitorInfo.setCompany(request.getCompany().trim());
                        exhibitorInfo.setCompanyT(JChineseConvertor.getInstance().s2t(request.getCompany().trim()));
                    }
                    if(StringUtil.isNotEmpty(request.getCompanyEn())){
                        exhibitorInfo.setCompanyEn(request.getCompanyEn().trim());
                    }
                    exhibitorInfo.setCreateTime(new Date());
    				exhibitorInfo.setUpdateTime(new Date());
					if(adminId != null){
						exhibitorInfo.setAdminUser(adminId);
						exhibitorInfo.setAdminUpdateTime(new Date());
					}
    			}else{
    				//添加
    				exhibitorInfo.setEid(eid);
    				exhibitorInfo.setCreateTime(new Date());
    				if(adminId != null) exhibitorInfo.setAdminUser(adminId);
    			}
				//exhibitorInfo.setCompany(request.getCompany().trim());
				//exhibitorInfo.setCompanyEn(request.getCompanyEn().trim());
                //exhibitorInfo.setCompanyT(JChineseConvertor.getInstance().s2t(request.getCompany().trim()));
                if(StringUtil.isNotEmpty(request.getCompany())){
                    exhibitorInfo.setCompany(request.getCompany().trim());
                    exhibitorInfo.setCompanyT(JChineseConvertor.getInstance().s2t(request.getCompany().trim()));
                }
                if(StringUtil.isNotEmpty(request.getCompanyEn())){
                    exhibitorInfo.setCompanyEn(request.getCompanyEn().trim());
                }
    			exhibitorInfo.setPhone(request.getPhone());
				exhibitorInfo.setFax(request.getFax());
				exhibitorInfo.setEmail(request.getEmail());
				exhibitorInfo.setWebsite(request.getWebsite());
				exhibitorInfo.setAddress(request.getAddress());
				exhibitorInfo.setAddressEn(request.getAddressEn());
				exhibitorInfo.setZipcode(request.getZipcode());
				exhibitorInfo.setMainProduct(request.getMainProduct());
				exhibitorInfo.setMainProductEn(request.getMainProductEn());
				exhibitorInfo.setMark(request.getMark());
                exhibitorInfo.setMeipai(request.getMeipai());
                exhibitorInfo.setMeipaiEn(request.getMeipaiEn());
				if(StringUtils.isNotEmpty(request.getLogo())) exhibitorInfo.setLogo(request.getLogo());
				if(request.getEinfoid() != null) {
                    exhibitorInfoDao.update(exhibitorInfo);
                    exhibitorDao.update(exhibitor);
                }
				else exhibitorInfoDao.create(exhibitorInfo);
                modifyInvoice(request.getInvoiceTitle(),request.getInvoiceNo(),request.getEid());
                modifyProductType(request.getProductType(), request.getEinfoid(), adminId);
    		}
    	}
    }

    /**
     * 添加或修改楣牌
     * @param _meipai
     * @param _meipaiEn
     * @param eid
     * @param adminId
     */
    @Transactional
	public void modifyMeipai(String _meipai, String _meipaiEn, Integer eid, Integer adminId){
    	if(meipaiService.getMeiPaiByEid(eid) != null){
        	TExhibitorMeipai meipai = meipaiService.getMeiPaiByEid(eid);
        	meipai.setMeipai(_meipai);
        	meipai.setMeipaiEn(_meipaiEn);
        	if(adminId !=null ){
        		meipai.setUpdateAdmin(adminId);
        		meipai.setAdminUpdateTime(new Date());
        	}else{
        		meipai.setUpdateTime(new Date());
        	}
        	meipaiService.saveOrUpdate(meipai);
        }else{
        	TExhibitorMeipai meipai = new TExhibitorMeipai();
        	meipai.setMeipai(_meipai);
        	meipai.setMeipaiEn(_meipaiEn);
        	meipai.setExhibitorId(eid);
        	meipai.setCreateTime(new Date());
        	meipaiService.saveOrUpdate(meipai);
        }
    }

    /**
     * 添加修改产品类型
     * @param productTypes
     * @param einfoid
     * @param adminId
     * @throws Exception
     */
    @Transactional
    public void modifyProductType(String productTypes, Integer einfoid, Integer adminId) throws Exception{
		deleteExhibitorClassByEinfoId(einfoid);
		if(StringUtils.isNotEmpty(productTypes)){
			String[] string = productTypes.split(",");
			for(String str:string){
				TProductType productType = productManagerService.queryProductsTypeById(Integer.parseInt(str));
				TExhibitorClass exhibitorClass = new TExhibitorClass();
				exhibitorClass.setEinfoId(einfoid);
				exhibitorClass.setParentClassId(productType.getParentId());
				exhibitorClass.setClassId(Integer.parseInt(str));
				exhibitorClass.setCreateTime(new Date());
				if(adminId !=null){
					exhibitorClass.setAdmin(adminId);
				}
				exhibitorClassDao.create(exhibitorClass);
			}
		}
		/*
    	List<TProductType> productTypes2 = productManagerService.queryProductsTypeByLv(2);
    	for(TProductType productType2:productTypes2){
    		if(loadExhibitorClassByClassId(productType2.getId()) != null){
    			if(!checkString(productTypes,String.valueOf(productType2.getId()))){
    				TExhibitorClass delExhibitorClass = loadExhibitorClassByClassId(productType2.getId());
    				exhibitorClassDao.delete(delExhibitorClass);
    			}
    		}else{
    			if(checkString(productTypes,String.valueOf(productType2.getId()))){
    				TExhibitorClass exhibitorClass = new TExhibitorClass();
    				exhibitorClass.setEinfoId(einfoid);
    				exhibitorClass.setParentClassId(productManagerService.loadParentClassIdById(productType2.getId()));
    				exhibitorClass.setClassId(productType2.getId());
    				exhibitorClass.setCreateTime(new Date());
    				if(adminId !=null){
    					exhibitorClass.setAdmin(adminId);
    	        	}
    				exhibitorClassDao.create(exhibitorClass);
    			}
    		}
    	}*/
    }

    /**
     * 修改发票信息
     * @param invoiceTitle
     * @param invoiceNo
     * @param eid
     */
    @Transactional
    private void modifyInvoice(String invoiceTitle, String invoiceNo, Integer eid) {
    	TInvoiceApply invoice = new TInvoiceApply();
    	if(invoiceService.getByEid(eid) != null) {
    		invoice = invoiceService.getByEid(eid);
    		invoice.setTitle(invoiceTitle);
    		invoice.setInvoiceNo(invoiceNo);
    		if(queryBoothByEid(eid) != null) invoice.setExhibitorNo(queryBoothByEid(eid).getBoothNumber());
    		else invoice.setExhibitorNo("");
    	}else{
    		if(queryBoothByEid(eid) != null)
                invoice = new TInvoiceApply(queryBoothByEid(eid).getBoothNumber(), invoiceNo, invoiceTitle, eid, new Date());
    		else
                invoice = new TInvoiceApply("", invoiceNo, invoiceTitle, eid, new Date());
    	}
    	invoiceService.create(invoice);
	}

	private Boolean checkString(String strs,String check){
		if(StringUtils.isNotEmpty(strs)){
			String[] string = strs.split(",");
	    	for(String str:string){
	    		if(str.equals(check)) return true;
	    	}
	    	return false;
		}
		return false;
    }

	/**
     * 根据eids删除产品分类
     * @param eids
     */
    @Transactional
    public void deleteExhibitorClassByEids(Integer[] eids) {
    	List<Integer> einfoids = new ArrayList<Integer>();
    	int i = 0;
    	for(Integer eid:eids){
    		TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(eid);
    		if(exhibitorInfo != null){
    			einfoids.add(exhibitorInfo.getEinfoid());
        		i ++;
    		}
    	}
    	List<TExhibitorClass> exhibitorClasses = queryExhibitorClassByClassEinfoids(einfoids);
    	if(exhibitorClasses != null){
    		for(TExhibitorClass exhibitorClass:exhibitorClasses){
        		getHibernateTemplate().delete(exhibitorClass);
        	}
    	}
    }

	@Transactional
	public void deleteExhibitorClassByEinfoId(Integer einfoid) throws Exception {
		List<TExhibitorClass> exhibitorClasses = exhibitorClassDao.queryByHql("from TExhibitorClass where einfoId = ?",new Object[]{einfoid});
		for(TExhibitorClass exhibitorClass:exhibitorClasses){
			exhibitorClassDao.delete(exhibitorClass);
		}
	}

    /**
     * 根据eids删除展商
     * @param eids
     * @throws Exception
     */
    @Transactional
    public void deleteExhibitorByEids(Integer[] eids) throws Exception {
    	joinerManagerService.deleteJoinersByEids(eids);
    	contactService.deleteContactsByEids(eids);
    	termManagerService.deleteTermByEids(eids);
    	meipaiService.deleteMeiPaiByEid(eids);
    	deleteExhibitorClassByEids(eids);
    	productManagerService.deleteProductByEids(eids);
    	deleteExhibitorInfoByEid(eids);
    	deleteExhibitorBooth(eids);
    	for(Integer eid:eids){
        	TExhibitor exhibitor = loadExhibitorByEid(eid);
        	if(exhibitor != null){
        		getHibernateTemplate().delete(exhibitor);
        	}
    	}
    }

    /**
     * 根据eids删除展位号
     * @param eids
     * @throws Exception
     */
    @Transactional
	public void deleteExhibitorBooth(Integer[] eids) throws Exception {
		List<TExhibitorBooth> exhibitorBooths = exhibitorBoothDao.loadExhibitorBoothByEids(eids);
		if(exhibitorBooths != null){
			for(TExhibitorBooth exhibitorBooth:exhibitorBooths){
				if(exhibitorBooth != null) exhibitorBoothDao.delete(exhibitorBooth);
			}
		}
	}

    /**
     * 分页查询展位预向列表
     * @param request
     * @return
     */
    public QueryXicecMapIntetionResponse queryXicecMapIntetionByPage(QueryXicecMapIntetionRequest request) {
        List<String> conditions = new ArrayList<String>();
        try {
            if (StringUtils.isNotEmpty(request.getBooth_num())) {
                conditions.add(" (booth_num like '%" + request.getBooth_num() + "%' OR booth_num like '%" + new String(request.getBooth_num().getBytes("ISO-8859-1"),"GBK") + "%' OR booth_num like '%" + new String(request.getBooth_num().getBytes("ISO-8859-1"),"utf-8") + "%') ");
            }
            if (request.getTag() != null) {
                conditions.add(" tag = " + request.getTag().intValue() + " ");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlNoOrder = " where " + conditionsSql;
        }
        Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        List<TXicecMapIntetion> tXicecMapIntetionList = xicecMapDao.queryPageByHQL("select count(*) from TXicecMapIntetion " + conditionsSqlNoOrder,
                "select new com.zhenhappy.ems.manager.dto.xicecmap.QueryXicecMapIntetionInfo(id, booth_num, tag) from TXicecMapIntetion "  + conditionsSqlNoOrder, new Object[]{}, page);
        QueryXicecMapIntetionResponse response = new QueryXicecMapIntetionResponse();
        response.setResultCode(0);
        response.setRows(tXicecMapIntetionList);
        response.setTotal(page.getTotalCount());
        return response;
    }

    /**
     * 通过展位号判断当前是否被卖出去
     * @param boothNum
     * @return
     */
    @Transactional
    public boolean isSellOutByBoothNum(String boothNum){
        boolean flag = false;
        List<TExhibitorBooth> boothList = getHibernateTemplate().find("from TExhibitorBooth where boothNumber = ?", new Object[]{boothNum});
        for(TExhibitorBooth tExhibitorBooth: boothList){
            TExhibitor tExhibitor = exhibitorDao.query(tExhibitorBooth.getEid());
            if(tExhibitor.getIsLogout() == 0){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 备份展商数据
     * @return
     */
    @Transactional
    public void backupExhibitorData() {
        List<TExhibitor> tExhibitorList = loadAllExhibitorList();
        for(TExhibitor tExhibitor:tExhibitorList) {
            TExhibitorBackupInfo tExhibitorBackupInfo = new TExhibitorBackupInfo();
            tExhibitorBackupInfo.setEid(tExhibitor.getEid());
            tExhibitorBackupInfo.setUsername(tExhibitor.getUsername());
            tExhibitorBackupInfo.setPassword(tExhibitor.getPassword());
            tExhibitorBackupInfo.setLevel(tExhibitor.getLevel());
            tExhibitorBackupInfo.setArea(tExhibitor.getArea());
            tExhibitorBackupInfo.setLastLoginTime(tExhibitor.getLastLoginTime());
            tExhibitorBackupInfo.setLastLoginIp(tExhibitor.getLastLoginIp());
            tExhibitorBackupInfo.setIsLogout(tExhibitor.getIsLogout());
            tExhibitorBackupInfo.setCreateUser(tExhibitor.getCreateUser());
            tExhibitorBackupInfo.setCreateTime(tExhibitor.getCreateTime());
            tExhibitorBackupInfo.setUpdateUser(tExhibitor.getUpdateUser());
            tExhibitorBackupInfo.setUpdateTime(tExhibitor.getUpdateTime());
            tExhibitorBackupInfo.setTag(tExhibitor.getTag());
            tExhibitorBackupInfo.setProvince(tExhibitor.getProvince());
            tExhibitorBackupInfo.setCountry(tExhibitor.getCountry());
            tExhibitorBackupInfo.setGroup(tExhibitor.getGroup());
            tExhibitorBackupInfo.setContractId(tExhibitor.getContractId());
            tExhibitorBackupInfo.setExhibitionArea(tExhibitor.getExhibitionArea());
            tExhibitorBackupInfo.setExhibitor_type(tExhibitor.getExhibitor_type());
            tExhibitorBackupInfo.setIsLogin(tExhibitor.getIsLogin());
            tExhibitorBackupInfo.setSend_invitation_flag(tExhibitor.getSend_invitation_flag());

            TExhibitorInfo exhibitorInfo = loadExhibitorInfoByEid(tExhibitor.getEid());
            if(exhibitorInfo != null){
                tExhibitorBackupInfo.setExhibitor_info_einfoid(exhibitorInfo.getEinfoid());
                tExhibitorBackupInfo.setExhibitor_info_organization_code(exhibitorInfo.getOrganizationCode());
                tExhibitorBackupInfo.setExhibitor_info_company(exhibitorInfo.getCompany());
                tExhibitorBackupInfo.setExhibitor_info_company_en(exhibitorInfo.getCompanyEn());
                tExhibitorBackupInfo.setExhibitor_info_company_t(exhibitorInfo.getCompanyT());
                tExhibitorBackupInfo.setExhibitor_info_phone(exhibitorInfo.getPhone());
                tExhibitorBackupInfo.setExhibitor_info_fax(exhibitorInfo.getFax());
                tExhibitorBackupInfo.setExhibitor_info_email(exhibitorInfo.getEmail());
                tExhibitorBackupInfo.setExhibitor_info_website(exhibitorInfo.getWebsite());
                tExhibitorBackupInfo.setExhibitor_info_zipcode(exhibitorInfo.getZipcode());
                tExhibitorBackupInfo.setExhibitor_info_main_product(exhibitorInfo.getMainProduct());
                tExhibitorBackupInfo.setExhibitor_info_main_product_en(exhibitorInfo.getMainProductEn());
                tExhibitorBackupInfo.setExhibitor_info_logo(exhibitorInfo.getLogo());
                tExhibitorBackupInfo.setExhibitor_info_mark(exhibitorInfo.getMark());
                tExhibitorBackupInfo.setExhibitor_info_is_delete(exhibitorInfo.getIsDelete());
                tExhibitorBackupInfo.setExhibitor_info_create_time(exhibitorInfo.getCreateTime());
                tExhibitorBackupInfo.setExhibitor_info_update_time(exhibitorInfo.getUpdateTime());
                tExhibitorBackupInfo.setExhibitor_info_admin_user(exhibitorInfo.getAdminUser());
                tExhibitorBackupInfo.setExhibitor_info_admin_update_time(exhibitorInfo.getAdminUpdateTime());
                tExhibitorBackupInfo.setExhibitor_info_address(exhibitorInfo.getAddress());
                tExhibitorBackupInfo.setExhibitor_info_address_en(exhibitorInfo.getAddressEn());
                tExhibitorBackupInfo.setExhibitor_info_meipai(exhibitorInfo.getMeipai());
                tExhibitorBackupInfo.setExhibitor_info_meipai_en(exhibitorInfo.getMeipaiEn());
                tExhibitorBackupInfo.setExhibitor_info_emark(exhibitorInfo.getEmark());
                tExhibitorBackupInfo.setExhibitor_info_company_hignlight(exhibitorInfo.getCompany_hignlight());
                tExhibitorBackupInfo.setExhibitor_info_email_address(exhibitorInfo.getEmail_address());
                tExhibitorBackupInfo.setExhibitor_info_email_contact(exhibitorInfo.getEmail_contact());
                tExhibitorBackupInfo.setExhibitor_info_email_telphone(exhibitorInfo.getEmail_telphone());

                //获取产品列表
                List<TExhibitorClass> exhibitorClasses = queryExhibitorClassByEid(exhibitorInfo.getEinfoid());
                if(exhibitorClasses.size() != 0){
                    List<TProductType> productTypes1 = productManagerService.queryProductsTypeByLv(1);
                    List<TProductType> productTypes2 = productManagerService.queryProductsTypeByLv(2);
                    StringBuffer productBuffer = new StringBuffer();
                    for(TProductType productType1:productTypes1){
                        for(TProductType productType2:productTypes2){
                            if(productType1.getId().intValue() == productType2.getParentId().intValue()){
                                QueryProductType2Response p = new QueryProductType2Response(productType2.getId(),productType2.getClassName(),"icon-ok",false);
                                for(TExhibitorClass exhibitorClass:exhibitorClasses){
                                    if(productType1 != null && productType1.getId() != null && exhibitorClass != null
                                            && exhibitorClass.getParentClassId() != null && (productType1.getId().intValue() == exhibitorClass.getParentClassId())){
                                        if(p.getId().intValue() == exhibitorClass.getClassId()){
                                            productBuffer.append(p.getText() + "   ");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    tExhibitorBackupInfo.setExhibitor_product_type(productBuffer.toString());
                }
            }

            TExhibitorTerm tExhibitorTerm = getExhibitorTermByEid(tExhibitor.getEid());
            if(tExhibitorTerm != null){
                tExhibitorBackupInfo.setExhibitor_term_join_term(tExhibitorTerm.getJoinTerm());
                tExhibitorBackupInfo.setExhibitor_term_booth_number(tExhibitorTerm.getBoothNumber());
                tExhibitorBackupInfo.setExhibitor_term_mark(tExhibitorTerm.getMark());
                tExhibitorBackupInfo.setExhibitor_term_is_delete(tExhibitorTerm.getIsDelete());
                tExhibitorBackupInfo.setExhibitor_term_create_user(tExhibitorTerm.getCreateUser());
                tExhibitorBackupInfo.setExhibitor_term_create_time(tExhibitorTerm.getCreateTime());
                tExhibitorBackupInfo.setExhibitor_term_update_user(tExhibitorTerm.getUpdateUser());
                tExhibitorBackupInfo.setExhibitor_term_update_time(tExhibitorTerm.getUpdateTime());
            }

            TExhibitorBooth tExhibitorBooth = queryBoothByEid(tExhibitor.getEid());
            if(tExhibitorBooth != null){
                tExhibitorBackupInfo.setExhibitor_booth_booth_number(tExhibitorBooth.getBoothNumber());
                tExhibitorBackupInfo.setExhibitor_booth_exhibition_area(tExhibitorBooth.getExhibitionArea());
                tExhibitorBackupInfo.setExhibitor_booth_mark(tExhibitorBooth.getMark());
                tExhibitorBackupInfo.setExhibitor_booth_create_user(tExhibitorBooth.getCreateUser());
                tExhibitorBackupInfo.setExhibitor_booth_create_time(tExhibitorBooth.getCreateTime());
                tExhibitorBackupInfo.setExhibitor_booth_update_user(tExhibitorBooth.getUpdateUser());
                tExhibitorBackupInfo.setExhibitor_booth_update_time(tExhibitorBooth.getUpdateTime());
            }

            TInvoiceApplyExtend tInvoiceApplyExtend = getInvoiceApplyExtendByEid(tExhibitor.getEid());
            if(tInvoiceApplyExtend != null){
                tExhibitorBackupInfo.setExhibitor_invoice_apply_exhibitor_no(tInvoiceApplyExtend.getExhibitorNo());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_no(tInvoiceApplyExtend.getInvoiceNo());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_title(tInvoiceApplyExtend.getTitle());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_create_time(tInvoiceApplyExtend.getCreateTime());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_flag(tInvoiceApplyExtend.getInvoice_flag());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_company(tInvoiceApplyExtend.getInvoice_company());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_taxpayer_no(tInvoiceApplyExtend.getInvoice_taxpayer_no());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_bank_name(tInvoiceApplyExtend.getInvoice_bank_name());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_bank_account(tInvoiceApplyExtend.getInvoice_bank_account());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_company_address(tInvoiceApplyExtend.getInvoice_company_address());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_company_contact(tInvoiceApplyExtend.getInvoice_company_contact());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_company_tel(tInvoiceApplyExtend.getInvoice_company_tel());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_general_tax_type(tInvoiceApplyExtend.getInvoice_general_tax_type());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_general_taxpayer_flag(tInvoiceApplyExtend.getInvoice_general_taxpayer_flag());
                tExhibitorBackupInfo.setExhibitor_invoice_apply_invoice_image_address(tInvoiceApplyExtend.getInvoice_image_address());
            }
            tExhibitorBackupInfo.setExhibitor_backup_date(new Date());
            exhibitorBackupInfoDao.create(tExhibitorBackupInfo);

            //产品列表
            List<TProduct> productList = productManagerService.loadProductsByEinfoid(exhibitorInfo.getEinfoid());
            for(TProduct tProduct:productList){
                TProductBackupInfo tProductBackupInfo = new TProductBackupInfo();
                tProductBackupInfo.setExhibitor_info_backup_id(tExhibitorBackupInfo.getId());
                tProductBackupInfo.setProductName(tProduct.getProductName());
                tProductBackupInfo.setProductNameEn(tProduct.getProductNameEn());
                tProductBackupInfo.setProductModel(tProduct.getProductModel());
                tProductBackupInfo.setProductModelEn(tProduct.getProductModelEn());
                tProductBackupInfo.setOrigin(tProduct.getOrigin());
                tProductBackupInfo.setOriginEn(tProduct.getOriginEn());
                tProductBackupInfo.setKeyWords(tProduct.getKeyWords());
                tProductBackupInfo.setKeyWordsEn(tProduct.getKeyWordsEn());
                tProductBackupInfo.setDescription(tProduct.getDescription());
                tProductBackupInfo.setDescriptionEn(tProduct.getDescriptionEn());
                tProductBackupInfo.setProductBrand(tProduct.getProductBrand());
                tProductBackupInfo.setProductBrandEn(tProduct.getProductBrandEn());
                tProductBackupInfo.setProductSpec(tProduct.getProductSpec());
                tProductBackupInfo.setProductBrandEn(tProduct.getProductBrandEn());
                tProductBackupInfo.setProductCount(tProduct.getProductCount());
                tProductBackupInfo.setProductCountEn(tProduct.getProductCountEn());
                tProductBackupInfo.setPackageDescription(tProduct.getPackageDescription());
                tProductBackupInfo.setPackageDescriptionEn(tProduct.getPackageDescriptionEn());
                tProductBackupInfo.setPriceDescription(tProduct.getPriceDescription());
                tProductBackupInfo.setPriceDescriptionEn(tProduct.getPriceDescriptionEn());
                tProductBackupInfo.setFlag(tProduct.getFlag());
                tProductBackupInfo.setCreateTime(tProduct.getCreateTime());
                tProductBackupInfo.setUpdateTime(tProduct.getUpdateTime());
                tProductBackupInfo.setAdmin(tProduct.getAdmin());
                tProductBackupInfo.setAdminUpdateTime(tProduct.getAdminUpdateTime());
                productBackupInfoDao.create(tProductBackupInfo);
            }

            //联系人列表
            List<TContact> contactList = contactService.loadContactByEid(exhibitorInfo.getEid());
            for(TContact tContact:contactList){
                TContactBackupInfo tContactBackupInfo = new TContactBackupInfo();
                tContactBackupInfo.setExhibitor_info_backup_id(tExhibitorBackupInfo.getId());
                tContactBackupInfo.setName(tContact.getName());
                tContactBackupInfo.setPosition(tContact.getPosition());
                tContactBackupInfo.setPhone(tContact.getPhone());
                tContactBackupInfo.setEmail(tContact.getEmail());
                tContactBackupInfo.setAddress(tContact.getAddress());
                tContactBackupInfo.setExpressNumber(tContact.getExpressNumber());
                tContactBackupInfo.setEid(tContact.getEid());
                tContactBackupInfo.setIsDelete(tContact.getIsDelete());
                contactBackupInfoDao.create(tContactBackupInfo);
            }

            //参展人员列表
            List<TExhibitorJoiner> tExhibitorJoinerList = joinerManagerService.loadExhibitorJoinerByEid(exhibitorInfo.getEid());
            for(TExhibitorJoiner tExhibitorJoiner:tExhibitorJoinerList){
                TExhibitorJoinerBackupInfo tExhibitorJoinerBackupInfo = new TExhibitorJoinerBackupInfo();
                tExhibitorJoinerBackupInfo.setExhibitor_info_backup_id(tExhibitorBackupInfo.getId());
                tExhibitorJoinerBackupInfo.setName(tExhibitorJoiner.getName());
                tExhibitorJoinerBackupInfo.setPosition(tExhibitorJoiner.getPosition());
                tExhibitorJoinerBackupInfo.setTelphone(tExhibitorJoiner.getTelphone());
                tExhibitorJoinerBackupInfo.setEmail(tExhibitorJoiner.getEmail());
                tExhibitorJoinerBackupInfo.setIsDelete(tExhibitorJoiner.getIsDelete());
                tExhibitorJoinerBackupInfo.setCreateTime(tExhibitorJoiner.getCreateTime());
                tExhibitorJoinerBackupInfo.setAdmin(tExhibitorJoiner.getAdmin());
                tExhibitorJoinerBackupInfo.setAdminUpdateTime(tExhibitorJoiner.getAdminUpdateTime());
                tExhibitorJoinerBackupInfo.setIsNew(tExhibitorJoiner.getIsNew());
                exhibitorJoinerBackupInfoDao.create(tExhibitorJoinerBackupInfo);

                //对应的VISA列表
                TVisa tVisa = tVisaManagerService.loadTVisaListByJoinerid(tExhibitorJoiner.getId());
                if(tVisa != null){
                    TVisaBackupInfo tVisaBackupInfo = new TVisaBackupInfo();
                    tVisaBackupInfo.setJoinerId(tExhibitorJoiner.getId());
                    tVisaBackupInfo.setExhibitor_info_backup_id(tExhibitorBackupInfo.getId());
                    tVisaBackupInfo.setPassportName(tVisa.getPassportName());
                    tVisaBackupInfo.setGender(tVisa.getGender());
                    tVisaBackupInfo.setNationality(tVisa.getNationality());
                    tVisaBackupInfo.setJobTitle(tVisa.getJobTitle());
                    tVisaBackupInfo.setCompanyName(tVisa.getCompanyName());
                    tVisaBackupInfo.setBoothNo(tVisa.getBoothNo());
                    tVisaBackupInfo.setDetailedAddress(tVisa.getDetailedAddress());
                    tVisaBackupInfo.setTel(tVisa.getTel());
                    tVisaBackupInfo.setFax(tVisa.getFax());
                    tVisaBackupInfo.setEmail(tVisa.getEmail());
                    tVisaBackupInfo.setCompanyWebsite(tVisa.getCompanyWebsite());
                    tVisaBackupInfo.setPassportNo(tVisa.getPassportNo());
                    tVisaBackupInfo.setExpDate(tVisa.getExpDate());
                    tVisaBackupInfo.setBirth(tVisa.getBirth());
                    tVisaBackupInfo.setApplyFor(tVisa.getApplyFor());
                    tVisaBackupInfo.setFrom(tVisa.getFrom());
                    tVisaBackupInfo.setTo(tVisa.getTo());
                    tVisaBackupInfo.setPassportPage(tVisa.getPassportPage());
                    tVisaBackupInfo.setBusinessLicense(tVisa.getBusinessLicense());
                    tVisaBackupInfo.setStatus(tVisa.getStatus());
                    tVisaBackupInfo.setCreateTime(tVisa.getCreateTime());
                    tVisaBackupInfo.setUpdateTime(tVisa.getUpdateTime());
                    tVisaBackupInfo.setAddress(tVisa.getAddress());
                    visaBackupInfoDao.create(tVisaBackupInfo);
                }
            }
        }
    }

    /**
     * 分页获取备份展商数据列表
     * @param request
     * @return
     */
    public QueryExhibitorResponse queryAllExhibitorBackupInfosByPage(QueryExhibitorBackupRequest request) throws UnsupportedEncodingException {
        List<String> conditions = new ArrayList<String>();
        if (request.getTag() != null) {
            conditions.add(" e.tag = " + request.getTag().intValue() + " ");
        }
        if (request.getGroup() != null) {
            conditions.add(" e.group = " + request.getGroup().intValue() + " ");
        }
        if (StringUtils.isNotEmpty(request.getBoothNumber())) {
            conditions.add(" (e.exhibitor_booth_booth_number like '%" + request.getBoothNumber() + "%' OR e.exhibitor_booth_booth_number like '%" + new String(request.getBoothNumber().getBytes("ISO-8859-1"),"GBK") + "%' OR e.exhibitor_booth_booth_number like '%" + new String(request.getBoothNumber().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (StringUtils.isNotEmpty(request.getCompany())) {
            conditions.add(" (e.exhibitor_info_company like '%" + request.getCompany() + "%' OR e.exhibitor_info_company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"GBK") + "%' OR e.exhibitor_info_company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (StringUtils.isNotEmpty(request.getCompanye())) {
            conditions.add(" (e.exhibitor_info_company_en like '%" + request.getCompanye() + "%' OR e.exhibitor_info_company_en like '%" + new String(request.getCompanye().getBytes("ISO-8859-1"),"GBK") + "%' OR e.exhibitor_info_company_en like '%" + new String(request.getCompanye().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (StringUtils.isNotEmpty(request.getContractId())) {
            conditions.add(" (e.contractId like '%" + request.getContractId() + "%' OR e.contractId like '%" + new String(request.getContractId().getBytes("ISO-8859-1"),"GBK") + "%' OR e.contractId like '%" + new String(request.getContractId().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        if (request.getArea() != null) {
            conditions.add(" e.area = " + request.getArea().intValue() + " ");
        }
        if (request.getCountry() != null) {
            conditions.add(" e.country = " + request.getCountry().intValue() + " ");
        }
        if (request.getProvince() != null) {
            conditions.add(" e.province = " + request.getProvince().intValue() + " ");
        }
        if (request.getIsLogout() != null) {
            conditions.add(" e.isLogout = " + request.getIsLogout().intValue() + " ");
        }

        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlNoOrder = " where " + conditionsSql;
        }

        String conditionsSqlOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlOrder = " where " + conditionsSql + " order by e.updateTime ASC ";
        }else{
            conditionsSqlOrder = " order by e.updateTime ASC ";
        }

        Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        List<QueryExhibitorBackup> tExhibitorBackupInfoList = exhibitorDao.queryPageByHQL("select count(*) from TExhibitorBackupInfo e " + conditionsSqlNoOrder,
                "select new com.zhenhappy.ems.manager.dto.backupinfo.QueryExhibitorBackup(e.id, e.eid, e.username, e.password, e.area, e.exhibitor_info_company, e.exhibitor_info_company_en," +
                        " e.country, e.province, e.isLogout, e.isLogin, e.tag, e.group, e.exhibitor_booth_booth_number, e.exhibitionArea, e.contractId, e.updateTime) "
                        + "from TExhibitorBackupInfo e " + conditionsSqlOrder, new Object[]{}, page);
        /*for(QueryExhibitorBackup tExhibitorBackupInfo:tExhibitorBackupInfoList){
            tExhibitorBackupInfo.setInfoFlag(selectColor(tExhibitorBackupInfo, exhibitorInfo));
            if(tExhibitorBackupInfo.getIsLogout() == 0 && null != tExhibitorBackupInfo.getIsLogin() && tExhibitorBackupInfo.getIsLogin() == 0){
                tExhibitorBackupInfo.setInfoFlag(5);
            }
        }*/
        QueryExhibitorResponse response = new QueryExhibitorResponse();
        response.setResultCode(0);
        response.setRows(tExhibitorBackupInfoList);
        response.setTotal(page.getTotalCount());
        return response;
    }

    /**
     * 根据id查询展商备份数据
     * @param id
     * @return
     */
    @Transactional
    public TExhibitorBackupInfo loadExhibitorBackupInfoById(Integer id) {
        return exhibitorBackupInfoDao.query(id);
    }

    /**
     * 根据id查询产品备份数据
     * @param id
     * @return
     */
    @Transactional
    public List<TProductBackupInfo> loadProductBackupInfoById(Integer id) {
        return productBackupInfoDao.queryByHql("from TProductBackupInfo where exhibitor_info_backup_id=?", new Object[]{id});
    }

    /**
     * 根据id查询联系人备份数据
     * @param id
     * @return
     */
    @Transactional
    public List<TContactBackupInfo> loadContactBackupInfoById(Integer id) {
        return contactBackupInfoDao.queryByHql("from TContactBackupInfo where exhibitor_info_backup_id=?", new Object[]{id});
    }

    /**
     * 根据id查询参展人员备份数据
     * @param id
     * @return
     */
    @Transactional
    public List<TExhibitorJoinerBackupInfo> loadExhibitorJoinerBackupInfoById(Integer id) {
        return exhibitorJoinerBackupInfoDao.queryByHql("from TExhibitorJoinerBackupInfo where exhibitor_info_backup_id=?", new Object[]{id});
    }

    /**
     * 根据id查询参展人员备份数据
     * @param joinerId
     * @return
     */
    @Transactional
    public TExhibitorJoinerBackupInfo loadExhibitorJoinerBackupInfoByJoinerId(Integer joinerId) {
        List<TExhibitorJoinerBackupInfo> tExhibitorJoinerBackupInfoList = exhibitorJoinerBackupInfoDao.queryByHql("from TExhibitorJoinerBackupInfo where id=?", new Object[]{joinerId});
        return tExhibitorJoinerBackupInfoList.size()>0?tExhibitorJoinerBackupInfoList.get(0):null;
    }

    /**
     * 根据id查询参展人员备份数据
     * @param id
     * @return
     */
    @Transactional
    public List<TVisaBackupInfo> loadExhibitorVisaBackupInfoByExhibitorInfoBackupId(Integer id) {
        return visaBackupInfoDao.queryByHql("from TVisaBackupInfo where exhibitor_info_backup_id=?", new Object[]{id});
    }

    /**
     * 根据id查询参展人员备份数据
     * @param id
     * @return
     */
    @Transactional
    public TVisaBackupInfo loadExhibitorVisaBackupInfoById(Integer id) {
        List<TVisaBackupInfo> tVisaBackupInfoList = visaBackupInfoDao.queryByHql("from TVisaBackupInfo where id=?", new Object[]{id});
        return tVisaBackupInfoList.size() > 0 ? tVisaBackupInfoList.get(0) : null;
    }
}
