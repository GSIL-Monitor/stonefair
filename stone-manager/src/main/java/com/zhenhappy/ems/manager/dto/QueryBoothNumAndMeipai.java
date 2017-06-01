package com.zhenhappy.ems.manager.dto;

/**
 * Created by wujianbin on 2014-07-21.
 */
public class QueryBoothNumAndMeipai {
	private Integer eid;
	private String tagName;
	private String boothNumber;
	private String companyZh;
	private String companyEn;
	private String contractID;
	private String meipai;
	private String meipaiEn;
	private String areaNum;

	public String getAreaNum() {
		return areaNum;
	}

	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}

	public String getBoothNumber() {
		return boothNumber;
	}

	public void setBoothNumber(String boothNumber) {
		this.boothNumber = boothNumber;
	}

	public String getCompanyEn() {
		return companyEn;
	}

	public void setCompanyEn(String companyEn) {
		this.companyEn = companyEn;
	}

	public String getCompanyZh() {
		return companyZh;
	}

	public void setCompanyZh(String companyZh) {
		this.companyZh = companyZh;
	}

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getMeipai() {
		return meipai;
	}

	public void setMeipai(String meipai) {
		this.meipai = meipai;
	}

	public String getMeipaiEn() {
		return meipaiEn;
	}

	public void setMeipaiEn(String meipaiEn) {
		this.meipaiEn = meipaiEn;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
