package com.zhenhappy.ems.dao;

import java.util.List;

import com.zhenhappy.ems.entity.TExhibitorJoiner;
import com.zhenhappy.ems.entity.TExhibitorJoinerEx;

/**
 * Created by wujianbin on 2014-04-24.
 */
public interface JoinerDao extends BaseDao<TExhibitorJoiner> {
	public List<TExhibitorJoiner> loadJoinersByJIds(Integer[] jids);
	public List<TExhibitorJoiner> loadJoinersByEids(Integer[] eids);
	public List<TExhibitorJoiner> loadJoinersByIds(Integer[] ids);
}
