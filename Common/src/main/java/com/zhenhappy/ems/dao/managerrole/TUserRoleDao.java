package com.zhenhappy.ems.dao.managerrole;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.entity.managerrole.TUserRole;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
public interface TUserRoleDao extends BaseDao<TUserRole> {
    public List<TUserRole> loadUserRoleByRoleids(Integer[] roleids);
}
