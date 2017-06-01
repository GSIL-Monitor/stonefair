package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.entity.TVisitorTemplate;
import com.zhenhappy.ems.manager.dto.QueryEmailTemplateRequest;
import com.zhenhappy.ems.manager.dto.QueryEmailTemplateResponse;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by wangxd on 2016-03-30.
 */
public interface CustomerTemplateService {

    @Transactional
    public List<TVisitorTemplate> loadAllCustomerTemplate();

    @Transactional
    public QueryEmailTemplateResponse queryEmailManagerTemplate(QueryEmailTemplateRequest request) throws UnsupportedEncodingException;

    @Transactional
    public void modifyEmailManagerTemplate(String value1, String value2, String value3, String value4, String value5,
                                           String value6, String value7, String value8, String value9, String value10,
                                           String value11, String value12, String value13, String value14) throws Exception;

    @Transactional
    public void modifyMessageManagerTemplate(String value1, String value2, String value3, String value4, String value5,
                                           String value6, String value7, String value8, String value9,
                                             String value10, String value11, String value12, String value13, String value14,
                                             String value15, String value16, String value17) throws Exception;
}
