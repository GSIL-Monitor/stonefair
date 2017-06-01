package com.zhenhappy.ems.manager.dao.companyinfo;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.entity.WCustomer;
import com.zhenhappy.ems.manager.entity.companyinfo.THistoryCustomer;

import java.util.List;

/**
 * Created by wangxd on 2016-05-24.
 */
public interface HistoryCustomerInfoDao extends BaseDao<THistoryCustomer> {
	public List<THistoryCustomer> loadSelectedHistoryCustomers(Integer[] ids);
	public List<THistoryCustomer> loadSelectedHistoryInlandCustomers(Integer[] ids);
	public List<THistoryCustomer> loadSelectedHistoryForeignCustomers(Integer[] ids);

	public List<THistoryCustomer> loadSelectedHistoryInlandCustomersByOwner(Integer[] ownerIds);
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByOwner(Integer[] ownerIds);
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByCountryIdAndOwner(Integer countryId, Integer[] ownerIds);

	public List<THistoryCustomer> loadAllHistoryForeignCustomersByEmail(String[] emails);
}
