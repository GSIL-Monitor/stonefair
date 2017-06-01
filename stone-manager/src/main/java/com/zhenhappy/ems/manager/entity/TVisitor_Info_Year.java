package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangxd on 2016-12-15.
 */
@Entity
@Table(name = "visitor_Info_Year")
public class TVisitor_Info_Year {
    private Integer id;
    private Integer CustomerID;
    private Integer WThInfoID;
    private String WSC;
    private Date CreateTime;
    private String CreateIP;
    private Date UpdateTime;
    private String UpdateIP;
    private String GUID;

    public TVisitor_Info_Year() {
    }

    public TVisitor_Info_Year(String createIP, Date createTime, Integer customerID, Integer WThInfoID, String GUID,
                              Integer id, String updateIP, Date updateTime, String WSC) {
        this.CreateIP = createIP;
        this.CreateTime = createTime;
        this.CustomerID = customerID;
        this.WThInfoID = WThInfoID;
        this.GUID = GUID;
        this.id = id;
        this.UpdateIP = updateIP;
        this.UpdateTime = updateTime;
        this.WSC = WSC;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "WThInfoID")
    public Integer getWThInfoID() {
        return WThInfoID;
    }

    public void setWThInfoID(Integer WThInfoID) {
        this.WThInfoID = WThInfoID;
    }

    @Column(name = "CreateIP")
    public String getCreateIP() {
        return CreateIP;
    }

    public void setCreateIP(String createIP) {
        CreateIP = createIP;
    }

    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    @Column(name = "CustomerID")
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    @Column(name = "GUID")
    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    @Column(name = "UpdateIP")
    public String getUpdateIP() {
        return UpdateIP;
    }

    public void setUpdateIP(String updateIP) {
        UpdateIP = updateIP;
    }

    @Column(name = "UpdateTime")
    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    @Column(name = "WSC")
    public String getWSC() {
        return WSC;
    }

    public void setWSC(String WSC) {
        this.WSC = WSC;
    }
}

