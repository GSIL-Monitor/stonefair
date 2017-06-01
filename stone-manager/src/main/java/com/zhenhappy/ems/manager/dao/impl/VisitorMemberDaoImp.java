package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.visitorgroup.VisitorMemberDao;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorMemberInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2016-12-26.
 */
@Repository
public class VisitorMemberDaoImp extends BaseDaoHibernateImp<TVisitorMemberInfo> implements VisitorMemberDao {
    public List<TVisitorMemberInfo> loadVisitorMemberListById(Integer[] ids){
        Query q = this.getSession().createQuery("select new TVisitorMemberInfo(a.id, a.checkingNo, a.memberName, a.groupID, " +
                "a.inviterID, a.email, a.mobile, a.password, a.company, a.position, a.address, a.createTime, a.createIP," +
                " a.updateTime, a.updateIP, a.isLeader, a.isDisabled, a.gUID, a.emailNum, a.msgNum) from TVisitorMemberInfo a where a.id in (:ids) order by a.updateTime desc");
        q.setParameterList("ids", ids);
        return q.list();
    }
}
