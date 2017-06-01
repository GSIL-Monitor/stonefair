package com.zhenhappy.ems.service.managerrole;

import com.zhenhappy.ems.dao.managerrole.TUserMenuDao;
import com.zhenhappy.ems.entity.managerrole.TUserMenu;
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
public class TUserMenuService {
	@Autowired
	private TUserMenuDao userMenuDao;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger log = Logger.getLogger(TUserMenuService.class);

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
	public TUserMenu findOneUserMenu(Integer id){
		return userMenuDao.query(id);
	}

	@Transactional()
	public List<TUserMenu> findOneUserMenuByMenuId(Integer menuId){
		List<TUserMenu> userMenuList = userMenuDao.queryByHql("from TUserMenu where menuId = ?", new Object[]{menuId});
		return userMenuList.size() > 0 ? userMenuList : null;
	}

	@Transactional()
	public  List<TUserMenu> findUserMenuByParentId(Integer id){
		List<TUserMenu> userMenuList = userMenuDao.queryByHql("from TUserMenu where parentId = ?", new Object[]{id});
		return userMenuList.size() > 0 ? userMenuList : null;
	}

	@Transactional()
	public  int countUserMenuByParentId(Integer parentId){
		List<TUserMenu> userMenuList = userMenuDao.queryByHql("from TUserMenu where parentId = ?", new Object[]{parentId});
		return userMenuList.size() > 0 ? userMenuList.size() : 0;
	}

	@Transactional()
	public  void deleteUserMenu(Integer id){
		TUserMenu userMenu = findOneUserMenu(id);
		if(userMenu != null){
			try {
				userMenuDao.delete(userMenu);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional()
	public  void addUserMenu(TUserMenu userMenu){
		userMenuDao.create(userMenu);
	}

	@Transactional()
	public  void updateUserMenu(TUserMenu userMenu){
		userMenuDao.update(userMenu);
	}
	
	//通过名称判断是否存在，（新增时不能重名)
	@Transactional()
	public TUserMenu existRoleWithMenuName(String menuName){
		List<TUserMenu> userMenuList = userMenuDao.queryByHql("from TUserMenu where userName = ?", new Object[]{menuName});
		return userMenuList.size() > 0 ? userMenuList.get(0) : null;
	}

	//通过名称判断是否存在，（新增时不能重名)
	@Transactional()
	public List<TUserMenu> findMenuByParendIdAndIds(Integer parentId, String[] menuIds){
		List<TUserMenu> userMenuList = new ArrayList<TUserMenu>();
		if(menuIds != null && menuIds.length>0){
			for(int i=0;i<menuIds.length;i++){
				int id = Integer.parseInt(menuIds[i]);
				List<TUserMenu> tempList = userMenuDao.queryByHql("from TUserMenu where parentId = ? and menuId = ? ", new Object[]{parentId,id});
				userMenuList.addAll(tempList);
			}
		}
		return userMenuList.size() > 0 ? userMenuList : null;
	}
	
	//批量删除
	@Transactional()
	public  void deleteUserMenuById(Integer id){
		if (id != null) {
			deleteUserMenu(id);
		}
	}
}
