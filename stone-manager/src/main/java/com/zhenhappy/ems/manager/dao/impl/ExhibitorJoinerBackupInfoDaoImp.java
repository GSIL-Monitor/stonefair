package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.backupinfo.ExhibitorJoinerBackupInfoDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TExhibitorJoinerBackupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
@Repository
public class ExhibitorJoinerBackupInfoDaoImp extends BaseDaoHibernateImp<TExhibitorJoinerBackupInfo> implements ExhibitorJoinerBackupInfoDao {

	@Override
	public List<TExhibitorJoinerBackupInfo> loadExhibitorJoinerBackupInfoByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new TExhibitorJoinerBackupInfo(a.admin, a.adminUpdateTime, a.createTime, a.email, " +
				"a.exhibitor_info_backup_id, a.exhibitorPosition, a.id, a.isDelete, a.name, a.position, a.telphone) " +
				"from TExhibitorJoinerBackupInfo a where a.id in (:ids) order by a.adminUpdateTime desc");
		q.setParameterList("ids", ids);
		return q.list();
	}
	
}
