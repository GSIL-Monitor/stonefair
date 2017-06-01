package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.companyinfo.HistoryCustomerInfoDao;
import com.zhenhappy.ems.manager.entity.companyinfo.THistoryCustomer;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-05-24.
 */
@Repository
public class HistoryCustomerInfoDaoImp extends BaseDaoHibernateImp<THistoryCustomer> implements HistoryCustomerInfoDao {

	@Override
	public List<THistoryCustomer> loadSelectedHistoryCustomers(Integer[] ids) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime, a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and a.id in (:ids) order by a.updatetime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}

	@Override
	public List<THistoryCustomer> loadSelectedHistoryInlandCustomers(Integer[] ids) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime, a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and a.country = 44 and a.id in (:ids) order by a.updatetime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}

	@Override
	public List<THistoryCustomer> loadSelectedHistoryInlandCustomersByOwner(Integer[] ownerIds) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime, a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and a.country = 44 and a.owner in (:ownerIds) order by a.updatetime desc");
		q.setParameterList("ownerIds", ownerIds);
		return q.list();
	}

	@Override
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByOwner(Integer[] ownerIds) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime, a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and  a.country <> 44 and a.owner in (:ownerIds) order by a.updatetime desc");
		q.setParameterList("ownerIds", ownerIds);
		return q.list();
	}

	@Override
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByCountryIdAndOwner(Integer countryId, Integer[] ownerIds) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime, a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and a.country in (:countryId) and a.owner in (:ownerIds) order by a.updatetime desc");
		q.setParameter("countryId", countryId);
		q.setParameterList("ownerIds", ownerIds);
		return q.list();
	}

	@Override
	public List<THistoryCustomer> loadSelectedHistoryForeignCustomers(Integer[] ids) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime,a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and a.country <> 44 and a.id in (:ids) order by a.updatetime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}

	@Override
	public List<THistoryCustomer> loadAllHistoryForeignCustomersByEmail(String[] emails) {
		Query q = this.getSession().createQuery("select new THistoryCustomer(a.id, a.cateory, a.company, a.country, " +
				"a.address, a.contact, a.position, a.telphone, a.email, a.fixtelphone, a.fax, a.website, a.backupaddress," +
				" a.remark, a.owner, a.createtime, a.updatetime, a.updateowner, a.isDelete) from THistoryCustomer a where a.isDelete = 0 and a.email in (:emails) order by a.updatetime desc");
		q.setParameterList("emails", emails);
		return q.list();
	}
}
