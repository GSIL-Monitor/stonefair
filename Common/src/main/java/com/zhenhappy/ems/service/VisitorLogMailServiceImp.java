package com.zhenhappy.ems.service;

import com.zhenhappy.ems.dao.VisitorLogMailDao;
import com.zhenhappy.ems.entity.TVisitorMailLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by wangxd on 2014-04-11.
 */
@Service
public class VisitorLogMailServiceImp implements VisitorLogMailService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     *
     * @return
     */
    @Override
    public void insertLogMail(TVisitorMailLog visitor_Log_Mail) {
        hibernateTemplate.saveOrUpdate(visitor_Log_Mail);
    }

}
