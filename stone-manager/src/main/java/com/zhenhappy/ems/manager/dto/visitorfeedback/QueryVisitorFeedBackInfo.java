package com.zhenhappy.ems.manager.dto.visitorfeedback;

import java.util.Date;

/**
 * Created by wangxd on 2017-03-23.
 */
public class QueryVisitorFeedBackInfo {
	private Integer id;
	private String userName;
	private String q1;
	private String a1;
	private String q2;
	private String a2;
	private String q3;
	private String a3;
	private Date createTime;
	private Integer readed;

	public QueryVisitorFeedBackInfo() {
	}

	public QueryVisitorFeedBackInfo(Integer id, String userName, String q1, String a1, String q2, String a2, String q3, String a3,
									Date createTime, Integer readed) {
		this.id = id;
		this.userName = userName;
		this.q1 = q1;
		this.a1 = a1;
		this.q2 = q2;
		this.a2 = a2;
		this.q3 = q3;
		this.a3 = a3;
		this.createTime = createTime;
		this.readed = readed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getReaded() {
		return readed;
	}

	public void setReaded(Integer readed) {
		this.readed = readed;
	}
}
