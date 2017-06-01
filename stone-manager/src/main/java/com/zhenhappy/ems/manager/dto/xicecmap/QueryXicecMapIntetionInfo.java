package com.zhenhappy.ems.manager.dto.xicecmap;

/**
 * Created by wangxd on 2016-07-22.
 */
public class QueryXicecMapIntetionInfo {
	private Integer id;
	private String booth_num;
	private Integer tag;

	/** default constructor */
	public QueryXicecMapIntetionInfo() {
	}

	/** minimal constructor */
	public QueryXicecMapIntetionInfo(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public QueryXicecMapIntetionInfo(Integer id, String booth_num, Integer tag) {
		this.id = id;
		this.booth_num = booth_num;
		this.tag = tag;
	}

	public Integer getId() {
		return this.id;
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
