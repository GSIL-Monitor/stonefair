package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.visitorfeedback.VisitorFeedBackDao;
import com.zhenhappy.ems.manager.dao.visitorgroup.VisitorGroupDao;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBack;
import com.zhenhappy.ems.manager.entity.visitorgroup.TVisitorGroupInfo;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2017-4-16.
 */
@Repository
public class VisitorFeedBackDaoImp extends BaseDaoHibernateImp<TVisitorFeedBack> implements VisitorFeedBackDao {

    @Override
    public List<TVisitorFeedBack> loadForeignFeedBackDataByIds(Integer[] ids) {
        Query q = this.getSession().createQuery("select new TVisitorFeedBack(a.id, a.userName, a.q1, a.a1, a.q2, a.a2,a.q3, " +
                "a.a3, a.q4, a.a4, a.q5, a.a5, a.q6, a.a6, a.q7, a.a7, a.q8, a.a8, a.q9, a.a9, a.q10, a.a10, a.q11, a.a11, a.q12, a.a12," +
                " a.q13, a.a13, a.q14, a.a14, a.q15, a.a15, a.q16, a.a16, a.q17, a.a17, a.q18, a.a18, a.q19, a.a19, a.q20, a.a20, a.remark, " +
                "a.createIP, a.createTime, a.readed, a.gUID) from TVisitorFeedBack a where a.id in (:ids) order by a.createTime desc");
        q.setParameterList("ids", ids);
        return q.list();
    }
}
