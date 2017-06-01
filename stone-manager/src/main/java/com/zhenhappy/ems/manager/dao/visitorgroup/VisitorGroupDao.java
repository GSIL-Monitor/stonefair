package com.zhenhappy.ems.manager.dao.visitorgroup;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorGroupInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-26.
 */
public interface VisitorGroupDao extends BaseDao<TVisitorGroupInfo>{
    public List<TVisitorGroupInfo> loadCustomerGroupByIds(Integer[] ids);
}
