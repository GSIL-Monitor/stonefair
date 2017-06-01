package com.zhenhappy.ems.manager.dto.visitorgroup;

import com.zhenhappy.ems.manager.dto.EasyuiRequest;

/**
 * Created by wangxd on 2016-12-26.
 */
public class QueryVisitorMemberListRequest extends EasyuiRequest {
	private Integer leaderID;

	public Integer getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
	}
}
