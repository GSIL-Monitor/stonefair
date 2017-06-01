package com.zhenhappy.ems.teatime;

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
public class ExhibitorTeaTimeManagerService {
	@Autowired
	private ExhibitorTeaTimeDao exhibitorTimeDao;
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
	public List<TExhibitorTeaTime> loadAllExhibitorTime() {
	    List<TExhibitorTeaTime> tExhibitorTimes = exhibitorTimeDao.queryByHql("from TExhibitorTeaTime", new Object[]{});
	    return tExhibitorTimes;
	}

	/**
     * 添加时间对象
     * @param tExhibitorTime
     */
    @Transactional
    public void addExhibitorTime(TExhibitorTeaTime tExhibitorTime) {
        getHibernateTemplate().save(tExhibitorTime);
    }
    
    /**
	 * 修改时间对象
	 * @param tExhibitorTime
	 */
    @Transactional
    public void modifyExhibitorTime(TExhibitorTeaTime tExhibitorTime) {
		getHibernateTemplate().update(tExhibitorTime);
    }

	@Transactional
	public void modifyExhibitorTime(String value1, String value2, String value3, String value4, String value5, String value6, String value7,
									String value8, String value9, String value10, String value11, String value12, String value13) throws Exception {
		jdbcTemplate.update("update t_exhibitor_teafair_time set tea_Fair_Show_Date_Zh=?, tea_Fair_Show_Date_En=?," +
				"exhibitor_Info_Submit_Deadline_Zh=?, exhibitor_Info_Submit_Deadline_En=?, tea_Fair_Show_Year=?, " +
				"tea_Fair_Show_Begin_Date=?, tea_Fair_Data_End_Html=?, tea_Fair_Contact_Submit_Deadline_Zh=?, " +
				"tea_Fair_Contact_Submit_Deadline_En=?, tea_Fair_Invoice_Submit_Deadline_Zh=?, " +
				"tea_Fair_Invoice_Submit_Deadline_En=?, tea_Fair_Visa_Submit_Deadline_Zh=?, tea_Fair_Visa_Submit_Deadline_En=?",
				new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13});
	}
}
