package com.zhenhappy.ems.dao;

import java.util.List;

import com.zhenhappy.ems.entity.TExhibitor;
import com.zhenhappy.ems.entity.TeaExhibitor;

/**
 * Created by wujianbin on 2014-04-03.
 */
public interface ExhibitorDao extends BaseDao<TExhibitor> {
	public List<TExhibitor> loadExhibitorsByEids(Integer[] eids);
	public List<TExhibitor> loadExhibitorsByEidsAndFlag(Integer[] eids, int flag);
	public List<TExhibitor> loadAllExhibitorsByTagAndRole(int tag, Integer type);
	public List<TExhibitor> loadAllExhibitorsByType(Integer type);
	public List<TExhibitor> loadAllExhibitorsByLogType(Integer type);
}
