package com.zhenhappy.ems.manager.entity;

import com.zhenhappy.ems.entity.WCustomer;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-06-21.
 */
public class WCustomerEx extends WCustomer{
    private String countryValue;

    public String getCountryValue() {
        return countryValue;
    }

    public void setCountryValue(String countryValue) {
        this.countryValue = countryValue;
    }
}
