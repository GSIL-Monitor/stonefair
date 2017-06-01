package com.zhenhappy.ems.manager.dto.companyinfo;

import com.zhenhappy.ems.dto.BaseResponse;

/**
 * query customers by page.
 * <p/>
 * Created by wangxd on 2016-05-31.
 */
public class ImportHistoryCustomerResponse extends BaseResponse {
    public String result;
    public String isExistData;
    public String willImportData;

    public ImportHistoryCustomerResponse() {
    }

    public ImportHistoryCustomerResponse(String result, String isExistData, String willImportData) {
        this.result = result;
        this.isExistData = isExistData;
        this.willImportData = willImportData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIsExistData() {
        return isExistData;
    }

    public void setIsExistData(String isExistData) {
        this.isExistData = isExistData;
    }

    public String getWillImportData() {
        return willImportData;
    }

    public void setWillImportData(String willImportData) {
        this.willImportData = willImportData;
    }
}
