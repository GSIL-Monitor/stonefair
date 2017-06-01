package com.zhenhappy.ems.dao.managerrole;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.entity.managerrole.TUserMenu;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
public interface TUserMenuDao extends BaseDao<TUserMenu> {
    public List<TUserMenu> loadUserMenuByMenuids(Integer[] ids);
}
