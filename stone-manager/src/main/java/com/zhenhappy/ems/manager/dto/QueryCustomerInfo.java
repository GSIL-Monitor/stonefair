package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wangxd on 2016-04-6.
 */
public class QueryCustomerInfo {
	private Integer id;
	private String email;
	private String checkingNo;
	private String password;
	private String firstName;
	private String lastName;
	private String sex;
	private String company;
	private String position;
	private Integer country;
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
	private String remark;
	private String createdIp;
	private Date createdTime;
	private String updatedIp;
	private Date updateTime;
	private Integer sendEmailNum;
	private Date sendEmailDate;
	private Boolean isDisabled;
	private String guid;
	private Integer isProfessional;
	private Boolean isjudged;
	private Integer isActivated;

	// Constructors

	/** default constructor */
	public QueryCustomerInfo() {
	}

	/** minimal constructor */
	public QueryCustomerInfo(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public QueryCustomerInfo(Integer id, String email, String checkingNo,
					 String password, String firstName, String lastName, String sex,
					 String company, String position, Integer country, String province,
					 String city, String address, String backupEmail,
					 String mobilePhoneCode, String mobilePhone, String telephoneCode,
					 String telephone, String telephoneCode2, String faxCode,
					 String fax, String faxCode2, String website, String remark,
					 String createdIp, Date createdTime, String updatedIp,
					 Date updateTime, Integer sendEmailNum,
					 Date sendEmailDate, Boolean isDisabled, String guid) {
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
		this.remark = remark;
		this.createdIp = createdIp;
		this.createdTime = createdTime;
		this.updatedIp = updatedIp;
		this.updateTime = updateTime;
		this.sendEmailNum = sendEmailNum;
		this.sendEmailDate = sendEmailDate;
		this.isDisabled = isDisabled;
		this.guid = guid;
	}

	public QueryCustomerInfo(Integer id, String firstName, String company, String city, String address,
							String mobile, String tel, String email, Date createTime, Date updateTime, Integer isProfessional, Integer isActivated) {
		this.id = id;
		this.firstName = firstName;
		this.company = company;
		this.city = city;
		this.address = address;
		this.mobilePhone = mobile;
		this.telephone = tel;
		this.email = email;
		this.createdTime = createTime;
		this.updateTime = updateTime;
		this.isProfessional = isProfessional;
		this.isActivated = isActivated;
	}

	public QueryCustomerInfo(Integer id, String firstName, String company, Integer country, String address,
							String mobile, String tel, String email, Date createTime, Date updateTime, Integer isProfessional, Integer isActivated) {
		this.id = id;
		this.firstName = firstName;
		this.company = company;
		this.country = country;
		this.address = address;
		this.mobilePhone = mobile;
		this.telephone = tel;
		this.email = email;
		this.createdTime = createTime;
		this.updateTime = updateTime;
		this.isProfessional = isProfessional;
		this.isActivated = isActivated;
	}

	public QueryCustomerInfo(Integer id, String email, String checkingNo,
					 String password, String firstName, String lastName, String sex,
					 String company, String position, Integer country, String province,
					 String city, String address, String backupEmail,
					 String mobilePhoneCode, String mobilePhone, String telephoneCode,
					 String telephone, String telephoneCode2, String faxCode,
					 String fax, String faxCode2, String website, String remark,
					 String createdIp, Date createdTime, String updatedIp,
					 Date updateTime, Integer sendEmailNum,
					 Date sendEmailDate, Boolean isDisabled, String guid, Integer isProfessional, Boolean isjudged) {
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
		this.remark = remark;
		this.createdIp = createdIp;
		this.createdTime = createdTime;
		this.updatedIp = updatedIp;
		this.updateTime = updateTime;
		this.sendEmailNum = sendEmailNum;
		this.sendEmailDate = sendEmailDate;
		this.isDisabled = isDisabled;
		this.guid = guid;
		this.isProfessional = isProfessional;
		this.isjudged = isjudged;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCheckingNo() {
		return this.checkingNo;
	}

	public void setCheckingNo(String checkingNo) {
		this.checkingNo = checkingNo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getCountry() {
		return this.country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBackupEmail() {
		return this.backupEmail;
	}

	public void setBackupEmail(String backupEmail) {
		this.backupEmail = backupEmail;
	}

	public String getMobilePhoneCode() {
		return this.mobilePhoneCode;
	}

	public void setMobilePhoneCode(String mobilePhoneCode) {
		this.mobilePhoneCode = mobilePhoneCode;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephoneCode() {
		return this.telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephoneCode2() {
		return this.telephoneCode2;
	}

	public void setTelephoneCode2(String telephoneCode2) {
		this.telephoneCode2 = telephoneCode2;
	}

	public String getFaxCode() {
		return this.faxCode;
	}

	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFaxCode2() {
		return this.faxCode2;
	}

	public void setFaxCode2(String faxCode2) {
		this.faxCode2 = faxCode2;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatedIp() {
		return this.createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedIp() {
		return this.updatedIp;
	}

	public void setUpdatedIp(String updatedIp) {
		this.updatedIp = updatedIp;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSendEmailNum() {
		return this.sendEmailNum;
	}

	public void setSendEmailNum(Integer sendEmailNum) {
		this.sendEmailNum = sendEmailNum;
	}

	public Date getSendEmailDate() {
		return this.sendEmailDate;
	}

	public void setSendEmailDate(Date sendEmailDate) {
		this.sendEmailDate = sendEmailDate;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Boolean getIsjudged() {
		return isjudged;
	}

	public void setIsjudged(Boolean isjudged) {
		this.isjudged = isjudged;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean disabled) {
		isDisabled = disabled;
	}

	public Integer getIsProfessional() {
		return isProfessional;
	}

	public void setIsProfessional(Integer professional) {
		isProfessional = professional;
	}

	public Integer getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Integer isActivated) {
		this.isActivated = isActivated;
	}
}
