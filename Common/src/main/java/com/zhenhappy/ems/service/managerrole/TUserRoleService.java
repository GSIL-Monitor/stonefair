package com.zhenhappy.ems.service.managerrole;

import com.zhenhappy.ems.dao.managerrole.TUserRoleDao;
import com.zhenhappy.ems.entity.managerrole.TUserRole;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxd on 2016-05-13.
 */
@Service
public class TUserRoleService {
	@Autowired
	private TUserRoleDao userRoleDao;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger log = Logger.getLogger(TUserRoleService.class);

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Transactional()
	public TUserRole findOneRole(Integer roleId){
		return userRoleDao.query(roleId);
	}

	@Transactional()
	public  List<TUserRole> findRole(Integer roleId){
		List<TUserRole> userRoleList = userRoleDao.queryByHql("from TUserRole where roleId = ?", new Object[]{roleId});
		return userRoleList.size() > 0 ? userRoleList : null;
	}

	@Transactional()
	public  List<TUserRole> findRoleByName(String roleName){
		List<String> conditions = new ArrayList<String>();
		if (StringUtils.isNotEmpty(roleName)) {
			conditions.add(" (roleName like '%" + roleName  + "%') ");
		}
		String conditionsSql = StringUtils.join(conditions, " and ");
		if(conditionsSql != null && !"".equals(conditionsSql)){
			conditionsSql = " where " + conditionsSql;
		}
		List<TUserRole> userRoleList = userRoleDao.queryByHql("from TUserRole" + conditionsSql, new Object[]{});
		return userRoleList.size() > 0 ? userRoleList : null;
	}

	@Transactional()
	public  int countRole(TUserRole userRole){
		List<TUserRole> userRoleList = userRoleDao.queryByHql("from TUserRole where roleId = ?", new Object[]{userRole.getRoleId()});
		return userRoleList.size() > 0 ? userRoleList.size() : 0;
	}

	@Transactional()
	public  int countRoleByName(String roleName){
		List<TUserRole> userRoleList = userRoleDao.queryByHql("from TUserRole where roleName = ?", new Object[]{roleName});
		return userRoleList.size() > 0 ? userRoleList.size() : 0;
	}

	@Transactional()
	public  void deleteRole(Integer roleId){
		TUserRole userRole = findOneRole(roleId);
		if(userRole != null){
			try {
				userRoleDao.delete(userRole);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional()
	public  void addRole(TUserRole userRole){
		userRoleDao.create(userRole);
	}

	@Transactional()
	public  void updateRole(TUserRole userRole){
		userRoleDao.update(userRole);
	}
	
	//通过名称判断是否存在，（新增时不能重名
	@Transactional()
	public TUserRole existRoleWithRoleName(String roleName){
		List<TUserRole> userRoleList = userRoleDao.queryByHql("from TUserRole where roleName = ?", new Object[]{roleName});
		return userRoleList.size() > 0 ? userRoleList.get(0) : null;
	}
	
	//批量删除
	@Transactional()
	public  void deleteRoleByRoleIds(Integer roleId){
		if (roleId != null) {
			deleteRole(roleId);
		}
	}
}
