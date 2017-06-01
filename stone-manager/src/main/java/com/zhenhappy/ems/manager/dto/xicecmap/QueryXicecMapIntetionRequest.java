package com.zhenhappy.ems.manager.dto.xicecmap;

import com.zhenhappy.ems.manager.dto.EasyuiRequest;

/**
 * Created by wangxd on 2016-07-22.
 */
public class QueryXicecMapIntetionRequest extends EasyuiRequest {
	private Integer id;
	private String booth_num;
	private Integer tag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBooth_num() {
		return booth_num;
	}

	public void setBooth_num(String booth_num) {
		this.booth_num = booth_num;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}
}
