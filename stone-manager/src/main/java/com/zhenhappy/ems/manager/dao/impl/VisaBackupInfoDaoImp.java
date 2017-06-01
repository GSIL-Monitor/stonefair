package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.backupinfo.VisaBackupInfoDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TVisaBackupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
@Repository
public class VisaBackupInfoDaoImp extends BaseDaoHibernateImp<TVisaBackupInfo> implements VisaBackupInfoDao {

	@Override
	public List<TVisaBackupInfo> loadVisaBackupInfoByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TVisaBackupInfo(a.address, a.applyFor, a.birth, a.boothNo, a.businessLicense, " +
				"a.companyName, a.companyWebsite,a.createTime, a.detailedAddress, a.email, a.exhibitor_info_backup_id, a.expDate, a.fax, " +
				"a.from, a.gender, a.id, a.jobTitle, a.joinerId, a.nationality, a.passportName, a.passportNo, a.passportPage, " +
				"a.status, a.tel, a.to, a.updateTime) from TVisaBackupInfo a where a.id in (:ids) ");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
