package com.zhenhappy.ems.manager.dto.companyinfo;

import com.zhenhappy.ems.manager.dto.EasyuiRequest;

/**
 * Created by wangxd on 2016-05-24.
 */
public class QueryHistoryCustomerRequest extends EasyuiRequest {
	private Integer id;
	private String cateory;
	private String company;
	private Integer country;
	private String address;
	private String contact;
	private String position;
	private String telphone;
	private String email;
	private String fixtelphone;
	private String fax;
	private String website;
	private String backupaddress;
	private String remark;
	private Integer owner;
	private String createtime;
	private String updatetime;
	private Integer inlandOrForeign;  //1:表示国内客商  2：表示国外客商

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCateory() {
		return cateory;
	}

	public void setCateory(String cateory) {
		this.cateory = cateory;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getFixtelphone() {
		return fixtelphone;
	}

	public void setFixtelphone(String fixtelphone) {
		this.fixtelphone = fixtelphone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBackupaddress() {
		return backupaddress;
	}

	public void setBackupaddress(String backupaddress) {
		this.backupaddress = backupaddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getInlandOrForeign() {
		return inlandOrForeign;
	}

	public void setInlandOrForeign(Integer inlandOrForeign) {
		this.inlandOrForeign = inlandOrForeign;
	}
}
