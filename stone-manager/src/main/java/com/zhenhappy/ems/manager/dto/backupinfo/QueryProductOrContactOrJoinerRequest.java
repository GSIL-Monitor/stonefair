package com.zhenhappy.ems.manager.dto.backupinfo;

import com.zhenhappy.ems.manager.dto.EasyuiRequest;

/**
 * Created by wangxd on 2016-12-21.
 */
public class QueryProductOrContactOrJoinerRequest extends EasyuiRequest {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
