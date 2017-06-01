package com.zhenhappy.ems.manager.dto;

/**
 * Created by wangxd on 2016-04-6.
 */
public class QueryCustomerYear {
	private String year;

	/** default constructor */
	public QueryCustomerYear() {
	}

	/** full constructor */
	public QueryCustomerYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
