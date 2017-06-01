package com.zhenhappy.ems.manager.dao.backupinfo;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TExhibitorJoinerBackupInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
public interface ExhibitorJoinerBackupInfoDao extends BaseDao<TExhibitorJoinerBackupInfo> {
	public List<TExhibitorJoinerBackupInfo> loadExhibitorJoinerBackupInfoByIds(Integer[] ids);
}
