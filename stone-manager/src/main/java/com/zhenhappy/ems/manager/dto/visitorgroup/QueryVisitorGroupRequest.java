package com.zhenhappy.ems.manager.dto.visitorgroup;

import com.zhenhappy.ems.manager.dto.EasyuiRequest;

/**
 * Created by wangxd on 2016-12-26.
 */
public class QueryVisitorGroupRequest extends EasyuiRequest {
	private Integer id;
	private String group_name;
	private String group_header_name;
	private String group_header_telphone;
	private String group_header_position;
	private String group_header_email;
	private String group_header_address;
	private String group_header_create_time;
	private String group_header_update_time;
	private Integer group_is_disabled;

	public String getGroup_header_address() {
		return group_header_address;
	}

	public void setGroup_header_address(String group_header_address) {
		this.group_header_address = group_header_address;
	}

	public String getGroup_header_create_time() {
		return group_header_create_time;
	}

	public void setGroup_header_create_time(String group_header_create_time) {
		this.group_header_create_time = group_header_create_time;
	}

	public String getGroup_header_email() {
		return group_header_email;
	}

	public void setGroup_header_email(String group_header_email) {
		this.group_header_email = group_header_email;
	}

	public String getGroup_header_name() {
		return group_header_name;
	}

	public void setGroup_header_name(String group_header_name) {
		this.group_header_name = group_header_name;
	}

	public String getGroup_header_position() {
		return group_header_position;
	}

	public void setGroup_header_position(String group_header_position) {
		this.group_header_position = group_header_position;
	}

	public String getGroup_header_telphone() {
		return group_header_telphone;
	}

	public void setGroup_header_telphone(String group_header_telphone) {
		this.group_header_telphone = group_header_telphone;
	}

	public String getGroup_header_update_time() {
		return group_header_update_time;
	}

	public void setGroup_header_update_time(String group_header_update_time) {
		this.group_header_update_time = group_header_update_time;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroup_is_disabled() {
		return group_is_disabled;
	}

	public void setGroup_is_disabled(Integer group_is_disabled) {
		this.group_is_disabled = group_is_disabled;
	}
}
