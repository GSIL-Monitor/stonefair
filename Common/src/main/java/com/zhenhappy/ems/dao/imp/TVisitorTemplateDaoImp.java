package com.zhenhappy.ems.dao.imp;

import com.zhenhappy.ems.dao.TVisitorTemplateDao;
import com.zhenhappy.ems.entity.TVisitorTemplate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangxd9 on 2016-03-30.
 */
@Repository
public class TVisitorTemplateDaoImp extends BaseDaoHibernateImp<TVisitorTemplate> implements TVisitorTemplateDao {

	@Override
	public List<TVisitorTemplate> loadWCustomerTemplate(Integer[] cids) {
		Query q = this.getSession().createQuery("select new TVisitorTemplate(c.id) from TVisitorTemplate c where c.id in (:pids)");
		q.setParameterList("cids", cids);
		return q.list();
	}
	
}
