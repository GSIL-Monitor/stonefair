package com.zhenhappy.ems.dto;

import java.util.Date;

/**
 * Created by wangxd on 2016-04-6.
 */
public class QueryEmailOrMsgInfo {
	private Integer id;
	private Integer CustomerID;
	private Date CreateTime;
	private String CreateIP;
	private Date ConfirmTime;
	private String ConfirmIP;
	private Integer Status;
	private String Admin;
	/** default constructor */
	public QueryEmailOrMsgInfo() {
	}

	/** minimal constructor */
	public QueryEmailOrMsgInfo(Integer id) {
		this.id = id;
	}

	public QueryEmailOrMsgInfo(Integer id, Integer customerID, Date createTime, String createIP, Date confirmTime, String confirmIP,
							   Integer status, String admin) {
		this.id = id;
		CustomerID = customerID;
		CreateTime = createTime;
		CreateIP = createIP;
		ConfirmTime = confirmTime;
		ConfirmIP = confirmIP;
		Status = status;
		Admin = admin;
	}

	public QueryEmailOrMsgInfo(Integer customerID, Date createTime, String createIP, Date confirmTime, String confirmIP,
							   Integer status, String admin) {
		CustomerID = customerID;
		CreateTime = createTime;
		CreateIP = createIP;
		ConfirmTime = confirmTime;
		ConfirmIP = confirmIP;
		Status = status;
		Admin = admin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(Integer customerID) {
		CustomerID = customerID;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getCreateIP() {
		return CreateIP;
	}

	public void setCreateIP(String createIP) {
		CreateIP = createIP;
	}

	public Date getConfirmTime() {
		return ConfirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		ConfirmTime = confirmTime;
	}

	public String getConfirmIP() {
		return ConfirmIP;
	}

	public void setConfirmIP(String confirmIP) {
		ConfirmIP = confirmIP;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getAdmin() {
		return Admin;
	}

	public void setAdmin(String admin) {
		Admin = admin;
	}
}
