package com.zhenhappy.ems.manager.dto.companyinfo;

import com.zhenhappy.ems.manager.dto.ModifyTagRequest;

/**
 * Created by wangxd on 2016-05-26.
 */
public class ModifyTagShareRequest extends ModifyTagRequest {
	private String shareName;

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}
}
