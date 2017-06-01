package com.zhenhappy.ems.dto.managerrole;

/**
 * Created by wangxd9 on 2016-05-17.
 */
public class QueryUserInfo {
	private Integer id;
	private String name;
	private String username;
	private String password;
	private Integer userType;
	private Integer roleId;
	private String roleName;
	private String userDescription;

	public QueryUserInfo() {
		super();
	}

	public QueryUserInfo(Integer id) {
		super();
		this.id = id;
	}

	public QueryUserInfo(Integer id, String name, String username, String password, Integer userType, Integer roleId, String roleName, String userDescription) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.roleId = roleId;
		this.roleName = roleName;
		this.userDescription = userDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}
}
