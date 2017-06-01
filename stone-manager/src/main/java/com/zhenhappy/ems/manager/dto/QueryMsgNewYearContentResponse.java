package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseResponse;

/**
 * query tags by page.
 * <p/>
 * Created by wangxd on 2016-06-2.
 */
public class QueryMsgNewYearContentResponse extends BaseResponse {
    public String msgContent;

    public QueryMsgNewYearContentResponse() {
    }

    public QueryMsgNewYearContentResponse(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
