package com.zhenhappy.ems.service;

import com.zhenhappy.ems.dao.InvoiceExtendDao;
import com.zhenhappy.ems.entity.TInvoiceApplyExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2016-010-31.
 */
@Service
public class InvoiceExtendService {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private InvoiceExtendDao invoiceExtendDao;

    @Transactional
    public void create(TInvoiceApplyExtend invoiceApply) {
        hibernateTemplate.saveOrUpdate(invoiceApply);
    }

    public TInvoiceApplyExtend getByEid(Integer eid) {
        List<TInvoiceApplyExtend> applyList = hibernateTemplate.find("from TInvoiceApplyExtend where eid = ?", eid);
        return applyList.size() == 0 ? null : applyList.get(0);
    }

    @Transactional()
    public void update(TInvoiceApplyExtend invoiceApplyExtend) {
        invoiceExtendDao.update(invoiceApplyExtend);
    }
}
