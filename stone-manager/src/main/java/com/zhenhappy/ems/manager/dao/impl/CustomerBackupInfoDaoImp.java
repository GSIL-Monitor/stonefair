package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.backupinfo.CustomerBackupInfoDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TVisitorBackupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
@Repository
public class CustomerBackupInfoDaoImp extends BaseDaoHibernateImp<TVisitorBackupInfo> implements CustomerBackupInfoDao {

	@Override
	public List<TVisitorBackupInfo> loadCustomerBackupInfoByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TVisitorBackupInfo(a.address, a.backup_date, a.backupEmail, a.checkingNo,a.city, " +
				"a.company, a.country, a.createdIp,a.createdTime, a.email, a.fax, a.faxCode2, a.faxCode,a.firstName, a.guid, a.id, a.industryOthers," +
				"a.isActivated, a.isDisabled, a.isjudged, a.isMobile,a.isProfessional, a.isReaded, a.lastLoginIP,a.lastLoginTime, a.lastName, " +
				"a.mobilePhone,a.mobilePhoneCode, a.password, a.position, a.province,a.remark, a.selIndustry, a.sendEmailDate, a.sendEmailNum," +
				"a.sendMsgDate, a.sendMsgNum,a.sex, a.survey_CreateIP,a.survey_CreateTime, a.survey_EmailSubject, a.survey_InviterEmail," +
				"a.survey_InviterName, a.survey_IsDisabled, a.survey_Q10,a.survey_Q1, a.survey_Q2, a.survey_Q3, a.survey_Q4,a.survey_Q5,a.survey_Q6," +
				"a.survey_Q7, a.survey_Q8,a.survey_Q9, a.survey_Remark1, a.survey_Remark2,a.survey_UpdateIP, a.survey_UpdateTime, a.survey_WSC," +
				"a.telephone, a.telephoneCode2, a.telephoneCode,a. updatedIp, a.updateTime, a.website, a.year_CreateIP,a.year_CreateTime, " +
				"a.year_UpdateIP, a.year_UpdateTime,a.year_WSC, a.year_WThInfoID) from TVisitorBackupInfo a where a.id in (:ids) order by a.updateTime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
