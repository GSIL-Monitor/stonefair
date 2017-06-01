package com.zhenhappy.ems.entity;

import java.util.List;

public class ExhibitorInvisitorEmail {
	private String sendEamilAccount;        //发送者的邮箱账号
	private String sendEmailPassword;       //发送者的邮箱密码
	private String companyName;   			//公司名
	private String boothNumber;   		    //展位号
	private String userName;      			//用户名
	private String password;      		    //密码
	private String exhibitorArea; 		    //展位面积
	private List<TContact> contactList;     //联系人
	private String rawSpaceOrShellScheme;   //空地或标摊

	public ExhibitorInvisitorEmail() {
	}

	public ExhibitorInvisitorEmail(String sendEamilAccount, String sendEmailPassword, String boothNumber, String companyName,
								   List<TContact> contactList, String exhibitorArea, String password, String userName, String rawSpaceOrShellScheme) {
		this.sendEamilAccount = sendEamilAccount;
		this.sendEmailPassword = sendEmailPassword;
		this.boothNumber = boothNumber;
		this.companyName = companyName;
		this.contactList = contactList;
		this.exhibitorArea = exhibitorArea;
		this.password = password;
		this.userName = userName;
		this.rawSpaceOrShellScheme = rawSpaceOrShellScheme;
	}

	public String getSendEamilAccount() {
		return sendEamilAccount;
	}

	public void setSendEamilAccount(String sendEamilAccount) {
		this.sendEamilAccount = sendEamilAccount;
	}

	public String getSendEmailPassword() {
		return sendEmailPassword;
	}

	public void setSendEmailPassword(String sendEmailPassword) {
		this.sendEmailPassword = sendEmailPassword;
	}

	public String getBoothNumber() {
		return boothNumber;
	}

	public void setBoothNumber(String boothNumber) {
		this.boothNumber = boothNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<TContact> getContactList() {
		return contactList;
	}

	public void setContactList(List<TContact> contactList) {
		this.contactList = contactList;
	}

	public String getExhibitorArea() {
		return exhibitorArea;
	}

	public void setExhibitorArea(String exhibitorArea) {
		this.exhibitorArea = exhibitorArea;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRawSpaceOrShellScheme() {
		return rawSpaceOrShellScheme;
	}

	public void setRawSpaceOrShellScheme(String rawSpaceOrShellScheme) {
		this.rawSpaceOrShellScheme = rawSpaceOrShellScheme;
	}
}
