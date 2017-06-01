package com.zhenhappy.ems.buddhatime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2016-06-28.
 */
@Service
public class ExhibitorBuddhaTimeManagerService {
	@Autowired
	private ExhibitorBuddhaTimeDao exhibitorTimeDao;
	@Autowired
    private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

	/**
	 * 查询所有时间对象
	 * @return
	 */
	@Transactional
	public List<TExhibitorBuddhaTime> loadAllExhibitorTime() {
	    List<TExhibitorBuddhaTime> tExhibitorTimes = exhibitorTimeDao.queryByHql("from TExhibitorTeaTime", new Object[]{});
	    return tExhibitorTimes;
	}

	/**
     * 添加时间对象
     * @param tExhibitorTime
     */
    @Transactional
    public void addExhibitorTime(TExhibitorBuddhaTime tExhibitorTime) {
        getHibernateTemplate().save(tExhibitorTime);
    }
    
    /**
	 * 修改时间对象
	 * @param tExhibitorTime
	 */
    @Transactional
    public void modifyExhibitorTime(TExhibitorBuddhaTime tExhibitorTime) {
		getHibernateTemplate().update(tExhibitorTime);
    }

	@Transactional
	public void modifyExhibitorTime(String value1, String value2, String value3) throws Exception {
		jdbcTemplate.update("update t_exhibitor_buddha_time set buddha_Fair_Show_Date=?, exhibitor_Info_Submit_Deadline=?," +
						"buddha_Fair_Show_Date_Begin=?", new Object[]{value1, value2, value3});
	}
}
