package com.zhenhappy.ems.manager.entity.backupinfo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2011-12-22.
 */
@Entity
@Table(name = "visa_Info_Backup", schema = "dbo")
public class TVisaBackupInfo {
	private Integer id;;
    private Integer exhibitor_info_backup_id;
	private String passportName;
	private Integer gender;
	private String nationality;
	private String jobTitle;
	private String companyName;
	private String boothNo;
	private String detailedAddress;
	private String tel;
	private String fax;
	private String email;
	private String companyWebsite;
	private String passportNo;
	@JSONField(format="yyyy-MM-dd")
	private Date expDate;
	@JSONField(format="yyyy-MM-dd")
	private Date birth;
	private String applyFor;
	@JSONField(format="yyyy-MM-dd")
	private Date from;
	@JSONField(format="yyyy-MM-dd")
	private Date to;
	private String passportPage;
	private String businessLicense;
	private Integer joinerId;
	private Integer status;  //0：表示未提交（已存在记录）；1：表示提交；2：表示未提交（新增加记录）
	private Date createTime;
	private Date updateTime;
	private String address;

	public TVisaBackupInfo() {
	}

    public TVisaBackupInfo(String address, String applyFor, Date birth, String boothNo, String businessLicense, String companyName,
                           String companyWebsite, Date createTime, String detailedAddress, String email, Integer exhibitor_info_backup_id,
                           Date expDate, String fax, Date from, Integer gender, Integer id, String jobTitle, Integer joinerId,
                           String nationality, String passportName, String passportNo, String passportPage, Integer status, String tel,
                           Date to, Date updateTime) {
        this.address = address;
        this.applyFor = applyFor;
        this.birth = birth;
        this.boothNo = boothNo;
        this.businessLicense = businessLicense;
        this.companyName = companyName;
        this.companyWebsite = companyWebsite;
        this.createTime = createTime;
        this.detailedAddress = detailedAddress;
        this.email = email;
        this.exhibitor_info_backup_id = exhibitor_info_backup_id;
        this.expDate = expDate;
        this.fax = fax;
        this.from = from;
        this.gender = gender;
        this.id = id;
        this.jobTitle = jobTitle;
        this.joinerId = joinerId;
        this.nationality = nationality;
        this.passportName = passportName;
        this.passportNo = passportNo;
        this.passportPage = passportPage;
        this.status = status;
        this.tel = tel;
        this.to = to;
        this.updateTime = updateTime;
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
    @Column(name = "exhibitor_info_backup_id")
    public Integer getExhibitor_info_backup_id() {
        return exhibitor_info_backup_id;
    }

    public void setExhibitor_info_backup_id(Integer exhibitor_info_backup_id) {
        this.exhibitor_info_backup_id = exhibitor_info_backup_id;
    }

    @Basic
	@Column(name = "passport_name")
	public String getPassportName() {
		return passportName;
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	@Basic
	@Column(name = "gender")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

    @Basic
    @Column(name = "nationality")
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Basic
    @Column(name = "job_title")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "booth_no")
    public String getBoothNo() {
        return boothNo;
    }

    public void setBoothNo(String boothNo) {
        this.boothNo = boothNo;
    }

    @Basic
    @Column(name = "detailed_address")
    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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
    @Column(name = "company_website")
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    @Basic
    @Column(name = "passport_no")
    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    @Basic
    @Column(name = "exp_date")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

	@Basic
    @Column(name = "birth")
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Basic
    @Column(name = "apply_for")
    public String getApplyFor() {
        return applyFor;
    }

    public void setApplyFor(String applyFor) {
        this.applyFor = applyFor;
    }

    @Basic
    @Column(name = "fromDate")
    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    @Basic
    @Column(name = "toDate")
    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Basic
    @Column(name = "passport_page")
    public String getPassportPage() {
        return passportPage;
    }

    public void setPassportPage(String passportPage) {
        this.passportPage = passportPage;
    }

    @Basic
    @Column(name = "business_license")
    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

	@Basic
	@Column(name = "joiner_id")
	public Integer getJoinerId() {
		return joinerId;
	}

	public void setJoinerId(Integer joinerId) {
		this.joinerId = joinerId;
	}

	@Basic
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "create_time", length = 23)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time", length = 23)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
