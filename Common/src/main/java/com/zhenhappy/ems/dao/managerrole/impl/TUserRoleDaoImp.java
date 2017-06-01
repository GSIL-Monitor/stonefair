package com.zhenhappy.ems.dao.managerrole.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.dao.managerrole.TUserRoleDao;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
@Repository
public class TUserRoleDaoImp extends BaseDaoHibernateImp<TUserRole> implements TUserRoleDao {

	@Override
	public List<TUserRole> loadUserRoleByRoleids(Integer[] roleids) {
		Query q = this.getSession().createQuery("select new TUserRole(c.roleId, c.roleName, c.menuIds, c.operationIds, c.roleDescription) from TUserRole c where c.roleId in (:roleids)");
		q.setParameterList("roleids", roleids);
		return q.list();
	}
}
