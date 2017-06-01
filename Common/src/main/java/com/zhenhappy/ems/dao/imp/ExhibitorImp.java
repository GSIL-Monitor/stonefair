package com.zhenhappy.ems.dao.imp;

import java.util.List;

import com.zhenhappy.ems.dao.ExhibitorDao;
import com.zhenhappy.ems.entity.TExhibitor;

import com.zhenhappy.ems.entity.TeaExhibitor;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by wujianbin on 2014-04-03.
 */
@Repository
public class ExhibitorImp extends BaseDaoHibernateImp<TExhibitor> implements ExhibitorDao {
    /**
     * <b>Summary: </b> 构造一个 NBaseDaoHibernateImp <b>Remarks: </b> 构造类
     * NBaseDaoHibernateImp 的构造函数 NBaseDaoHibernateImp
     *
     */
    public ExhibitorImp() {
        super();
    }

	@Override
	public List<TExhibitor> loadExhibitorsByEids(Integer[] eids) {
		Query q = this.getSession().createQuery("select new TExhibitor(e.eid, "
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
				+ "e.exhibitionArea,"
				+ "e.exhibitor_type,"
				+ "e.isLogin)"
				+ "from TExhibitor e where e.eid in (:eids)");
		q.setParameterList("eids", eids);
		return q.list();
	}

	@Override
	public List<TExhibitor> loadExhibitorsByEidsAndFlag(Integer[] eids, int flag) {
		Query q = this.getSession().createQuery("select new TExhibitor(e.eid, "
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
				+ "e.exhibitionArea,"
				+ "e.exhibitor_type,"
				+ "e.isLogin)"
				+ "from TExhibitor e where e.isLogout = " + flag + "and  e.eid in (:eids)");
		q.setParameterList("eids", eids);
		return q.list();
	}

	@Override
	public List<TExhibitor> loadAllExhibitorsByTagAndRole(int tag, Integer type) {
		String typeCondition = "";
		if(type == 1){
			typeCondition = " and exhibitor_type = 1";
		}else if(type == 2){
			typeCondition = " and exhibitor_type = 2";
		} else if(type == 0){
			typeCondition = " and (e.exhibitor_type = '' or e.exhibitor_type is null)";
		}
		Query q = this.getSession().createQuery("select new TExhibitor(e.eid, "
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
				+ "e.isLogin)"
				+ "from TExhibitor e where e.tag = " + tag + typeCondition);
		return q.list();
	}

	@Override
	public List<TExhibitor> loadAllExhibitorsByType(Integer type) {
		String typeCondition = "";
		if(type == 1){
			typeCondition = " exhibitor_type = 1";
		}else if(type == 2){
			typeCondition = " exhibitor_type = 2";
		} else if(type == 0){
			typeCondition = " (exhibitor_type = '' or exhibitor_type is null)";
		}

		Query q = this.getSession().createQuery("select new TExhibitor(e.eid, "
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
				+ "e.isLogin)"
				+ "from TExhibitor e where " + typeCondition);
		return q.list();
	}

	@Override
	public List<TExhibitor> loadAllExhibitorsByLogType(Integer type) {
		String typeCondition = "";
		if(type == 1){
			typeCondition = " isLogout = 0";
			Query q = this.getSession().createQuery("select new TExhibitor(e.eid, "
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
					+ "e.isLogin)"
					+ "from TExhibitor e where " + typeCondition);
			return q.list();
		} else{
			Query q = this.getSession().createQuery("select new TExhibitor(e.eid, "
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
					+ "e.isLogin)"
					+ "from TExhibitor e ");
			return q.list();
		}
	}
}
