package com.zhenhappy.ems.dto;

import java.util.Date;

/**
 * Created by wangxd on 2016-04-13.
 */
public class QueryEmailOrMsgRequest extends EasyuiRequest {
	private Integer id;
	private Integer customerID;
	private String createTime;
	private String createIP;
	private String confirmTime;
	private String confirmIP;
	private Integer status;
	private String admin;
	private String guid;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateIP() {
		return createIP;
	}

	public void setCreateIP(String createIP) {
		this.createIP = createIP;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmIP() {
		return confirmIP;
	}

	public void setConfirmIP(String confirmIP) {
		this.confirmIP = confirmIP;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
