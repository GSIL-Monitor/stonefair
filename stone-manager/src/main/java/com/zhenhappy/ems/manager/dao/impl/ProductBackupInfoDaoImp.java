package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.backupinfo.ProductBackupInfoDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TProductBackupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
@Repository
public class ProductBackupInfoDaoImp extends BaseDaoHibernateImp<TProductBackupInfo> implements ProductBackupInfoDao {

	@Override
	public List<TProductBackupInfo> loadProductBackupInfoByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TProductBackupInfo(a.admin, a.adminUpdateTime, a.createTime, a.description, a.descriptionEn, " +
				"a.exhibitor_info_backup_id, a.flag, a.id, a.keyWords, a.keyWordsEn, a.origin, a.originEn, a.packageDescription, " +
				"a.packageDescriptionEn, a.priceDescription, a.priceDescriptionEn, a.productBrand, a.productBrandEn, a.productCount, " +
				"a.productCountEn, a.productModel, a.productModelEn, a.productName, a.productNameEn, a.productSpec, a.productSpecEn, a.updateTime) " +
				"from TProductBackupInfo a where a.id in (:ids) order by a.adminUpdateTime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
