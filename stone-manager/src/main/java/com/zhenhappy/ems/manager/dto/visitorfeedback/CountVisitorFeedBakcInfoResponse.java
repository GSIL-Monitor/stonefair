package com.zhenhappy.ems.manager.dto.visitorfeedback;

import com.zhenhappy.ems.dto.BaseResponse;

/**
 * query customers by page.
 * <p/>
 * Created by wangxd on 2016-05-31.
 */
public class CountVisitorFeedBakcInfoResponse extends BaseResponse {
    private String result;
    private Integer total;
    private String showData;

    public CountVisitorFeedBakcInfoResponse() {
    }

    public CountVisitorFeedBakcInfoResponse(String result, Integer total, String showData) {
        this.result = result;
        this.total = total;
        this.showData = showData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getShowData() {
        return showData;
    }

    public void setShowData(String showData) {
        this.showData = showData;
    }
}
