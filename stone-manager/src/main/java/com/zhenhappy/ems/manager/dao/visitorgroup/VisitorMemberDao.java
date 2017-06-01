package com.zhenhappy.ems.manager.dao.visitorgroup;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorMemberInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-26.
 */
public interface VisitorMemberDao extends BaseDao<TVisitorMemberInfo>{
    public List<TVisitorMemberInfo> loadVisitorMemberListById(Integer[] ids);
}
