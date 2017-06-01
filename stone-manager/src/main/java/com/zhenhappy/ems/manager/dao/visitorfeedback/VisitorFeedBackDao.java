package com.zhenhappy.ems.manager.dao.visitorfeedback;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBack;

import java.util.List;

/**
 * Created by wangxd on 2017-4-16.
 */
public interface VisitorFeedBackDao extends BaseDao<TVisitorFeedBack>{

    public List<TVisitorFeedBack> loadForeignFeedBackDataByIds(Integer[] ids);
}
