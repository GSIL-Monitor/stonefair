package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.visitorgroup.VisitorGroupDao;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorGroupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-26.
 */
@Repository
public class VisitorGroupDaoImp extends BaseDaoHibernateImp<TVisitorGroupInfo> implements VisitorGroupDao {
    public List<TVisitorGroupInfo> loadCustomerGroupByIds(Integer[] ids){
        Query q = this.getSession().createQuery("select new TVisitorGroupInfo(a.id, a.groupName, a.leaderID, a.createTime, " +
                "a.createIP, a.updateTime, a.updateIP, a.isDisabled, a.gUID) from TVisitorGroupInfo a where a.id in (:ids) order by a.updateTime desc");
        q.setParameterList("ids", ids);
        return q.list();
    }
}
