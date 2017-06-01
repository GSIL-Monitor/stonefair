package com.zhenhappy.ems.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * WcustomerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "visitor_Apply_Msg", schema = "dbo")
public class VApplyMsg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer customerID;
	private Date createTime;
	private String createIP;
	private Date confirmTime;
	private String confirmIP;
	private Integer status;
	private String admin;
	private String guid;

	// Constructors

	/** default constructor */
	public VApplyMsg() {
	}

	/** minimal constructor */
	public VApplyMsg(Integer id) {
		this.id = id;
	}

	public VApplyMsg(Integer customerID, Date createTime, String createIP, Date confirmTime, String confirmIP,
					   Integer status, String admin) {
		this.customerID = customerID;
		this.createTime = createTime;
		this.createIP = createIP;
		this.confirmTime = confirmTime;
		this.confirmIP = confirmIP;
		this.status = status;
		this.admin = admin;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CustomerID", nullable = false)
	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	@Column(name = "CreateTime", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CreateIP")
	public String getCreateIP() {
		return createIP;
	}

	public void setCreateIP(String createIP) {
		this.createIP = createIP;
	}

	@Column(name = "ConfirmTime")
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Column(name = "ConfirmIP")
	public String getConfirmIP() {
		return confirmIP;
	}

	public void setConfirmIP(String confirmIP) {
		this.confirmIP = confirmIP;
	}

	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "Admin")
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	@Column(name = "GUID")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}