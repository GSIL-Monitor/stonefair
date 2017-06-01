package com.zhenhappy.ems.dao.managerrole;

import com.zhenhappy.ems.dao.BaseDao;
import com.zhenhappy.ems.entity.managerrole.TUserOperation;

import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
public interface TUserOperationDao extends BaseDao<TUserOperation> {
    public List<TUserOperation> loadUserOperationByIds(Integer[] ids);
}
