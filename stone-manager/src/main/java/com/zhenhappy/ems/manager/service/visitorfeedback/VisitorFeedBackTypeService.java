package com.zhenhappy.ems.manager.service.visitorfeedback;

import com.zhenhappy.ems.manager.entity.visitorfeedback.TVisitorFeedBackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2017-5-26.
 */
@Service
public class VisitorFeedBackTypeService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional
    public List<TVisitorFeedBackType> loadAllVisitorFeedBackType(){
        List<TVisitorFeedBackType> visitorFeedBackTypeList = hibernateTemplate.find("from TVisitorFeedBackType ", null);
        if(visitorFeedBackTypeList.size()==0){
            return null;
        }else{
            return visitorFeedBackTypeList;
        }
    }

    @Transactional
    public TVisitorFeedBackType loadTVisitorFeedBackTypeById(Integer id){
        List<TVisitorFeedBackType> visitorFeedBackTypeList = hibernateTemplate.find("from TVisitorFeedBackType where id=? ", new Object[]{id});
        if(visitorFeedBackTypeList.size()==0){
            return null;
        }else{
            return visitorFeedBackTypeList.get(0);
        }
    }
}
