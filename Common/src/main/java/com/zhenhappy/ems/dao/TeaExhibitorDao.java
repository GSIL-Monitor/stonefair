package com.zhenhappy.ems.dao;

import com.zhenhappy.ems.entity.TeaExhibitor;

import java.util.List;

/**
 * Created by wangxd on 2016-07-20.
 */
public interface TeaExhibitorDao extends BaseDao<TeaExhibitor> {
	public List<TeaExhibitor> loadAllExhibitorsByLogType(Integer type);
}
