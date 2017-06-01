package com.zhenhappy.ems.dao.managerrole.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.dao.managerrole.TUserOperationDao;
import com.zhenhappy.ems.entity.managerrole.TUserOperation;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
@Repository
public class TUserOperationDaoImp extends BaseDaoHibernateImp<TUserOperation> implements TUserOperationDao {
	@Override
	public List<TUserOperation> loadUserOperationByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TUserOperation(c.operationId, c.operationName, c.menuId, c.menuName) from TUserOperation c where c.operationId in (:ids)");
		q.setParameterList("ids", ids);
		return q.list();
	}
}
