package com.zhenhappy.ems.manager.dto.companyinfo;

import com.zhenhappy.ems.manager.entity.companyinfo.THistoryCustomer;

/**
 * Created by wangxd on 2016/5/24.
 */
public class ExportHistoryCustomerInfo extends THistoryCustomer {
    private String countryEx;
    private String contactEx;

    public String getCountryEx() {
        return countryEx;
    }

    public void setCountryEx(String countryEx) {
        this.countryEx = countryEx;
    }

    public String getContactEx() {
        return contactEx;
    }

    public void setContactEx(String contactEx) {
        this.contactEx = contactEx;
    }
}
