package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wangxd on 2016-4-11.
 */
public class ExportCustomerSurvey {
	private Integer id;
	private Integer wCustomerInfoID;
	private String customerName;
	private String company;
	private String telphone;
	private String email;
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
	private boolean isDisabled;
	private String wsc;

	public boolean getisDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean disabled) {
		isDisabled = disabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getwCustomerInfoID() {
		return wCustomerInfoID;
	}

	public void setwCustomerInfoID(Integer wCustomerInfoID) {
		this.wCustomerInfoID = wCustomerInfoID;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	public String getQ4() {
		return q4;
	}

	public void setQ4(String q4) {
		this.q4 = q4;
	}

	public String getQ5() {
		return q5;
	}

	public void setQ5(String q5) {
		this.q5 = q5;
	}

	public String getQ6() {
		return q6;
	}

	public void setQ6(String q6) {
		this.q6 = q6;
	}

	public String getQ7() {
		return q7;
	}

	public void setQ7(String q7) {
		this.q7 = q7;
	}

	public String getQ8() {
		return q8;
	}

	public void setQ8(String q8) {
		this.q8 = q8;
	}

	public String getQ9() {
		return q9;
	}

	public void setQ9(String q9) {
		this.q9 = q9;
	}

	public String getQ10() {
		return q10;
	}

	public void setQ10(String q10) {
		this.q10 = q10;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getInviterEmail() {
		return inviterEmail;
	}

	public void setInviterEmail(String inviterEmail) {
		this.inviterEmail = inviterEmail;
	}

	public String getInviterName() {
		return inviterName;
	}

	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getCreatedIP() {
		return createdIP;
	}

	public void setCreatedIP(String createdIP) {
		this.createdIP = createdIP;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedIP() {
		return updatedIP;
	}

	public void setUpdatedIP(String updatedIP) {
		this.updatedIP = updatedIP;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWsc() {
		return wsc;
	}

	public void setWsc(String wsc) {
		this.wsc = wsc;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
