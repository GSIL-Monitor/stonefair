package com.zhenhappy.ems.service.managerrole;

import com.zhenhappy.ems.dao.managerrole.TUserInfoDao;
import com.zhenhappy.ems.dto.managerrole.QueryUserInfo;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import com.zhenhappy.util.Page;
import com.zhenhappy.util.QueryFactory;
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
public class TUserInfoService {
	@Autowired
	private TUserInfoDao userInfoDao;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger log = Logger.getLogger(TUserInfoService.class);

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
	public TUserInfo findOneUserInfo(Integer id){
		return userInfoDao.query(id);
	}

	@Transactional()
	public  List<TUserInfo> loadAllUserInfo(){
		Page page = new Page();
		page.setPageSize(20);
		page.setPageIndex(1);
		String conditionsSqlOrder = "where u.roleId = r.roleId";
		List<QueryUserInfo> userInfoList = userInfoDao.queryPageByHQL("select count(*) from TUserInfo u, TUserRole r " + conditionsSqlOrder,
				"select new com.zhenhappy.ems.dto.managerrole.QueryUserInfo(u.id,u.name, u.userName, u.password, u.userType, u.roleId, r.roleName,u.userDescription) "
						+ "from TUserInfo u, TUserRole r " + conditionsSqlOrder, new Object[]{}, page);
		if(userInfoList != null && userInfoList.size()>0){
			List<TUserInfo> userInfoResultList = new ArrayList<TUserInfo>();
			for(int i=0;i<userInfoList.size();i++){
				QueryUserInfo queryUserInfo = userInfoList.get(i);
				TUserInfo tUserInfo = new TUserInfo();
				tUserInfo.setId(queryUserInfo.getId());
				tUserInfo.setName(queryUserInfo.getName());
				tUserInfo.setUserName(queryUserInfo.getUsername());
				tUserInfo.setPassword(queryUserInfo.getPassword());
				tUserInfo.setUserType(queryUserInfo.getUserType());
				tUserInfo.setRoleId(queryUserInfo.getRoleId());
				tUserInfo.setRoleName(queryUserInfo.getRoleName());
				tUserInfo.setUserDescription(queryUserInfo.getUserDescription());
				userInfoResultList.add(tUserInfo);
			}
			return userInfoResultList;
		} else {
			return null;
		}
	}

	@Transactional()
	public  int countUserInfo(TUserInfo userInfo){
		//List<TUserInfo> userRoleList = userInfoDao.queryByHql("from TUserInfo where roleId = ?", new Object[]{userInfo.getRoleId()});
		int count = userInfoDao.queryCount("select count(*) from TUserInfo u, TUserRole r where u.roleId = r.roleId", new Object[]{}, QueryFactory.HQL);
		return count;
	}

	@Transactional()
	public  void deleteUserInfo(Integer id){
		TUserInfo userInfo = findOneUserInfo(id);
		if(userInfo != null){
			try {
				userInfoDao.delete(userInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional()
	public  void addUserInfo(TUserInfo userInfo){
		userInfoDao.create(userInfo);
	}

	@Transactional()
	public  void updateUserInfo(TUserInfo userInfo){
		userInfoDao.update(userInfo);
	}
	
	//通过名称判断是否存在，（新增时不能重名
	@Transactional()
	public TUserInfo existUserInfoWithUserName(String userName){
		List<String> conditions = new ArrayList<String>();
		if (StringUtils.isNotEmpty(userName)) {
			conditions.add(" (userName like '%" + userName  + "%') ");
		}
		String conditionsSql = StringUtils.join(conditions, " and ");
		if(conditionsSql != null && !"".equals(conditionsSql)){
			conditionsSql = " where " + conditionsSql;
		}
		List<TUserInfo> userInfoist = userInfoDao.queryByHql("from TUserInfo" + conditionsSql, new Object[]{});
		return userInfoist.size() > 0 ? userInfoist.get(0) : null;
	}

	//通过名称判断是否存在，（新增时不能重名
	@Transactional()
	public TUserInfo existUserWithRoleId(Integer roleId){
		List<TUserInfo> userInfoist = userInfoDao.queryByHql("from TUserInfo where roleId = ?", new Object[]{roleId});
		return userInfoist.size() > 0 ? userInfoist.get(0) : null;
	}
	
	//批量删除
	@Transactional()
	public  void deleteUserInfoById(Integer id){
		if (id != null) {
			deleteUserInfo(id);
		}
	}

	//根据ownerId查找，用于资料分享操作
	@Transactional()
	public TUserInfo findOneUserInfoByOwnerId(Integer ownerId){
		List<TUserInfo> userInfoist = userInfoDao.queryByHql("from TUserInfo where ownerId = ?", new Object[]{ownerId});
		return userInfoist.size() > 0 ? userInfoist.get(0) : null;
	}

	//根据ownerId查找，用于资料分享操作
	@Transactional()
	public List<TUserInfo> findUserInfoByRoleId(Integer roleId){
		List<TUserInfo> userInfoist = userInfoDao.queryByHql("from TUserInfo where roleId = ?", new Object[]{roleId});
		return userInfoist.size() > 0 ? userInfoist : null;
	}
}
