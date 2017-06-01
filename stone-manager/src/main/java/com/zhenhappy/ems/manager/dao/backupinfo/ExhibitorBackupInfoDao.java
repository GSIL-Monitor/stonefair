package com.zhenhappy.ems.manager.dao.backupinfo;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TExhibitorBackupInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
public interface ExhibitorBackupInfoDao extends BaseDao<TExhibitorBackupInfo> {
	public List<TExhibitorBackupInfo> loadExhibitorBackupInfoByIds(Integer[] ids);
}
