package com.zhenhappy.ems.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * WcustomerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "visitor_Info", schema = "dbo")
public class WCustomer implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// Fields

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
	private Integer sendMsgNum;
	private Date sendEmailDate;
	private Date sendMsgDate;
	private Integer isActivated;
	private Boolean isMobile;
	private Boolean isjudged;
	private Integer isProfessional;
	private Integer IsReaded;
	private Boolean isDisabled;
	private String guid;

	// Constructors

	/** default constructor */
	public WCustomer() {
	}


	/** minimal constructor */
	public WCustomer(Integer id) {
		this.id = id;
	}

	public WCustomer(Integer id, String email, String checkingNo,
					 String password, String firstName, String lastName, String sex,
					 String company, String position, Integer country, String province,
					 String city, String address, String backupEmail,
					 String mobilePhoneCode, String mobilePhone, String telephoneCode,
					 String telephone, String telephoneCode2, String faxCode,
					 String fax, String faxCode2, String website, String remark,
					 String createdIp, Date createdTime, String updatedIp,
					 Date updateTime, Integer sendEmailNum,Integer sendMsgNum,
					 Date sendEmailDate, Date sendMsgDate, Boolean isDisabled, String guid, Integer isProfessional, Boolean isjudged,
					 Boolean isMobile, Integer isActivated) {
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
		this.sendMsgNum = sendMsgNum;
		this.sendEmailDate = sendEmailDate;
		this.sendMsgDate = sendMsgDate;
		this.isDisabled = isDisabled;
		this.guid = guid;
		this.isProfessional = isProfessional;
		this.isjudged = isjudged;
		this.isMobile = isMobile;
		this.isActivated = isActivated;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Email", length = 250)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CheckingNo")
	public String getCheckingNo() {
		return this.checkingNo;
	}

	public void setCheckingNo(String checkingNo) {
		this.checkingNo = checkingNo;
	}

	@Column(name = "Password", length = 250)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "FirstName")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LastName")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "Sex")
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "Company")
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "Position")
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "Country")
	public Integer getCountry() {
		return this.country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	@Column(name = "Province", length = 50)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "City")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "Address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "BackupEmail", length = 250)
	public String getBackupEmail() {
		return this.backupEmail;
	}

	public void setBackupEmail(String backupEmail) {
		this.backupEmail = backupEmail;
	}

	@Column(name = "MobilePhoneCode", length = 50)
	public String getMobilePhoneCode() {
		return this.mobilePhoneCode;
	}

	public void setMobilePhoneCode(String mobilePhoneCode) {
		this.mobilePhoneCode = mobilePhoneCode;
	}

	@Column(name = "MobilePhone", length = 250)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "TelephoneCode", length = 50)
	public String getTelephoneCode() {
		return this.telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

	@Column(name = "Telephone", length = 250)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "TelephoneCode2")
	public String getTelephoneCode2() {
		return this.telephoneCode2;
	}

	public void setTelephoneCode2(String telephoneCode2) {
		this.telephoneCode2 = telephoneCode2;
	}

	@Column(name = "FaxCode", length = 50)
	public String getFaxCode() {
		return this.faxCode;
	}

	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}

	@Column(name = "Fax", length = 250)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "FaxCode2")
	public String getFaxCode2() {
		return this.faxCode2;
	}

	public void setFaxCode2(String faxCode2) {
		this.faxCode2 = faxCode2;
	}

	@Column(name = "Website", length = 250)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "Remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CreateIP")
	public String getCreatedIp() {
		return this.createdIp;
	}

	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}

	@Column(name = "CreateTime", length = 23)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "UpdateIP")
	public String getUpdatedIp() {
		return this.updatedIp;
	}

	public void setUpdatedIp(String updatedIp) {
		this.updatedIp = updatedIp;
	}

	@Column(name = "UpdateTime", length = 23)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "SendEmailNum")
	public Integer getSendEmailNum() {
		return this.sendEmailNum;
	}

	public void setSendEmailNum(Integer sendEmailNum) {
		this.sendEmailNum = sendEmailNum;
	}

	@Column(name = "SendEmailDate", length = 23)
	public Date getSendEmailDate() {
		return this.sendEmailDate;
	}

	public void setSendEmailDate(Date sendEmailDate) {
		this.sendEmailDate = sendEmailDate;
	}

	@Column(name = "GUID")
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "Isjudged")
	public Boolean getIsjudged() {
		return isjudged;
	}

	public void setIsjudged(Boolean isjudged) {
		this.isjudged = isjudged;
	}

	@Column(name = "IsDisabled")
	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean disabled) {
		isDisabled = disabled;
	}

	@Column(name = "IsProfessional")
	public Integer getIsProfessional() {
		return isProfessional;
	}

	public void setIsProfessional(Integer professional) {
		isProfessional = professional;
	}

	@Column(name = "IsMobile")
	public Boolean getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Boolean mobile) {
		isMobile = mobile;
	}

	@Column(name = "SendMsgNum")
	public Integer getSendMsgNum() {
		return sendMsgNum;
	}

	public void setSendMsgNum(Integer sendMsgNum) {
		this.sendMsgNum = sendMsgNum;
	}

	@Column(name = "SendMsgDate", length = 23)
	public Date getSendMsgDate() {
		return sendMsgDate;
	}

	public void setSendMsgDate(Date sendMsgDate) {
		this.sendMsgDate = sendMsgDate;
	}

	@Column(name = "IsReaded")
	public Integer getIsReaded() {
		return IsReaded;
	}

	public void setIsReaded(Integer isReaded) {
		IsReaded = isReaded;
	}

	@Column(name = "IsActivated")
	public Integer getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Integer isActivated) {
		this.isActivated = isActivated;
	}
}