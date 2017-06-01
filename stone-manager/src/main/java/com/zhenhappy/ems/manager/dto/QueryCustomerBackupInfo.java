package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wangxd on 2016-12-4.
 */
public class QueryCustomerBackupInfo {
	private Integer id;
	private String email;
	private String checkingNo;
	private String password;
	private String firstName;
	private String lastName;
	private String sex;
	private String company;
	private String position;
	private String country;
	private String province;
	private String city;
	private String address;
	private String backupEmail;
	private String mobilePhoneCode;
	private String mobilePhone;
	private String telephoneCode;
	private String telephone;
	private String telephoneCode2;
	private String faxCode;
	private String fax;
	private String faxCode2;
	private String website;
	private String SelIndustry;
	private String IndustryOthers;
	private String remark;
	private String createdIp;
	private Date createdTime;
	private String updatedIp;
	private Date updateTime;
	private String LastLoginIP;
	private Date LastLoginTime;
	private Integer sendEmailNum;
	private Date sendEmailDate;
	private Integer sendMsgNum;
	private Date sendMsgDate;
	private Integer isActivated;
	private Boolean isMobile;
	private Boolean isjudged;
	private Integer isProfessional;
	private Boolean IsReaded;
	private Boolean isDisabled;
	private String guid;
	private Date backup_date;

	// Constructors

	/** default constructor */
	public QueryCustomerBackupInfo() {
	}

	public QueryCustomerBackupInfo(Integer id, String firstName, String company, String country, String city, String address, String mobilePhone,
								   String telephone, String email, Date createdTime, Date updateTime, Integer isProfessional,
								   Integer isActivated, Date backup_date) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.company = company;
		this.country = country;
		this.city = city;
		this.address = address;
		this.mobilePhone = mobilePhone;
		this.telephone = telephone;
		this.createdTime = createdTime;
		this.updateTime = updateTime;
		this.isProfessional = isProfessional;
		this.isActivated = isActivated;
		this.backup_date = backup_date;
	}

	public QueryCustomerBackupInfo(Integer id, String email, String checkingNo,
								   String password, String firstName, String lastName, String sex,
								   String company, String position, String country, String province,
								   String city, String address, String backupEmail,
								   String mobilePhoneCode, String mobilePhone, String telephoneCode,
								   String telephone, String telephoneCode2, String faxCode,
								   String fax, String faxCode2, String website, String SelIndustry, String IndustryOthers, String remark,
								   String createdIp, Date createdTime, String updatedIp,
								   Date updateTime, String LastLoginIP, Date LastLoginTime, Integer sendEmailNum,Date sendEmailDate, Integer sendMsgNum,
								   Date sendMsgDate, Integer isActivated, Boolean isMobile, Boolean isjudged, Integer isProfessional,
								   Boolean IsReaded, Boolean isDisabled, String guid, Date backup_date) {
		this.id = id;
		this.email = email;
		this.checkingNo = checkingNo;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.company = company;
		this.position = position;
		this.country = country;
		this.province = province;
		this.city = city;
		this.address = address;
		this.backupEmail = backupEmail;
		this.mobilePhoneCode = mobilePhoneCode;
		this.mobilePhone = mobilePhone;
		this.telephoneCode = telephoneCode;
		this.telephone = telephone;
		this.telephoneCode2 = telephoneCode2;
		this.faxCode = faxCode;
		this.fax = fax;
		this.faxCode2 = faxCode2;
		this.website = website;
		this.SelIndustry = SelIndustry;
		this.IndustryOthers = IndustryOthers;
		this.remark = remark;
		this.createdIp = createdIp;
		this.createdTime = createdTime;
		this.updatedIp = updatedIp;
		this.updateTime = updateTime;
		this.LastLoginIP = LastLoginIP;
		this.LastLoginTime = LastLoginTime;
		this.sendEmailNum = sendEmailNum;
		this.sendMsgNum = sendMsgNum;
		this.sendEmailDate = sendEmailDate;
		this.sendMsgDate = sendMsgDate;
		this.isDisabled = isDisabled;
		this.guid = guid;
		this.isProfessional = isProfessional;
		this.isjudged = isjudged;
		this.isMobile = isMobile;
		this.isActivated = isActivated;
		this.IsReaded = IsReaded;
		this.backup_date = backup_date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBackup_date() {
		return backup_date;
	}

	public void setBackup_date(Date backup_date) {
		this.backup_date = backup_date;
	}

	public String getBackupEmail() {
		return backupEmail;
	}

	public void setBackupEmail(String backupEmail) {
		this.backupEmail = backupEmail;
	}

	public String getCheckingNo() {
		return checkingNo;
	}

	public void setCheckingNo(String checkingNo) {
		this.checkingNo = checkingNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreatedIp() {
		return createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFaxCode2() {
		return faxCode2;
	}

	public void setFaxCode2(String faxCode2) {
		this.faxCode2 = faxCode2;
	}

	public String getFaxCode() {
		return faxCode;
	}

	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

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

	public String getIndustryOthers() {
		return IndustryOthers;
	}

	public void setIndustryOthers(String industryOthers) {
		IndustryOthers = industryOthers;
	}

	public Integer getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Integer isActivated) {
		this.isActivated = isActivated;
	}

	public Boolean getDisabled() {
		return isDisabled;
	}

	public void setDisabled(Boolean disabled) {
		isDisabled = disabled;
	}

	public Boolean getIsjudged() {
		return isjudged;
	}

	public void setIsjudged(Boolean isjudged) {
		this.isjudged = isjudged;
	}

	public Boolean getMobile() {
		return isMobile;
	}

	public void setMobile(Boolean mobile) {
		isMobile = mobile;
	}

	public Integer getProfessional() {
		return isProfessional;
	}

	public void setProfessional(Integer professional) {
		isProfessional = professional;
	}

	public Boolean getReaded() {
		return IsReaded;
	}

	public void setReaded(Boolean readed) {
		IsReaded = readed;
	}

	public String getLastLoginIP() {
		return LastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		LastLoginIP = lastLoginIP;
	}

	public Date getLastLoginTime() {
		return LastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		LastLoginTime = lastLoginTime;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhoneCode() {
		return mobilePhoneCode;
	}

	public void setMobilePhoneCode(String mobilePhoneCode) {
		this.mobilePhoneCode = mobilePhoneCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSelIndustry() {
		return SelIndustry;
	}

	public void setSelIndustry(String selIndustry) {
		SelIndustry = selIndustry;
	}

	public Date getSendEmailDate() {
		return sendEmailDate;
	}

	public void setSendEmailDate(Date sendEmailDate) {
		this.sendEmailDate = sendEmailDate;
	}

	public Integer getSendEmailNum() {
		return sendEmailNum;
	}

	public void setSendEmailNum(Integer sendEmailNum) {
		this.sendEmailNum = sendEmailNum;
	}

	public Date getSendMsgDate() {
		return sendMsgDate;
	}

	public void setSendMsgDate(Date sendMsgDate) {
		this.sendMsgDate = sendMsgDate;
	}

	public Integer getSendMsgNum() {
		return sendMsgNum;
	}

	public void setSendMsgNum(Integer sendMsgNum) {
		this.sendMsgNum = sendMsgNum;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephoneCode2() {
		return telephoneCode2;
	}

	public void setTelephoneCode2(String telephoneCode2) {
		this.telephoneCode2 = telephoneCode2;
	}

	public String getTelephoneCode() {
		return telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

	public String getUpdatedIp() {
		return updatedIp;
	}

	public void setUpdatedIp(String updatedIp) {
		this.updatedIp = updatedIp;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
