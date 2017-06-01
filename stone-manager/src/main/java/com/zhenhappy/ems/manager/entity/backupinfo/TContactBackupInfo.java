package com.zhenhappy.ems.manager.entity.backupinfo;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wujianbin on 2014-05-15.
 */
@Entity
@Table(name = "contact_Info_Backup", schema = "dbo")
public class TContactBackupInfo {
    private Integer id;
	private Integer exhibitor_info_backup_id;
    private String name;
    private String position;
    private String phone;
    private String email;
    private String address;
    private String expressNumber;
    private Integer eid;
    private Integer isDelete;

    public TContactBackupInfo() {
		super();
		this.isDelete = 0;
	}

	public TContactBackupInfo(String address, Integer eid, String email, Integer exhibitor_info_backup_id, String expressNumber,
							  Integer id, Integer isDelete, String name, String phone, String position) {
		this.address = address;
		this.eid = eid;
		this.email = email;
		this.exhibitor_info_backup_id = exhibitor_info_backup_id;
		this.expressNumber = expressNumber;
		this.id = id;
		this.isDelete = isDelete;
		this.name = name;
		this.phone = phone;
		this.position = position;
	}

	@Basic
    @Column(name = "is_delete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
    @Column(name = "expressnumber")
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	@Basic
    @Column(name = "eid")
    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

	@Column(name = "exhibitor_info_backup_id")
	public Integer getExhibitor_info_backup_id() {
		return exhibitor_info_backup_id;
	}

	public void setExhibitor_info_backup_id(Integer exhibitor_info_backup_id) {
		this.exhibitor_info_backup_id = exhibitor_info_backup_id;
	}
}
