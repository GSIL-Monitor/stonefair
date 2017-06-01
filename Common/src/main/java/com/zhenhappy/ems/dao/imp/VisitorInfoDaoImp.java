package com.zhenhappy.ems.dao.imp;

import com.zhenhappy.ems.dao.VisitorInfoDao;
import com.zhenhappy.ems.entity.TVisitorInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by wujianbin on 2014-08-11.
 */
@Repository
public class VisitorInfoDaoImp extends BaseDaoHibernateImp<TVisitorInfo> implements VisitorInfoDao {

    @Override
    public List<TVisitorInfo> loadCustomersByIds(Integer[] ids, int rabbiFlag) {
        Query q = this.getSession().createQuery("select new TVisitorInfo(a.id, a.email, a.checkingNo, a.password, a.firstName, a.lastName, a.sex," +
                "a.company, a.position, a.country, a.province, a.city, a.address, a.backupEmail, a.mobileCode, a.mobilePhone, a.telCode," +
                "a.tel, a.telCode2, a.faxCode, a.fax, a.faxCode2, a.website, a.industry,a.industryOther, a.remark, a.createIp, a.createTime, a.updateIp," +
                "a.updateTime, a.sendEmailNum,a.sendEmailTime, a.sendMsgNum,a.sendMsgTime, a.langFlag,a.visitDate,a.beenToFair,a.beenToRole,a.isRecieveEmail," +
                "a.isRecieveDoc,a.isMobile,a.isjudged,a.isProfessional,a.isAccommodation, a.isDisabled, a.isReaded, a.tmp_Country,a.tmp_Postcode,a.tmp_Interest,a.tmp_InterestOthers," +
                "a.tmp_Knowfrom,a.tmp_KnowfromOthers,a.tmp_V_name1,a.tmp_V_title1, a.tmp_V_position1,a.tmp_V_contact1,a.tmp_V_name2,a.tmp_V_title2," +
                "a.tmp_V_position2,a.tmp_V_contact2,a.tmp_V_name3,a.tmp_V_title3, a.tmp_V_position3,a.tmp_V_contact3,a.guid,a.govement,a.rabbi,a.customer_type) from TVisitorInfo a " +
                "where a.id in (:ids) and (a.rabbi " + (rabbiFlag==1?"= 1" :"=0 or a.rabbi is null") + ")");
        q.setParameterList("ids", ids);
        return q.list();
    }

    @Override
    public List<TVisitorInfo> loadInlandRabbiCustomers() {
        Query q = this.getSession().createQuery("select new TVisitorInfo(a.id, a.email, a.checkingNo, a.password, a.firstName, a.lastName, a.sex," +
                "a.company, a.position, a.country, a.province, a.city, a.address, a.backupEmail, a.mobileCode, a.mobilePhone, a.telCode," +
                "a.tel, a.telCode2, a.faxCode, a.fax, a.faxCode2, a.website, a.industry,a.industryOther, a.remark, a.createIp, a.createTime, a.updateIp," +
                "a.updateTime, a.sendEmailNum,a.sendEmailTime, a.sendMsgNum,a.sendMsgTime, a.langFlag,a.visitDate,a.beenToFair,a.beenToRole,a.isRecieveEmail," +
                "a.isRecieveDoc,a.isMobile,a.isjudged,a.isProfessional,a.isAccommodation, a.isDisabled, a.isReaded, a.tmp_Country,a.tmp_Postcode,a.tmp_Interest,a.tmp_InterestOthers," +
                "a.tmp_Knowfrom,a.tmp_KnowfromOthers,a.tmp_V_name1,a.tmp_V_title1, a.tmp_V_position1,a.tmp_V_contact1,a.tmp_V_name2,a.tmp_V_title2," +
                "a.tmp_V_position2,a.tmp_V_contact2,a.tmp_V_name3,a.tmp_V_title3, a.tmp_V_position3,a.tmp_V_contact3,a.guid,a.govement,a.rabbi,a.customer_type) from TVisitorInfo a where a.country=44 and a.rabbi=1");
        return q.list();
    }

    @Override
    public List<TVisitorInfo> loadForeignRabbiCustomers() {
        Query q = this.getSession().createQuery("select new TVisitorInfo(a.id, a.email, a.checkingNo, a.password, a.firstName, a.lastName, a.sex," +
                "a.company, a.position, a.country, a.province, a.city, a.address, a.backupEmail, a.mobileCode, a.mobilePhone, a.telCode," +
                "a.tel, a.telCode2, a.faxCode, a.fax, a.faxCode2, a.website, a.industry,a.industryOther, a.remark, a.createIp, a.createTime, a.updateIp," +
                "a.updateTime, a.sendEmailNum,a.sendEmailTime, a.sendMsgNum,a.sendMsgTime, a.langFlag,a.visitDate,a.beenToFair,a.beenToRole,a.isRecieveEmail," +
                "a.isRecieveDoc,a.isMobile,a.isjudged,a.isProfessional,a.isAccommodation, a.isDisabled, a.isReaded, a.tmp_Country,a.tmp_Postcode,a.tmp_Interest,a.tmp_InterestOthers," +
                "a.tmp_Knowfrom,a.tmp_KnowfromOthers,a.tmp_V_name1,a.tmp_V_title1, a.tmp_V_position1,a.tmp_V_contact1,a.tmp_V_name2,a.tmp_V_title2," +
                "a.tmp_V_position2,a.tmp_V_contact2,a.tmp_V_name3,a.tmp_V_title3, a.tmp_V_position3,a.tmp_V_contact3,a.guid,a.govement,a.rabbi,a.customer_type) from TVisitorInfo a where a.country<>44 and a.rabbi=1");
        return q.list();
    }
}
