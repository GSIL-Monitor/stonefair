package com.zhenhappy.ems.service;

import com.zhenhappy.ems.dao.JoinerDao;
import com.zhenhappy.ems.entity.TExhibitorJoiner;
import com.zhenhappy.ems.entity.TExhibitorJoinerEx;
import com.zhenhappy.ems.entity.managerrole.TUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wujianbin on 2014-04-20.
 */
@Service
public class JoinerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private JoinerDao joinerDao;

    public JoinerDao getJoinerDao() {
        return joinerDao;
    }

    public void setJoinerDao(JoinerDao joinerDao) {
        this.joinerDao = joinerDao;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * query all joiners in exhibitor.
     * @param eid
     * @return
     */
    public List<TExhibitorJoiner> queryAllJoinersByEid(Integer eid){
        String hql = "from TExhibitorJoiner where eid = " + eid;
        List<TExhibitorJoiner> joiners = hibernateTemplate.find(hql,new Object[]{});
        return joiners;
    }

    /**
     * query all joiners in exhibitor.
     * @param eid
     * @param flag 1：表示未激活参展列表；2：表示已激活参展列表
     * @return
     */
    public List<TExhibitorJoiner> queryAllJoinersByEid(Integer eid, Integer flag){
        String isDeleteStr = "";
        List<TExhibitorJoiner> joiners;
        if(flag == 1){
            joiners = hibernateTemplate.find("from TExhibitorJoiner where eid = ? and isDelete = 1 ", new Object[]{eid});
            isDeleteStr = " and isDelete = 1";
        }else if(flag == 2){
            joiners = hibernateTemplate.find("from TExhibitorJoiner where eid = ? and isDelete = 0 ", new Object[]{eid});
            isDeleteStr = " and isDelete = 0";
        }else{
            joiners = hibernateTemplate.find("from TExhibitorJoiner where eid = ? ", new Object[]{eid});
            isDeleteStr = "";
        }
        /*String hql = "from TExhibitorJoiner where eid = " + eid  + isDeleteStr + " order by isDelete";
        List<TExhibitorJoiner> joiners = hibernateTemplate.find("from TExhibitorJoiner where eid = ? and isDelete = ? ",
                new Object[]{eid, flag});*/
        //List<TExhibitorJoiner> joiners = hibernateTemplate.find(hql,new Object[]{});
        return joiners;
    }

    /**
     * query all joiners in exhibitor.
     * @return
     */
    public List<TExhibitorJoinerEx> queryAllJoiners(){
        List<TExhibitorJoinerEx> joiners = new ArrayList<TExhibitorJoinerEx>();
        List<Map<String, Object>> items = jdbcTemplate.queryForList("select e.company, b.booth_number, j.name, j.position, j.telphone, j.email from t_exhibitor_joiner j, t_exhibitor_info e, t_exhibitor_booth b where j.eid = e.eid and e.eid = b.eid");
        for (Map<String, Object> item : items) {
            TExhibitorJoinerEx joiner = new TExhibitorJoinerEx();
            joiner.setCompany((String) item.get("company"));
            joiner.setName((String) item.get("name"));
            joiner.setPosition((String) item.get("position"));
            joiner.setEmail((String) item.get("email"));
            joiner.setTelphone((String) item.get("telphone"));
            joiner.setExhibitorPosition((String) item.get("booth_number"));
            joiners.add(joiner);
        }

        return joiners;
    }

    /**
     * query all joiners in exhibitor.
     * @return
     */
    public List<TExhibitorJoinerEx> queryAllJoinersByTagAndRole(Integer tag, Integer type, TUserInfo userInfo){
        List<TExhibitorJoinerEx> joiners = new ArrayList<TExhibitorJoinerEx>();
        List<Map<String, Object>> items;
        if(userInfo != null){
            if(userInfo.getRoleId() > 2){
                tag = userInfo.getOwnerId();
            }
        }
        String typeCondition = "";
        if(type == 1){
            typeCondition = " and t.exhibitor_type = 1 ";
        }else if(type == 2){
            typeCondition = " and t.exhibitor_type = 2 ";
        }else if(type == 0){
            typeCondition = " and (t.exhibitor_type = '' or  t.exhibitor_type is null) ";
        }
        if(tag == -1){
            items = jdbcTemplate.queryForList("select e.company, b.booth_number, j.name, j.position, j.telphone, j.email from t_exhibitor_joiner j, t_exhibitor_info e, t_exhibitor_booth b ,t_exhibitor t " +
                    "where j.eid = e.eid and e.eid = b.eid and t.eid = e.eid " + typeCondition);
        }else{
            items = jdbcTemplate.queryForList("select e.company, b.booth_number, j.name, j.position, j.telphone, j.email from t_exhibitor_joiner j, t_exhibitor_info e, t_exhibitor_booth b,t_exhibitor t " +
                    "where t.tag=" + tag + " and j.eid = e.eid and e.eid = b.eid and e.eid = t.eid "  + typeCondition);
        }
        for (Map<String, Object> item : items) {
            TExhibitorJoinerEx joiner = new TExhibitorJoinerEx();
            joiner.setCompany((String) item.get("company"));
            joiner.setName((String) item.get("name"));
            joiner.setPosition((String) item.get("position"));
            joiner.setEmail((String) item.get("email"));
            joiner.setTelphone((String) item.get("telphone"));
            joiner.setExhibitorPosition((String) item.get("booth_number"));
            joiners.add(joiner);
        }

        return joiners;
    }

    /**
     * query all joiners in exhibitor.
     * @return
     */
    public List<TExhibitorJoiner> queryAllJoinersByEids(Integer[] eids){
        return joinerDao.loadJoinersByEids(eids);
    }

    /**
     * query all joiners in exhibitor.
     * @return
     */
    public List<TExhibitorJoiner> queryAllJoinersByIds(Integer[] ids){
        return joinerDao.loadJoinersByIds(ids);
    }

    @Transactional
    public void saveOrUpdate(TExhibitorJoiner joiner){
        hibernateTemplate.saveOrUpdate(joiner);
    }

    @Transactional
    public void saveOrUpdate(TExhibitorJoinerEx joiner){
        hibernateTemplate.saveOrUpdate(joiner);
    }

    @Transactional
    public int delete(Integer jid,Integer eid){
        return jdbcTemplate.update("delete from t_exhibitor_joiner where id =? and eid =?",new Object[]{jid,eid});
    }
}
