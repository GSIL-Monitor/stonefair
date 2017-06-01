package com.zhenhappy.ems.service;

import com.zhenhappy.ems.entity.TVisitorMsgLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by wangxd on 2014-04-11.
 */
@Service
public class VisitorLogMsgServiceImp implements VisitorLogMsgService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     *
     * @return
     */
    @Override
    public void insertLogMsg(TVisitorMsgLog visitor_Log_Msg) {
        hibernateTemplate.saveOrUpdate(visitor_Log_Msg);
    }

}
