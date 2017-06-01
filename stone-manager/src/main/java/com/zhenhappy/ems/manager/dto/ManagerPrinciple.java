package com.zhenhappy.ems.manager.dto;


import com.zhenhappy.ems.entity.managerrole.TUserInfo;

/**
 * Created by lianghaijian on 2014-04-22.
 */
public class ManagerPrinciple {

    /**
     * the name of principle stored in session.
     */
    public static final String MANAGERPRINCIPLE = "MANAGERPRINCIPLE";

    private TUserInfo admin;
    private String currentOperationIds;

    public TUserInfo getAdmin() {
        return admin;
    }

    public void setAdmin(TUserInfo admin) {
        this.admin = admin;
    }

    public String getCurrentOperationIds() {
        return currentOperationIds;
    }

    public void setCurrentOperationIds(String currentOperationIds) {
        this.currentOperationIds = currentOperationIds;
    }
}
