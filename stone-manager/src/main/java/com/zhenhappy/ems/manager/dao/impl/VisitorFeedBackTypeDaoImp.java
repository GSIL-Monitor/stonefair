package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.visitorfeedback.VisitorFeedBackDao;
import com.zhenhappy.ems.manager.dao.visitorfeedback.VisitorFeedBackTypeDao;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBack;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBackType;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd on 2017-5-26.
 */
@Repository
public class VisitorFeedBackTypeDaoImp extends BaseDaoHibernateImp<TVisitorFeedBackType> implements VisitorFeedBackTypeDao {
}
