package com.zhenhappy.ems.dao.imp;

import com.zhenhappy.ems.dao.TInvoiceApplyDao;
import com.zhenhappy.ems.dao.TVisaDao;
import com.zhenhappy.ems.entity.TInvoiceApplyExtend;
import com.zhenhappy.ems.entity.TVisa;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wujianbin on 2014-07-02.
 */
@Repository
public class TInvoiceApplyDaoImp extends BaseDaoHibernateImp<TInvoiceApplyExtend> implements TInvoiceApplyDao {

	@Override
	public List<TInvoiceApplyExtend> loadTInvoiceApplyByInvoiceFlag(Integer[] eids) {
		Query q = this.getSession().createQuery("select new TInvoiceApplyExtend(v.id, v.exhibitorNo, v.invoiceNo, v.title," +
				" v.eid, v.createTime, v.invoice_flag, v.invoice_company, v.invoice_taxpayer_no, v.invoice_bank_name, " +
				"v.invoice_bank_account, v.invoice_company_address, v.invoice_company_tel, v.invoice_company_contact," +
				" v.invoice_general_taxpayer_flag, v.invoice_general_tax_type, v.invoice_image_address) " +
				"from TVisa v where v.id in (:vids) and v.passportNo != ''");
		q.setParameterList("eids", eids);
		return q.list();
	}
	
}
