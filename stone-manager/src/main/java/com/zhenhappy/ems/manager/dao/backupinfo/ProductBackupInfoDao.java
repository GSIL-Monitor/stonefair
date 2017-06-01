package com.zhenhappy.ems.manager.dao.backupinfo;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TExhibitorBackupInfo;
import com.zhenhappy.ems.manager.entity.backupinfo.TProductBackupInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
public interface ProductBackupInfoDao extends BaseDao<TProductBackupInfo> {
	public List<TProductBackupInfo> loadProductBackupInfoByIds(Integer[] ids);
}
