package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.backupinfo.ContactBackupInfoDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TContactBackupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
@Repository
public class ContactBackupInfoDaoImp extends BaseDaoHibernateImp<TContactBackupInfo> implements ContactBackupInfoDao {

	@Override
	public List<TContactBackupInfo> loadContactBackupInfoByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TContactBackupInfo(a.address, a.eid, a.email, a.exhibitor_info_backup_id, a.expressNumber, " +
				"a.id, a.isDelete, a.name, a.phone, a.position) from TContactBackupInfo a where a.id in (:ids) ");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
