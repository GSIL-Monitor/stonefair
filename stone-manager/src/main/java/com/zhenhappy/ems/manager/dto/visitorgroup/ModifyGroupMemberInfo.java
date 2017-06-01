package com.zhenhappy.ems.manager.dto.visitorgroup;

/**
 * Created by wangxd on 2017-02-09.
 */
public class ModifyGroupMemberInfo {
	private Integer id;
	private Integer leaderID;
	private String group_member_company;
	private String group_member_name;
	private String group_member_position;
	private String group_member_telphone;
	private String group_member_email;
	private String group_member_address;
	private Integer group_member_Identify;

	public Integer getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
	}

	public String getGroup_member_address() {
		return group_member_address;
	}

	public void setGroup_member_address(String group_member_address) {
		this.group_member_address = group_member_address;
	}

	public String getGroup_member_company() {
		return group_member_company;
	}

	public void setGroup_member_company(String group_member_company) {
		this.group_member_company = group_member_company;
	}

	public String getGroup_member_email() {
		return group_member_email;
	}

	public void setGroup_member_email(String group_member_email) {
		this.group_member_email = group_member_email;
	}

	public Integer getGroup_member_Identify() {
		return group_member_Identify;
	}

	public void setGroup_member_Identify(Integer group_member_Identify) {
		this.group_member_Identify = group_member_Identify;
	}

	public String getGroup_member_name() {
		return group_member_name;
	}

	public void setGroup_member_name(String group_member_name) {
		this.group_member_name = group_member_name;
	}

	public String getGroup_member_position() {
		return group_member_position;
	}

	public void setGroup_member_position(String group_member_position) {
		this.group_member_position = group_member_position;
	}

	public String getGroup_member_telphone() {
		return group_member_telphone;
	}

	public void setGroup_member_telphone(String group_member_telphone) {
		this.group_member_telphone = group_member_telphone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
