package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-04-11.
 */
@Entity
@Table(name = "visitor_Info_Survey", schema = "dbo")
public class TVisitor_Info_Survey {
    private Integer id;
    private Integer wCustomerInfoID;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;
    private String q7;
    private String q8;
    private String q9;
    private String q10;
    private String remark1;
    private String remark2;
    private String inviterEmail;
    private String inviterName;
    private String emailSubject;
    private String createdIP;
    private Date createdTime;
    private String updatedIP;
    private Date updateTime;
    private boolean disabledFlag;
    private String wsc;

    public TVisitor_Info_Survey() {
        super();
    }

    public TVisitor_Info_Survey(Integer id) {
        this.id = id;
    }

    public TVisitor_Info_Survey(Integer id, Integer WCustomerInfoID, String q1, String q2, String q3, String q4,
                                String q5, String q6, String q7, String q8, String q9, String q10,
                                String remark1, String remark2, String inviterEmail, String inviterName,
                                String emailSubject, String createdIP, Date createdTime, String updatedIP,
                                Date updateTime, boolean isDisabled, String wsc) {
        this.id = id;
        this.wCustomerInfoID = WCustomerInfoID;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
        this.q10 = q10;
        this.remark1 = remark1;
        this.remark2 = remark2;
        this.inviterEmail = inviterEmail;
        this.inviterName = inviterName;
        this.emailSubject = emailSubject;
        this.createdIP = createdIP;
        this.createdTime = createdTime;
        this.updatedIP = updatedIP;
        this.updateTime = updateTime;
        this.disabledFlag = isDisabled;
        this.wsc = wsc;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "CustomerID")
    public Integer getwCustomerInfoID() {
        return wCustomerInfoID;
    }

    public void setwCustomerInfoID(Integer wCustomerInfoID) {
        this.wCustomerInfoID = wCustomerInfoID;
    }

    @Column(name = "Q1")
    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    @Column(name = "Q2")
    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    @Column(name = "Q3")
    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    @Column(name = "Q4")
    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    @Column(name = "Q5")
    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    @Column(name = "Q6")
    public String getQ6() {
        return q6;
    }

    public void setQ6(String q6) {
        this.q6 = q6;
    }

    @Column(name = "Q7")
    public String getQ7() {
        return q7;
    }

    public void setQ7(String q7) {
        this.q7 = q7;
    }

    @Column(name = "Q8")
    public String getQ8() {
        return q8;
    }

    public void setQ8(String q8) {
        this.q8 = q8;
    }

    @Column(name = "Q9")
    public String getQ9() {
        return q9;
    }

    public void setQ9(String q9) {
        this.q9 = q9;
    }

    @Column(name = "Q10")
    public String getQ10() {
        return q10;
    }

    public void setQ10(String q10) {
        this.q10 = q10;
    }

    @Column(name = "Remark1")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    @Column(name = "Remark2")
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @Column(name = "InviterEmail")
    public String getInviterEmail() {
        return inviterEmail;
    }

    public void setInviterEmail(String inviterEmail) {
        this.inviterEmail = inviterEmail;
    }

    @Column(name = "InviterName")
    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    @Column(name = "EmailSubject")
    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Column(name = "CreateIP")
    public String getCreatedIP() {
        return createdIP;
    }

    public void setCreatedIP(String createdIP) {
        this.createdIP = createdIP;
    }

    @Column(name = "CreateTime")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "UpdateIP")
    public String getUpdatedIP() {
        return updatedIP;
    }

    public void setUpdatedIP(String updatedIP) {
        this.updatedIP = updatedIP;
    }

    @Column(name = "UpdateTime")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "IsDisabled")
    public boolean getDisabledFlag() {
        return disabledFlag;
    }

    public void setDisabledFlag(boolean disabledFlag) {
        this.disabledFlag = disabledFlag;
    }

    @Column(name = "WSC")
    public String getWsc() {
        return wsc;
    }

    public void setWsc(String wsc) {
        this.wsc = wsc;
    }

}
