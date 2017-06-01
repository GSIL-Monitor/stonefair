package com.zhenhappy.ems.manager.dto.companyinfo;

import com.zhenhappy.ems.manager.dto.EasyuiResponse;

/**
 * query customers by page.
 * <p/>
 * Created by wangxd on 2016-05-24.
 */
public class QueryHistoryCustomerResponse extends EasyuiResponse {
    private Integer canOperateOwnerFalg;

    public Integer getCanOperateOwnerFalg() {
        return canOperateOwnerFalg;
    }

    public void setCanOperateOwnerFalg(Integer canOperateOwnerFalg) {
        this.canOperateOwnerFalg = canOperateOwnerFalg;
    }
}
