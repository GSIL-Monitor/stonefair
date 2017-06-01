package com.zhenhappy.ems.dao;

import com.zhenhappy.ems.entity.TVisitorTemplate;

import java.util.List;

/**
 * Created by wujianbin on 2014-07-02.
 */
public interface TVisitorTemplateDao extends BaseDao<TVisitorTemplate> {

	List<TVisitorTemplate> loadWCustomerTemplate(Integer[] cids);
}
