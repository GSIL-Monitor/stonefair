package com.zhenhappy.ems.manager.dto;

/**
 * Created by wujianbin on 2014-08-14.
 */
public class QueryCustomerRequest extends EasyuiRequest {
	private Integer id;
	private String firstName;
	private String password;
	private String company;
	private Integer country;
	private String address;
	private String position;
	private String city;
	private String mobilePhone;
	private String telephone;
	private String email;
	private String createTime;
	private String updateTime;
	private String website;
	private String fax;
	private Integer isProfessional;
	private Integer inlandOrForeign;  //1:表示国内客商  2：表示国外客商
	private Integer isActivated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInlandOrForeign() {
		return inlandOrForeign;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setInlandOrForeign(Integer inlandOrForeign) {
		this.inlandOrForeign = inlandOrForeign;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createdTime) {
		this.createTime = createdTime;
	}

	public Integer getIsProfessional() {
		return isProfessional;
	}

	public void setIsProfessional(Integer isProfessional) {
		this.isProfessional = isProfessional;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Integer isActivated) {
		this.isActivated = isActivated;
	}
}
