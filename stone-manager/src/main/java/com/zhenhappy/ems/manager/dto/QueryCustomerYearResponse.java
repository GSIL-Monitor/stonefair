package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseResponse;

/**
 * query tags by page.
 * <p/>
 * Created by wangxd on 2016-06-2.
 */
public class QueryCustomerYearResponse extends BaseResponse {
    public String yearData;

    public QueryCustomerYearResponse() {
    }

    public QueryCustomerYearResponse(String yearData) {
        this.yearData = yearData;
    }

    public String getYearData() {
        return yearData;
    }

    public void setYearData(String yearData) {
        this.yearData = yearData;
    }
}
