package com.zhenhappy.ems.stonetime;

import com.zhenhappy.ems.entity.TTag;
import com.zhenhappy.util.Page;
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
public class ExhibitorTimeManagerService {
	@Autowired
	private ExhibitorTimeDao exhibitorTimeDao;
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
	public List<TExhibitorTime> loadAllExhibitorTime() {
	    List<TExhibitorTime> tExhibitorTimes = exhibitorTimeDao.queryByHql("from TExhibitorTime", new Object[]{});
	    return tExhibitorTimes;
	}

	/**
     * 添加时间对象
     * @param tExhibitorTime
     */
    @Transactional
    public void addExhibitorTime(TExhibitorTime tExhibitorTime) {
        getHibernateTemplate().save(tExhibitorTime);
    }
    
    /**
	 * 修改时间对象
	 * @param tExhibitorTime
	 */
    @Transactional
    public void modifyExhibitorTime(TExhibitorTime tExhibitorTime) {
		getHibernateTemplate().update(tExhibitorTime);
    }

	@Transactional
	public void modifyExhibitorTime(String value1, String value2, String value3, String value4, String value5,
									String value6, String value7, String value8, String value9, String value10,
									String value11, String value12, String value13, String value14, String value15,
									String value16, String value17, String value18, Integer exhibitor_area) throws Exception {
		jdbcTemplate.update("update t_exhibitor_time set company_Info_Submit_Deadline_Zh=?, company_Info_Submit_Deadline_En=?," +
						"participant_List_Submit_Deadline_Zh=?, participant_List_Submit_Deadline_En=?, invoice_Information_Submit_Deadline_Zh=?," +
						"invoice_Information_Submit_Deadline_En=?, advertisement_Submit_Deadline_Zh=?, advertisement_Submit_Deadline_En=?, " +
						"company_Info_Insert_Submit_Deadline_Zh=?, company_Info_Insert_Submit_Deadline_En=?, stone_fair_end_year=?," +
						"stone_fair_begin_year=?, company_Info_Data_End_Html=?, visa_Info_Submit_Deadline_Zh=?, visa_Info_Submit_Deadline_En=?," +
				"stone_Fair_Show_Date_Zh=? ,stone_Fair_Show_Date_En=?, exhibitor_end_time=? where area_time=?",
				new Object[]{value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13,
						value14, value15, value16,value17, value18, exhibitor_area});
	}

	@Transactional
	public void modifyExhibitorMenuMove(Integer value, Integer exhibitor_area) throws Exception {
		jdbcTemplate.update("update t_exhibitor_time set menu_move_switch=? where area_time=?", new Object[]{value, exhibitor_area});
	}
}
