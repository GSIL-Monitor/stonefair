package com.zhenhappy.ems.service.managerrole;

import com.zhenhappy.ems.dao.managerrole.TUserOperationDao;
import com.zhenhappy.ems.entity.managerrole.TUserOperation;
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
public class TUserOperationService {
	@Autowired
	private TUserOperationDao userOperationDao;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger log = Logger.getLogger(TUserOperationService.class);

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
	public TUserOperation findOperation(Integer id){
		return userOperationDao.query(id);
	}

	@Transactional()
	public List<TUserOperation> findOperationByMenuId(Integer menuId){
		List<TUserOperation> userOperationList = userOperationDao.queryByHql("from TUserOperation where menuId = ?", new Object[]{menuId});
		return userOperationList.size() > 0 ? userOperationList : null;
	}

	@Transactional()
	public  List<TUserOperation> findUserOperation(Integer id){
		List<TUserOperation> userOperationList = userOperationDao.queryByHql("from TUserOperation where operationId = ?", new Object[]{id});
		return userOperationList.size() > 0 ? userOperationList : null;
	}

	@Transactional()
	public  int countOperation(TUserOperation userOperation){
		List<TUserOperation> userOperationList = userOperationDao.queryByHql("from TUserOperation where operationId = ?", new Object[]{userOperation.getOperationId()});
		return userOperationList.size() > 0 ? userOperationList.size() : 0;
	}

	@Transactional()
	public  void deleteOperation(Integer id){
		TUserOperation userOperation = findOperation(id);
		if(userOperation != null){
			try {
				userOperationDao.delete(userOperation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional()
	public  void addOperation(TUserOperation userOperation){
		userOperationDao.create(userOperation);
	}

	@Transactional()
	public  void updateOperation(TUserOperation userOperation){
		userOperationDao.update(userOperation);
	}
	
	//通过名称判断是否存在，（新增时不能重名)
	@Transactional()
	public TUserOperation existRoleWithMenuName(String operationName){
		List<String> conditions = new ArrayList<String>();
		if (StringUtils.isNotEmpty(operationName)) {
			conditions.add(" (operationName like '%" + operationName  + "%') ");
		}
		String conditionsSql = StringUtils.join(conditions, " and ");
		if(conditionsSql != null && !"".equals(conditionsSql)){
			conditionsSql = " where " + conditionsSql;
		}
		List<TUserOperation> userOperationList = userOperationDao.queryByHql("from TUserOperation" + conditionsSql, new Object[]{});
		return userOperationList.size() > 0 ? userOperationList.get(0) : null;
	}

	//通过名称判断是否存在，（新增时不能重名)
	@Transactional()
	public List<TUserOperation> findOperationByParendIdAndIds(String[] menuIds){
		List<TUserOperation> userOperationList = new ArrayList<TUserOperation>();
		if(menuIds != null && menuIds.length>0){
			for(int i=0;i<menuIds.length;i++){
				int id = Integer.parseInt(menuIds[i]);
				List<TUserOperation> tempList = userOperationDao.queryByHql("from TUserOperation where and menuId = ?", new Object[]{id});
				userOperationList.addAll(tempList);
			}
		}
		return userOperationList.size() > 0 ? userOperationList : null;
	}
	
	//批量删除
	@Transactional()
	public  void deleteUserInfoById(Integer id){
		if (id != null) {
			deleteOperation(id);
		}
	}
}
