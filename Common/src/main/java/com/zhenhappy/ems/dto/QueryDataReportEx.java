package com.zhenhappy.ems.dto;

import java.util.List;

/**
 * 数据报表：用来统计展商数据报表
 * Created by wangxd on 2016-05-4.
 */
public class QueryDataReportEx {
	private String owner;
	private List<DataReportForProvinceCountInfo> provinceInfo;
	/** default constructor */
	public QueryDataReportEx() {
	}

	public QueryDataReportEx(String owner, List<DataReportForProvinceCountInfo> provinceInfo) {
		this.owner = owner;
		this.provinceInfo = provinceInfo;
	}

	public List<DataReportForProvinceCountInfo> getProvinceInfo() {
		return provinceInfo;
	}

	public void setProvinceInfo(List<DataReportForProvinceCountInfo> provinceInfo) {
		this.provinceInfo = provinceInfo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
