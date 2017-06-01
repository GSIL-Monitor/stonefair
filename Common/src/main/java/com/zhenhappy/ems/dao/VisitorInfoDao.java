package com.zhenhappy.ems.dao;

import com.zhenhappy.ems.entity.TVisitorInfo;
import com.zhenhappy.ems.entity.WCustomer;

import java.util.List;

/**
 * Created by wujianbin on 2014-07-02.
 */
public interface VisitorInfoDao extends BaseDao<TVisitorInfo> {
    public List<TVisitorInfo> loadCustomersByIds(Integer[] ids, int rabbiFlag);
    public List<TVisitorInfo> loadInlandRabbiCustomers();
    public List<TVisitorInfo> loadForeignRabbiCustomers();
}
