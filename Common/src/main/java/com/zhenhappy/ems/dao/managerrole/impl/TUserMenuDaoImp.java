package com.zhenhappy.ems.dao.managerrole.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.dao.managerrole.TUserMenuDao;
import com.zhenhappy.ems.entity.managerrole.TUserMenu;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
@Repository
public class TUserMenuDaoImp extends BaseDaoHibernateImp<TUserMenu> implements TUserMenuDao {

	@Override
	public List<TUserMenu> loadUserMenuByMenuids(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TUserMenu(c.menuId, c.menuName, c.menuUrl, c.parentId, c.menuDescription, c.state, c.iconCls, c.seq) from TUserMenu c where c.menuId in (:ids)");
		q.setParameterList("ids", ids);
		return q.list();
	}
}
