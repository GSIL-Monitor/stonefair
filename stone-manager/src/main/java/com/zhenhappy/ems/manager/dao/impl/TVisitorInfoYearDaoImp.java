package com.zhenhappy.ems.manager.dao.impl;

import com.zhenhappy.ems.dao.imp.BaseDaoHibernateImp;
import com.zhenhappy.ems.manager.dao.ExhibitorTermDao;
import com.zhenhappy.ems.manager.dao.TVisitorInfoYearDao;
import com.zhenhappy.ems.manager.entity.TExhibitorTerm;
import com.zhenhappy.ems.manager.entity.TVisitor_Info_Survey;
import com.zhenhappy.ems.manager.entity.TVisitor_Info_Year;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2016-12-15.
 */
@Repository
public class TVisitorInfoYearDaoImp extends BaseDaoHibernateImp<TVisitor_Info_Year> implements TVisitorInfoYearDao {
}
