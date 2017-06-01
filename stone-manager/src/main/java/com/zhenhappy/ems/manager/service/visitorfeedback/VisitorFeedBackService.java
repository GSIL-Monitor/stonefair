package com.zhenhappy.ems.manager.service.visitorfeedback;

import com.zhenhappy.ems.manager.dto.visitorfeedback.CountVisitorFeedBackInfo;
import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxd on 2017-5-26.
 */
@Service
public class VisitorFeedBackService {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public List<TVisitorFeedBack> loadAllVisitorFeedBack(){
        List<TVisitorFeedBack> visitorFeedBackList = hibernateTemplate.find("from TVisitorFeedBack ", null);
        if(visitorFeedBackList.size()==0){
            return null;
        }else{
            return visitorFeedBackList;
        }
    }

    @Transactional
    public List<CountVisitorFeedBackInfo> loadTVisitorFeedBackByAnswerName(String answerName){
        String hql = "select " + answerName + " , COUNT(*) as count from t_visitor_Feedback group by " + answerName + " order by count desc";
        List<Map<String, Object>> countFeedBackList = jdbcTemplate.queryForList(hql);
        List<CountVisitorFeedBackInfo> countVisitorFeedBackInfoArrayList = new ArrayList<CountVisitorFeedBackInfo>();
        for (int i = 0; i < countFeedBackList.size(); i++) {
            Map exhibitorMap = countFeedBackList.get(i);
            CountVisitorFeedBackInfo temp = new CountVisitorFeedBackInfo();
            Iterator it = exhibitorMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if("count".equalsIgnoreCase(key.toString())){
                    if(value != null){
                        temp.setValue(value.toString());
                    }
                }else {
                    if(value != null){
                        temp.setName(value.toString());
                    }
                }
            }
            countVisitorFeedBackInfoArrayList.add(temp);
        }
        return countVisitorFeedBackInfoArrayList;
    }
}
