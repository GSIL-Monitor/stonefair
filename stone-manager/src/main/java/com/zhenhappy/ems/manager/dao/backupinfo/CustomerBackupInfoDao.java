package com.zhenhappy.ems.manager.dao.backupinfo;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TVisitorBackupInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
public interface CustomerBackupInfoDao extends BaseDao<TVisitorBackupInfo> {
	public List<TVisitorBackupInfo> loadCustomerBackupInfoByIds(Integer[] ids);
}
