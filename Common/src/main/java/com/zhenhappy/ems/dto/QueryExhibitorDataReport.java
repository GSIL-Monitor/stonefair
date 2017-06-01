package com.zhenhappy.ems.dto;

/**
 * Created by wangxd on 2016-05-4.
 */
public class QueryExhibitorDataReport {
	private String yearMonth;
	private String total;

	/** default constructor */
	public QueryExhibitorDataReport() {
	}

	public QueryExhibitorDataReport(String yearMonth,String total) {
		this.yearMonth = yearMonth;
		this.total = total;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}
