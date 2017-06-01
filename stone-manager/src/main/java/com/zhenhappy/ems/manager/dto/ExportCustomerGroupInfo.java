package com.zhenhappy.ems.manager.dto;

import java.util.Date;

/**
 * Created by wujianbin on 2014-12-13.
 */
public class ExportCustomerGroupInfo {
	private String groupName;       //参观团名称
	private String identify;        //身份：团长/团员
	private String checkingNo;		//预登记确认号
	private String memberName;		//姓名
	private String company;			//公司
	private String email;			//邮箱
	private String position;		//职位
	private String mobile;			//手机
	private String address;			//地址
	private Date createTime;        //参观团成员注册时间
	private Date updateTime;        //参观团成员更新时间
	private Integer emailNum;		//邮件数量
	private Integer msgNum;			//短信数量

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getCheckingNo() {
		return checkingNo;
	}

	public void setCheckingNo(String checkingNo) {
		this.checkingNo = checkingNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getEmailNum() {
		return emailNum;
	}

	public void setEmailNum(Integer emailNum) {
		this.emailNum = emailNum;
	}

	public Integer getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(Integer msgNum) {
		this.msgNum = msgNum;
	}
}
