package com.zhenhappy.ems.dao.managerrole.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.dao.managerrole.TUserInfoDao;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
@Repository
public class TUserInfoDaoImp extends BaseDaoHibernateImp<TUserInfo> implements TUserInfoDao {
	public List<TUserInfo> loadUserInfoByRoleids(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TUserInfo(c.id, c.name, c.userName, c.password, c.userType, c.roleId, c.userDescription) from TUserInfo c where c.id in (:ids)");
		q.setParameterList("ids", ids);
		return q.list();
	}
}
