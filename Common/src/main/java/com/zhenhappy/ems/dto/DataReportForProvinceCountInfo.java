package com.zhenhappy.ems.dto;

/**
 * 数据报表：用来统计展商数据报表
 * Created by wangxd on 2016-05-4.
 */
public class DataReportForProvinceCountInfo {
	private String owner;
	private String province;
	private Integer count;

	/** default constructor */
	public DataReportForProvinceCountInfo() {
	}

	public DataReportForProvinceCountInfo(String owner, String province, Integer count) {
		this.owner = owner;
		this.province = province;
		this.count = count;
	}
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
