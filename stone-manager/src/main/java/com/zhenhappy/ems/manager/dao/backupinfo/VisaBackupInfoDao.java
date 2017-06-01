package com.zhenhappy.ems.manager.dao.backupinfo;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.manager.entity.backupinfo.TContactBackupInfo;
import com.zhenhappy.ems.manager.entity.backupinfo.TVisaBackupInfo;

import java.util.List;

/**
 * Created by wangxd on 2016-12-04.
 */
public interface VisaBackupInfoDao extends BaseDao<TVisaBackupInfo> {
	public List<TVisaBackupInfo> loadVisaBackupInfoByIds(Integer[] ids);
}
