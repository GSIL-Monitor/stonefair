package com.zhenhappy.ems.dao.imp;

import com.zhenhappy.ems.dao.ExhibitorDao;
import com.zhenhappy.ems.dao.TeaExhibitorDao;
import com.zhenhappy.ems.entity.TExhibitor;
import com.zhenhappy.ems.entity.TeaExhibitor;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 2016-07-20.
 */
@Repository
public class TeaExhibitorImp extends BaseDaoHibernateImp<TeaExhibitor> implements TeaExhibitorDao {
    /**
     * <b>Summary: </b> 构造一个 NBaseDaoHibernateImp <b>Remarks: </b> 构造类
     * NBaseDaoHibernateImp 的构造函数 NBaseDaoHibernateImp
     *
     */
    public TeaExhibitorImp() {
        super();
    }

	@Override
	public List<TeaExhibitor> loadAllExhibitorsByLogType(Integer type) {
		String typeCondition = "";
		if(type == 1){
			typeCondition = " isLogout = 0";
			Query q = this.getSession().createQuery("select new TeaExhibitor(e.eid, "
					+ "e.username, "
					+ "e.password, "
					+ "e.level, "
					+ "e.area, "
					+ "e.lastLoginTime, "
					+ "e.lastLoginIp, "
					+ "e.isLogout, "
					+ "e.createUser, "
					+ "e.createTime, "
					+ "e.updateUser, "
					+ "e.updateTime, "
					+ "e.tag, "
					+ "e.province, "
					+ "e.country, "
					+ "e.group, "
					+ "e.contractId, "
					+ "e.exhibitionArea, "
					+ "e.exhibitor_type,"
					+ "e.isLogin,"
					+ "e.fair_year)"
					+ "from TeaExhibitor e where " + typeCondition);
			return q.list();
		} else{
			Query q = this.getSession().createQuery("select new TeaExhibitor(e.eid, "
					+ "e.username, "
					+ "e.password, "
					+ "e.level, "
					+ "e.area, "
					+ "e.lastLoginTime, "
					+ "e.lastLoginIp, "
					+ "e.isLogout, "
					+ "e.createUser, "
					+ "e.createTime, "
					+ "e.updateUser, "
					+ "e.updateTime, "
					+ "e.tag, "
					+ "e.province, "
					+ "e.country, "
					+ "e.group, "
					+ "e.contractId, "
					+ "e.exhibitionArea, "
					+ "e.exhibitor_type,"
					+ "e.isLogin,"
					+ "e.fair_year)"
					+ "from TeaExhibitor e ");
			return q.list();
		}
	}
}
