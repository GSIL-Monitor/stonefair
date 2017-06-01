package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.dao.WVisaDao;
import com.zhenhappy.ems.entity.TArticle;
import com.zhenhappy.ems.entity.WVisa;
import com.zhenhappy.ems.manager.dto.QueryWVisaRequest;
import com.zhenhappy.ems.manager.dto.QueryWVisaResponse;
import com.zhenhappy.ems.service.WVisaService;
import com.zhenhappy.util.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujianbin on 2014-07-03.
 */
@Service
public class WVisaManagerService extends WVisaService {
	@Autowired
	private WVisaDao wVisaDao;

	@Transactional
	public QueryWVisaResponse queryWVisaByPage(QueryWVisaRequest request) throws UnsupportedEncodingException {
		List<String> conditions = new ArrayList<String>();
		if (StringUtils.isNotEmpty(request.getFullPassportName())) {
			conditions.add(" (fullPassportName like '%" + request.getFullPassportName() + "%' OR fullPassportName like '%" + new String(request.getFullPassportName().getBytes("ISO-8859-1"),"GBK") + "%' OR fullPassportName like '%" + new String(request.getFullPassportName().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (StringUtils.isNotEmpty(request.getNationality())) {
			conditions.add(" (nationality like '%" + request.getNationality() + "%' OR nationality like '%" + new String(request.getNationality().getBytes("ISO-8859-1"),"GBK") + "%' OR nationality like '%" + new String(request.getNationality().getBytes("ISO-8859-1"),"utf-8") + "%') ");
		}
		if (request.getUpdateTime() != null) {
			conditions.add(" (updateTime like '%" + request.getUpdateTime() + "%");
		}
		if (request.getcreateTime() != null) {
			conditions.add(" (createTime like '%" + request.getcreateTime() + "%");
		}
		String conditionsSql = StringUtils.join(conditions, " and ");
		String conditionsSqlNoOrder = "";
		if(StringUtils.isNotEmpty(conditionsSql)){
			conditionsSqlNoOrder = " where " + conditionsSql;
		}

	    Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        List<WVisa> wVisas = wVisaDao.queryPageByHQL("select count(*) from WVisa "  + conditionsSqlNoOrder, "from WVisa "  + conditionsSqlNoOrder + " order by updateTime DESC, id DESC", new Object[]{}, page);
		QueryWVisaResponse response = new QueryWVisaResponse();
        response.setResultCode(0);
        response.setRows(wVisas);
        response.setTotal(page.getTotalCount());
        return response;
	}

	/**
	 * 查询所有VISA
	 * @return
	 */
	@Transactional
	public List<WVisa> loadAllWVisas() {
		List<WVisa> wVisas = wVisaDao.queryByHql("from WVisa order by updateTime DESC, id DESC", new Object[]{ });
		return wVisas;
	}

	/**
	 * 根据vids查询展商列表
	 * @return
	 */
	@Transactional
	public List<WVisa> loadSelectedWVisas(Integer[] vids) {
		List<WVisa> wVisas = null;
		wVisas = wVisaDao.loadWVisasByVids(vids);
		return wVisas.size() > 0 ? wVisas : null;
	}

	/**
	 * 根据vids查询展商列表
	 * @return
	 */
	@Transactional
	public WVisa getWVisaById(Integer id) {
		WVisa visa = wVisaDao.query(id);
		return visa;
	}

	/**
	 * 根据vids查询展商列表
	 * @return
	 */
	@Transactional
	public WVisa getWVisaByCustomerId(Integer customerId) {
		List<WVisa> wVisaList = wVisaDao.queryByHql("from WVisa where WCustomerInfo=? ", new Object[]{customerId});
		return wVisaList.size() > 0 ? wVisaList.get(0) : null;
	}
}
