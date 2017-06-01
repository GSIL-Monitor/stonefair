package com.zhenhappy.ems.dao;

import com.zhenhappy.ems.entity.TTeaVisitorInfo;

import java.util.List;

/**
 * Created by wangxd on 2017-01-04.
 */
public interface TeaCustomerInfoDao extends BaseDao<TTeaVisitorInfo> {
	public List<TTeaVisitorInfo> loadTeaCustomersByIds(Integer[] ids);
}
