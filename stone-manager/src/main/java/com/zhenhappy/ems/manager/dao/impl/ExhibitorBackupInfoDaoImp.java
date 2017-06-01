package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.backupinfo.ExhibitorBackupInfoDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TExhibitorBackupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
@Repository
public class ExhibitorBackupInfoDaoImp extends BaseDaoHibernateImp<TExhibitorBackupInfo> implements ExhibitorBackupInfoDao {

	@Override
	public List<TExhibitorBackupInfo> loadExhibitorBackupInfoByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TExhibitorBackupInfo(a.area, a.contractId, a.country, a.createTime, a.createUser, a.eid, " +
				"a.exhibitionArea, a.exhibitor_booth_booth_number, a.exhibitor_booth_create_time, a.exhibitor_booth_create_user, " +
				"a.exhibitor_booth_exhibition_area, a.exhibitor_booth_mark, a.exhibitor_booth_update_time, a.exhibitor_booth_update_user, " +
				"a.exhibitor_info_address, a.exhibitor_info_address_en, a.exhibitor_info_admin_update_time, a.exhibitor_info_admin_user, " +
				"a.exhibitor_info_company, a.exhibitor_info_company_en, a.exhibitor_info_company_hignlight, a.exhibitor_info_company_t, " +
				"a.exhibitor_info_create_time, a.exhibitor_info_einfoid, a.exhibitor_info_email, a.exhibitor_info_email_address, " +
				"a.exhibitor_info_email_contact, a.exhibitor_info_email_telphone, a.exhibitor_info_emark, a.exhibitor_info_fax, " +
				"a.exhibitor_info_is_delete, a.exhibitor_info_logo, a.exhibitor_info_main_product, a.exhibitor_info_main_product_en, " +
				"a.exhibitor_info_mark, a.exhibitor_info_meipai, a.exhibitor_info_meipai_en, a.exhibitor_info_organization_code, " +
				"a.exhibitor_info_phone, a.exhibitor_info_update_time, a.exhibitor_info_website, a.exhibitor_info_zipcode, " +
				"a.exhibitor_invoice_apply_create_time, a.exhibitor_invoice_apply_exhibitor_no, a.exhibitor_invoice_apply_invoice_bank_account, " +
				"a.exhibitor_invoice_apply_invoice_bank_name, a.exhibitor_invoice_apply_invoice_company, a.exhibitor_invoice_apply_invoice_company_address, " +
				"a.exhibitor_invoice_apply_invoice_company_contact, a.exhibitor_invoice_apply_invoice_company_tel, a.exhibitor_invoice_apply_invoice_flag, " +
				"a.exhibitor_invoice_apply_invoice_general_tax_type, a.exhibitor_invoice_apply_invoice_general_taxpayer_flag, a.exhibitor_invoice_apply_invoice_image_address, " +
				"a.exhibitor_invoice_apply_invoice_no, a.exhibitor_invoice_apply_invoice_taxpayer_no,a.exhibitor_invoice_apply_title, " +
				"a.exhibitor_product_type, a.exhibitor_term_booth_number, a.exhibitor_term_create_time, a.exhibitor_term_create_user, " +
				"a.exhibitor_term_is_delete, a.exhibitor_term_join_term, a.exhibitor_term_mark, a.exhibitor_term_update_time, " +
				"a.exhibitor_term_update_user, a.exhibitor_type, a.group, a.isLogin, a.isLogout, a.lastLoginIp, a.lastLoginTime, a.level, a.password, " +
				"a.prodClass, a.province, a.send_invitation_flag, a.tag, a.updateTime, a.updateUser, a.username) from TExhibitorBackupInfo a where a.id in (:ids) order by a.updateTime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
