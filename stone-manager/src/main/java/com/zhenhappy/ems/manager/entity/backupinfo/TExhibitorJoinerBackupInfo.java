package com.zhenhappy.ems.manager.entity.backupinfo;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-12-21.
 */
@Entity
@Table(name = "exhibitor_Joiner_Info_Backup", schema = "dbo")
public class TExhibitorJoinerBackupInfo {
	private Integer id;
	private Integer exhibitor_info_backup_id;
	private String name;
	private String position;
	private String telphone;
	private String email;
	private Integer isDelete;
	private Date createTime;
	private Integer admin;
	private Date adminUpdateTime;
	private Integer isNew;//表示是否为本届新增加的参展人员。注：归档后，要重置为往届，即值为0或NULL

	public TExhibitorJoinerBackupInfo() {
		super();
		this.isDelete = 0;
	}

	public TExhibitorJoinerBackupInfo(Integer admin, Date adminUpdateTime, Date createTime, String email, Integer exhibitor_info_backup_id,
									  Integer id, Integer isDelete, Integer isNew, String name, String position, String telphone) {
		this.admin = admin;
		this.adminUpdateTime = adminUpdateTime;
		this.createTime = createTime;
		this.email = email;
		this.exhibitor_info_backup_id = exhibitor_info_backup_id;
		this.id = id;
		this.isDelete = isDelete;
		this.isNew = isNew;
		this.name = name;
		this.position = position;
		this.telphone = telphone;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 500)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "position", length = 500)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "telphone", length = 20)
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Column(name = "email", length = 500)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "is_delete")
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "admin")
	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	@Column(name = "admin_update_time", length = 500)
	public Date getAdminUpdateTime() {
		return adminUpdateTime;
	}

	public void setAdminUpdateTime(Date adminUpdateTime) {
		this.adminUpdateTime = adminUpdateTime;
	}

	@Column(name = "exhibitor_info_backup_id")
	public Integer getExhibitor_info_backup_id() {
		return exhibitor_info_backup_id;
	}

	public void setExhibitor_info_backup_id(Integer exhibitor_info_backup_id) {
		this.exhibitor_info_backup_id = exhibitor_info_backup_id;
	}

	@Column(name = "is_new")
	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
}
