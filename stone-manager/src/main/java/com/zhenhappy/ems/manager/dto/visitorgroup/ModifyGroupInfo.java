package com.zhenhappy.ems.manager.dto.visitorgroup;

/**
 * Created by wangxd on 2016-04-8.
 */
public class ModifyGroupInfo {
	private Integer leaderID;
	private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
	}
}
