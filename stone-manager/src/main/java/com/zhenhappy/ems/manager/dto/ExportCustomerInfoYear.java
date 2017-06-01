package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wangxd on 2016-4-11.
 */
public class ExportCustomerInfoYear {
	private Integer id;
	private Integer customerID;
	private Integer wThInfoID;
	private String wSC;
	private Date createTime;
	private String createIP;
	private Date updateTime;
	private String updateIP;
	private String gUID;

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

	public Integer getwThInfoID() {
		return wThInfoID;
	}

	public void setwThInfoID(Integer wThInfoID) {
		this.wThInfoID = wThInfoID;
	}

	public String getwSC() {
		return wSC;
	}

	public void setwSC(String wSC) {
		this.wSC = wSC;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateIP() {
		return createIP;
	}

	public void setCreateIP(String createIP) {
		this.createIP = createIP;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateIP() {
		return updateIP;
	}

	public void setUpdateIP(String updateIP) {
		this.updateIP = updateIP;
	}

	public String getgUID() {
		return gUID;
	}

	public void setgUID(String gUID) {
		this.gUID = gUID;
	}
}
