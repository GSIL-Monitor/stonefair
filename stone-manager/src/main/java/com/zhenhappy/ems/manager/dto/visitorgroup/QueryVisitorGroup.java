package com.zhenhappy.ems.manager.dto.visitorgroup;

import java.util.Date;

/**
 * Created by wangxd on 2017-02-09.
 */
public class QueryVisitorGroup {
	private Integer Id;
	private Integer LeaderID;
	private String GroupName;
	private String MemberName;
	private String Mobile;
	private String Position;
	private String Email;
	private String Address;
	private Date CreateTime;
	private Date UpdateTime;
	private Integer IsDisabled;
	private Integer EmailNum;
	private Integer MsgNum;

	public QueryVisitorGroup() {
		super();
	}

	public QueryVisitorGroup(Integer id, Integer leaderID, String groupName, String memberName, String mobile, String position,
							 String email,String address, Date createTime, Date updateTime, Integer isDisabled, Integer emailNum, Integer msgNum) {
		Id = id;
		LeaderID = leaderID;
		Address = address;
		CreateTime = createTime;
		Email = email;
		GroupName = groupName;
		MemberName = memberName;
		Mobile = mobile;
		Position = position;
		UpdateTime = updateTime;
		IsDisabled = isDisabled;
		EmailNum = emailNum;
		MsgNum = msgNum;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getLeaderID() {
		return LeaderID;
	}

	public void setLeaderID(Integer leaderID) {
		LeaderID = leaderID;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public String getMemberName() {
		return MemberName;
	}

	public void setMemberName(String memberName) {
		MemberName = memberName;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public Date getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(Date updateTime) {
		UpdateTime = updateTime;
	}

	public Integer getIsDisabled() {
		return IsDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		IsDisabled = isDisabled;
	}

	public Integer getEmailNum() {
		return EmailNum;
	}

	public void setEmailNum(Integer emailNum) {
		EmailNum = emailNum;
	}

	public Integer getMsgNum() {
		return MsgNum;
	}

	public void setMsgNum(Integer msgNum) {
		MsgNum = msgNum;
	}
}
