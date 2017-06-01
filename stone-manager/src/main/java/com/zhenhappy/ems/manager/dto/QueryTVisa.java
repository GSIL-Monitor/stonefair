package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wujianbin on 2014-07-14.
 */
public class QueryTVisa {
	private Integer id;
	private String passportName;
	private String passportPage;
	private String nationality;
	private String companyName;
	private Date updateTime;
	private Date createTime;

	public QueryTVisa() {
		super();
	}

	public QueryTVisa(Integer id) {
		super();
		this.id = id;
	}

	public QueryTVisa(Integer id, String passportName, String nationality, String companyName, Date updateTime, Date createTime, String passportPage) {
		this.companyName = companyName;
		this.createTime = createTime;
		this.id = id;
		this.nationality = nationality;
		this.passportName = passportName;
		this.updateTime = updateTime;
		this.passportPage = passportPage;
	}

	public String getPassportPage() {
		return passportPage;
	}

	public void setPassportPage(String passportPage) {
		this.passportPage = passportPage;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassportName() {
		return passportName;
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
