package com.zhenhappy.ems.manager.entity;

/**
 * Created by wangxd on 2011-11-22.
 */

public class PreviewExhibitorInvitation {
    public Integer eid;           //展商eid
    public String contactName;   //联系人姓名
    public String boothConfirm;  //展位确认涵

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getBoothConfirm() {
        return boothConfirm;
    }

    public void setBoothConfirm(String boothConfirm) {
        this.boothConfirm = boothConfirm;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
