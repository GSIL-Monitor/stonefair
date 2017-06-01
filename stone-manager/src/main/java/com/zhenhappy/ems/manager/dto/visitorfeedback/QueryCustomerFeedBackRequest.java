package com.zhenhappy.ems.manager.dto.visitorfeedback;

import com.zhenhappy.ems.manager.dto.EasyuiRequest;

import java.util.Date;

/**
 * Created by wangxd on 2017-04-16.
 */
public class QueryCustomerFeedBackRequest extends EasyuiRequest {
	private Integer id;
	private String UserName;
	private String Q1;
	private String A1;
	private String Q2;
	private String A2;
	private String Q3;
	private String A3;
	private String CreateTime;
	private Boolean IsReaded;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getQ1() {
		return Q1;
	}

	public void setQ1(String q1) {
		Q1 = q1;
	}

	public String getA1() {
		return A1;
	}

	public void setA1(String a1) {
		A1 = a1;
	}

	public String getQ2() {
		return Q2;
	}

	public void setQ2(String q2) {
		Q2 = q2;
	}

	public String getA2() {
		return A2;
	}

	public void setA2(String a2) {
		A2 = a2;
	}

	public String getQ3() {
		return Q3;
	}

	public void setQ3(String q3) {
		Q3 = q3;
	}

	public String getA3() {
		return A3;
	}

	public void setA3(String a3) {
		A3 = a3;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public Boolean getReaded() {
		return IsReaded;
	}

	public void setReaded(Boolean readed) {
		IsReaded = readed;
	}
}
