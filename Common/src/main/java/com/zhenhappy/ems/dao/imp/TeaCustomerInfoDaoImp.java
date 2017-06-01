package com.zhenhappy.ems.dao.imp;

import com.zhenhappy.ems.dao.TeaCustomerInfoDao;
import com.zhenhappy.ems.entity.TTeaVisitorInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2017-01-04.
 */
@Repository
public class TeaCustomerInfoDaoImp extends BaseDaoHibernateImp<TTeaVisitorInfo> implements TeaCustomerInfoDao {

	@Override
	public List<TTeaVisitorInfo> loadTeaCustomersByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TTeaVisitorInfo(a.address, a.backupEmail, a.beenToFair, a.beenToRole, a.checkingNo, " +
				"a.city, a.company, a.country, a.createIp, a.createTime, a.customer_type, a.email, a.fax, a.faxCode2, " +
				"a.faxCode, a.firstName, a.govement, a.guid, a.id, a.industry, a.industryOther, a.isAccommodation, a.isDisabled, " +
				"a.isjudged, a.isMobile, a.isProfessional, a.isReaded, a.isRecieveDoc, a.isRecieveEmail, a.langFlag, a.lastName, " +
				"a.mobileCode, a.mobilePhone, a.password, a.position, a.province, a.rabbi, a.remark, a.sendEmailNum, a.sendEmailTime, " +
				"a.sendMsgNum, a.sendMsgTime, a.sex, a.tel, a.telCode2, a.telCode, a.tmp_Country, a.tmp_Interest, a.tmp_InterestOthers, " +
				"a.tmp_Knowfrom, a.tmp_KnowfromOthers, a.tmp_Postcode, a.tmp_V_contact1, a.tmp_V_contact2, a.tmp_V_contact3, " +
				"a.tmp_V_name1, a.tmp_V_name2, a.tmp_V_name3, a.tmp_V_position1, a.tmp_V_position2, a.tmp_V_position3, a.tmp_V_title1, " +
				"a.tmp_V_title2, a.tmp_V_title3, a.updateIp, a.updateTime, a.visitDate, a.website) " +
				"from TTeaVisitorInfo a where a.id in (:ids) order by a.updateTime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
