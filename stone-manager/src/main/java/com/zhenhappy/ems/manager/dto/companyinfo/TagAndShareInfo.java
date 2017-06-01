package com.zhenhappy.ems.manager.dto.companyinfo;

import com.zhenhappy.ems.entity.TTag;

/**
 * Created by wangxd on 2016-05-26.
 */
public class TagAndShareInfo extends TTag {
	private String sharers;
	private String sharerIds;

	public String getSharers() {
		return sharers;
	}

	public void setSharers(String sharers) {
		this.sharers = sharers;
	}

	public String getSharerIds() {
		return sharerIds;
	}

	public void setSharerIds(String sharerIds) {
		this.sharerIds = sharerIds;
	}
}
