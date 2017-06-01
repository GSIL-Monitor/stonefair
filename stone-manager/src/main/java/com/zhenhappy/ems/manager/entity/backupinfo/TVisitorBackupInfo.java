package com.zhenhappy.ems.manager.entity.backupinfo;

import java.util.Date;

import javax.persistence.*;

/**
 * WcustomerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "visitor_Info_Backup", schema = "dbo")
public class TVisitorBackupInfo implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Fields

    private Integer id;
    private String email;
    private String checkingNo;
    private String password;
    private String firstName;
    private String lastName;
    private String sex;
    private String company;
    private String position;
    private String country;
    private String province;
    private String city;
    private String address;
    private String backupEmail;
    private String mobilePhoneCode;
    private String mobilePhone;
    private String telephoneCode;
    private String telephone;
    private String telephoneCode2;
    private String faxCode;
    private String fax;
    private String faxCode2;
    private String website;
    private String SelIndustry;
    private String IndustryOthers;
    private String remark;
    private String createdIp;
    private Date createdTime;
    private String updatedIp;
    private Date updateTime;
    private String LastLoginIP;
    private Date LastLoginTime;
    private Integer sendEmailNum;
    private Date sendEmailDate;
    private Integer sendMsgNum;
    private Date sendMsgDate;
    private Integer isActivated;
    private Boolean isMobile;
    private Boolean isjudged;
    private Integer isProfessional;
    private Boolean IsReaded;
    private Boolean isDisabled;
    private String guid;
    private String visa_FullPassportName;
    private String visa_Gender;
    private String visa_Nationality;
    private String visa_PassportNo;
    private Date visa_ExpDate;
    private Date visa_DateOfBirth;
    private String visa_ChineseEmbassy;
    private String visa_ConsulateOfCity;
    private Date visa_DurationBeginTime;
    private Date visa_DurationEndTime;
    private String visa_PassportPage;
    private String visa_BusinessLicense;
    private Integer visa_WthInfoId;
    private Date visa_CreateTime;
    private Date visa_UpdateTime;
    private Boolean visa_NeedPost;
    private String visa_ExpressTp;
    private String visa_ExpressNo;
    private Boolean visa_IsDisabled;
    private String visa_Hotel;
    private String survey_Q1;
    private String survey_Q2;
    private String survey_Q3;
    private String survey_Q4;
    private String survey_Q5;
    private String survey_Q6;
    private String survey_Q7;
    private String survey_Q8;
    private String survey_Q9;
    private String survey_Q10;
    private String survey_Remark1;
    private String survey_Remark2;
    private String survey_InviterEmail;
    private String survey_InviterName;
    private String survey_EmailSubject;
    private String survey_CreateIP;
    private Date survey_CreateTime;
    private String survey_UpdateIP;
    private Date survey_UpdateTime;
    private boolean survey_IsDisabled;
    private String survey_WSC;
    private Integer year_WThInfoID;
    private String year_WSC;
    private Date year_CreateTime;
    private String year_CreateIP;
    private Date year_UpdateTime;
    private String year_UpdateIP;
    private Date backup_date;

    // Constructors

    /** default constructor */
    public TVisitorBackupInfo() {
    }

    public TVisitorBackupInfo(Integer id) {
        this.id = id;
    }

    public TVisitorBackupInfo(Integer id, String firstName, String country, String city, String address, String mobilePhone,
                              String telephone, String email, Date createdTime, Date updateTime, Integer isProfessional,
                              Integer isActivated, Date backup_date) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.telephone = telephone;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.isProfessional = isProfessional;
        this.isActivated = isActivated;
        this.backup_date = backup_date;
    }

    public TVisitorBackupInfo(String address, Date backup_date, String backupEmail, String checkingNo,
                              String city, String company, String country, String createdIp, Date createdTime,
                              String email, String fax, String faxCode2, String faxCode, String firstName,
                              String guid, Integer id, String industryOthers, Integer isActivated,
                              Boolean isDisabled, Boolean isjudged, Boolean isMobile, Integer isProfessional,
                              Boolean isReaded, String lastLoginIP, Date lastLoginTime, String lastName,
                              String mobilePhone, String mobilePhoneCode, String password, String position,
                              String province, String remark, String selIndustry, Date sendEmailDate,
                              Integer sendEmailNum, Date sendMsgDate, Integer sendMsgNum, String sex,
                              String survey_CreateIP, Date survey_CreateTime, String survey_EmailSubject,
                              String survey_InviterEmail, String survey_InviterName, boolean survey_IsDisabled,
                              String survey_Q10, String survey_Q1, String survey_Q2, String survey_Q3,
                              String survey_Q4, String survey_Q5, String survey_Q6, String survey_Q7,
                              String survey_Q8, String survey_Q9, String survey_Remark1, String survey_Remark2,
                              String survey_UpdateIP, Date survey_UpdateTime, String survey_WSC,
                              String telephone, String telephoneCode2, String telephoneCode, String updatedIp,
                              Date updateTime, String visa_BusinessLicense, String visa_ChineseEmbassy,
                              String visa_ConsulateOfCity, Date visa_CreateTime, Date visa_DateOfBirth,
                              Date visa_DurationBeginTime, Date visa_DurationEndTime, Date visa_ExpDate,
                              String visa_ExpressNo, String visa_ExpressTp, String visa_FullPassportName,
                              String visa_Gender, String visa_Hotel, Boolean visa_IsDisabled,
                              String visa_Nationality, Boolean visa_NeedPost, String visa_PassportNo,
                              String visa_PassportPage, Date visa_UpdateTime, Integer visa_WthInfoId,
                              String website, String year_CreateIP, Date year_CreateTime, String year_UpdateIP,
                              Date year_UpdateTime, String year_WSC, Integer year_WThInfoID) {
        this.address = address;
        this.backup_date = backup_date;
        this.backupEmail = backupEmail;
        this.checkingNo = checkingNo;
        this.city = city;
        this.company = company;
        this.country = country;
        this.createdIp = createdIp;
        this.createdTime = createdTime;
        this.email = email;
        this.fax = fax;
        this.faxCode2 = faxCode2;
        this.faxCode = faxCode;
        this.firstName = firstName;
        this.guid = guid;
        this.id = id;
        IndustryOthers = industryOthers;
        this.isActivated = isActivated;
        this.isDisabled = isDisabled;
        this.isjudged = isjudged;
        this.isMobile = isMobile;
        this.isProfessional = isProfessional;
        IsReaded = isReaded;
        LastLoginIP = lastLoginIP;
        LastLoginTime = lastLoginTime;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.mobilePhoneCode = mobilePhoneCode;
        this.password = password;
        this.position = position;
        this.province = province;
        this.remark = remark;
        SelIndustry = selIndustry;
        this.sendEmailDate = sendEmailDate;
        this.sendEmailNum = sendEmailNum;
        this.sendMsgDate = sendMsgDate;
        this.sendMsgNum = sendMsgNum;
        this.sex = sex;
        this.survey_CreateIP = survey_CreateIP;
        this.survey_CreateTime = survey_CreateTime;
        this.survey_EmailSubject = survey_EmailSubject;
        this.survey_InviterEmail = survey_InviterEmail;
        this.survey_InviterName = survey_InviterName;
        this.survey_IsDisabled = survey_IsDisabled;
        this.survey_Q10 = survey_Q10;
        this.survey_Q1 = survey_Q1;
        this.survey_Q2 = survey_Q2;
        this.survey_Q3 = survey_Q3;
        this.survey_Q4 = survey_Q4;
        this.survey_Q5 = survey_Q5;
        this.survey_Q6 = survey_Q6;
        this.survey_Q7 = survey_Q7;
        this.survey_Q8 = survey_Q8;
        this.survey_Q9 = survey_Q9;
        this.survey_Remark1 = survey_Remark1;
        this.survey_Remark2 = survey_Remark2;
        this.survey_UpdateIP = survey_UpdateIP;
        this.survey_UpdateTime = survey_UpdateTime;
        this.survey_WSC = survey_WSC;
        this.telephone = telephone;
        this.telephoneCode2 = telephoneCode2;
        this.telephoneCode = telephoneCode;
        this.updatedIp = updatedIp;
        this.updateTime = updateTime;
        this.visa_BusinessLicense = visa_BusinessLicense;
        this.visa_ChineseEmbassy = visa_ChineseEmbassy;
        this.visa_ConsulateOfCity = visa_ConsulateOfCity;
        this.visa_CreateTime = visa_CreateTime;
        this.visa_DateOfBirth = visa_DateOfBirth;
        this.visa_DurationBeginTime = visa_DurationBeginTime;
        this.visa_DurationEndTime = visa_DurationEndTime;
        this.visa_ExpDate = visa_ExpDate;
        this.visa_ExpressNo = visa_ExpressNo;
        this.visa_ExpressTp = visa_ExpressTp;
        this.visa_FullPassportName = visa_FullPassportName;
        this.visa_Gender = visa_Gender;
        this.visa_Hotel = visa_Hotel;
        this.visa_IsDisabled = visa_IsDisabled;
        this.visa_Nationality = visa_Nationality;
        this.visa_NeedPost = visa_NeedPost;
        this.visa_PassportNo = visa_PassportNo;
        this.visa_PassportPage = visa_PassportPage;
        this.visa_UpdateTime = visa_UpdateTime;
        this.visa_WthInfoId = visa_WthInfoId;
        this.website = website;
        this.year_CreateIP = year_CreateIP;
        this.year_CreateTime = year_CreateTime;
        this.year_UpdateIP = year_UpdateIP;
        this.year_UpdateTime = year_UpdateTime;
        this.year_WSC = year_WSC;
        this.year_WThInfoID = year_WThInfoID;
    }

    // Property accessors
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Email", length = 250)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "CheckingNo")
    public String getCheckingNo() {
        return this.checkingNo;
    }

    public void setCheckingNo(String checkingNo) {
        this.checkingNo = checkingNo;
    }

    @Column(name = "Password", length = 250)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "FirstName")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LastName")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "Sex")
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "Company")
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "Position")
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "Country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "Province", length = 50)
    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "City")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "Address")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "BackupEmail", length = 250)
    public String getBackupEmail() {
        return this.backupEmail;
    }

    public void setBackupEmail(String backupEmail) {
        this.backupEmail = backupEmail;
    }

    @Column(name = "MobilePhoneCode", length = 50)
    public String getMobilePhoneCode() {
        return this.mobilePhoneCode;
    }

    public void setMobilePhoneCode(String mobilePhoneCode) {
        this.mobilePhoneCode = mobilePhoneCode;
    }

    @Column(name = "MobilePhone", length = 250)
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "TelephoneCode", length = 50)
    public String getTelephoneCode() {
        return this.telephoneCode;
    }

    public void setTelephoneCode(String telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    @Column(name = "Telephone", length = 250)
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "TelephoneCode2")
    public String getTelephoneCode2() {
        return this.telephoneCode2;
    }

    public void setTelephoneCode2(String telephoneCode2) {
        this.telephoneCode2 = telephoneCode2;
    }

    @Column(name = "FaxCode", length = 50)
    public String getFaxCode() {
        return this.faxCode;
    }

    public void setFaxCode(String faxCode) {
        this.faxCode = faxCode;
    }

    @Column(name = "Fax", length = 250)
    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "FaxCode2")
    public String getFaxCode2() {
        return this.faxCode2;
    }

    public void setFaxCode2(String faxCode2) {
        this.faxCode2 = faxCode2;
    }

    @Column(name = "Website", length = 250)
    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "Remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CreateIP")
    public String getCreatedIp() {
        return this.createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    @Column(name = "CreateTime", length = 23)
    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "UpdateIP")
    public String getUpdatedIp() {
        return this.updatedIp;
    }

    public void setUpdatedIp(String updatedIp) {
        this.updatedIp = updatedIp;
    }

    @Column(name = "UpdateTime", length = 23)
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "SendEmailNum")
    public Integer getSendEmailNum() {
        return this.sendEmailNum;
    }

    public void setSendEmailNum(Integer sendEmailNum) {
        this.sendEmailNum = sendEmailNum;
    }

    @Column(name = "SendEmailDate", length = 23)
    public Date getSendEmailDate() {
        return this.sendEmailDate;
    }

    public void setSendEmailDate(Date sendEmailDate) {
        this.sendEmailDate = sendEmailDate;
    }

    @Column(name = "GUID")
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Column(name = "Isjudged")
    public Boolean getIsjudged() {
        return isjudged;
    }

    public void setIsjudged(Boolean isjudged) {
        this.isjudged = isjudged;
    }

    @Column(name = "IsDisabled")
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    @Column(name = "IsProfessional")
    public Integer getIsProfessional() {
        return isProfessional;
    }

    public void setIsProfessional(Integer professional) {
        isProfessional = professional;
    }

    @Column(name = "IsMobile")
    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean mobile) {
        isMobile = mobile;
    }

    @Column(name = "SendMsgNum")
    public Integer getSendMsgNum() {
        return sendMsgNum;
    }

    public void setSendMsgNum(Integer sendMsgNum) {
        this.sendMsgNum = sendMsgNum;
    }

    @Column(name = "SendMsgDate", length = 23)
    public Date getSendMsgDate() {
        return sendMsgDate;
    }

    public void setSendMsgDate(Date sendMsgDate) {
        this.sendMsgDate = sendMsgDate;
    }

    @Column(name = "IsActivated")
    public Integer getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }

    @Column(name = "backup_date")
    public Date getBackup_date() {
        return backup_date;
    }

    public void setBackup_date(Date backup_date) {
        this.backup_date = backup_date;
    }

    @Column(name = "SelIndustry")
    public String getSelIndustry() {
        return SelIndustry;
    }

    public void setSelIndustry(String selIndustry) {
        SelIndustry = selIndustry;
    }

    @Column(name = "IndustryOthers")
    public String getIndustryOthers() {
        return IndustryOthers;
    }

    public void setIndustryOthers(String industryOthers) {
        IndustryOthers = industryOthers;
    }

    @Column(name = "LastLoginIP")
    public String getLastLoginIP() {
        return LastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        LastLoginIP = lastLoginIP;
    }

    @Column(name = "LastLoginTime")
    public Date getLastLoginTime() {
        return LastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        LastLoginTime = lastLoginTime;
    }

    @Column(name = "IsReaded")
    public Boolean getReaded() {
        return IsReaded;
    }

    public void setReaded(Boolean readed) {
        IsReaded = readed;
    }

    @Column(name = "survey_CreateIP")
    public String getSurvey_CreateIP() {
        return survey_CreateIP;
    }

    public void setSurvey_CreateIP(String survey_CreateIP) {
        this.survey_CreateIP = survey_CreateIP;
    }

    @Column(name = "survey_CreateTime")
    public Date getSurvey_CreateTime() {
        return survey_CreateTime;
    }

    public void setSurvey_CreateTime(Date survey_CreateTime) {
        this.survey_CreateTime = survey_CreateTime;
    }

    @Column(name = "survey_EmailSubject")
    public String getSurvey_EmailSubject() {
        return survey_EmailSubject;
    }

    public void setSurvey_EmailSubject(String survey_EmailSubject) {
        this.survey_EmailSubject = survey_EmailSubject;
    }

    @Column(name = "survey_InviterEmail")
    public String getSurvey_InviterEmail() {
        return survey_InviterEmail;
    }

    public void setSurvey_InviterEmail(String survey_InviterEmail) {
        this.survey_InviterEmail = survey_InviterEmail;
    }

    @Column(name = "survey_InviterName")
    public String getSurvey_InviterName() {
        return survey_InviterName;
    }

    public void setSurvey_InviterName(String survey_InviterName) {
        this.survey_InviterName = survey_InviterName;
    }

    @Column(name = "survey_IsDisabled")
    public boolean isSurvey_IsDisabled() {
        return survey_IsDisabled;
    }

    public void setSurvey_IsDisabled(boolean survey_IsDisabled) {
        this.survey_IsDisabled = survey_IsDisabled;
    }

    @Column(name = "survey_Q10")
    public String getSurvey_Q10() {
        return survey_Q10;
    }

    public void setSurvey_Q10(String survey_Q10) {
        this.survey_Q10 = survey_Q10;
    }

    @Column(name = "survey_Q1")
    public String getSurvey_Q1() {
        return survey_Q1;
    }

    public void setSurvey_Q1(String survey_Q1) {
        this.survey_Q1 = survey_Q1;
    }

    @Column(name = "survey_Q2")
    public String getSurvey_Q2() {
        return survey_Q2;
    }

    public void setSurvey_Q2(String survey_Q2) {
        this.survey_Q2 = survey_Q2;
    }

    @Column(name = "survey_Q3")
    public String getSurvey_Q3() {
        return survey_Q3;
    }

    public void setSurvey_Q3(String survey_Q3) {
        this.survey_Q3 = survey_Q3;
    }

    @Column(name = "survey_Q4")
    public String getSurvey_Q4() {
        return survey_Q4;
    }

    public void setSurvey_Q4(String survey_Q4) {
        this.survey_Q4 = survey_Q4;
    }

    @Column(name = "survey_Q5")
    public String getSurvey_Q5() {
        return survey_Q5;
    }

    public void setSurvey_Q5(String survey_Q5) {
        this.survey_Q5 = survey_Q5;
    }

    @Column(name = "survey_Q6")
    public String getSurvey_Q6() {
        return survey_Q6;
    }

    public void setSurvey_Q6(String survey_Q6) {
        this.survey_Q6 = survey_Q6;
    }

    @Column(name = "survey_Q7")
    public String getSurvey_Q7() {
        return survey_Q7;
    }

    public void setSurvey_Q7(String survey_Q7) {
        this.survey_Q7 = survey_Q7;
    }

    @Column(name = "survey_Q8")
    public String getSurvey_Q8() {
        return survey_Q8;
    }

    public void setSurvey_Q8(String survey_Q8) {
        this.survey_Q8 = survey_Q8;
    }

    @Column(name = "survey_Q9")
    public String getSurvey_Q9() {
        return survey_Q9;
    }

    public void setSurvey_Q9(String survey_Q9) {
        this.survey_Q9 = survey_Q9;
    }

    @Column(name = "survey_Remark1")
    public String getSurvey_Remark1() {
        return survey_Remark1;
    }

    public void setSurvey_Remark1(String survey_Remark1) {
        this.survey_Remark1 = survey_Remark1;
    }

    @Column(name = "survey_Remark2")
    public String getSurvey_Remark2() {
        return survey_Remark2;
    }

    public void setSurvey_Remark2(String survey_Remark2) {
        this.survey_Remark2 = survey_Remark2;
    }

    @Column(name = "survey_UpdateIP")
    public String getSurvey_UpdateIP() {
        return survey_UpdateIP;
    }

    public void setSurvey_UpdateIP(String survey_UpdateIP) {
        this.survey_UpdateIP = survey_UpdateIP;
    }

    @Column(name = "survey_UpdateTime")
    public Date getSurvey_UpdateTime() {
        return survey_UpdateTime;
    }

    public void setSurvey_UpdateTime(Date survey_UpdateTime) {
        this.survey_UpdateTime = survey_UpdateTime;
    }

    @Column(name = "survey_WSC")
    public String getSurvey_WSC() {
        return survey_WSC;
    }

    public void setSurvey_WSC(String survey_WSC) {
        this.survey_WSC = survey_WSC;
    }

    @Column(name = "year_CreateIP")
    public String getYear_CreateIP() {
        return year_CreateIP;
    }

    public void setYear_CreateIP(String year_CreateIP) {
        this.year_CreateIP = year_CreateIP;
    }

    @Column(name = "year_CreateTime")
    public Date getYear_CreateTime() {
        return year_CreateTime;
    }

    public void setYear_CreateTime(Date year_CreateTime) {
        this.year_CreateTime = year_CreateTime;
    }

    @Column(name = "year_UpdateIP")
    public String getYear_UpdateIP() {
        return year_UpdateIP;
    }

    public void setYear_UpdateIP(String year_UpdateIP) {
        this.year_UpdateIP = year_UpdateIP;
    }

    @Column(name = "year_UpdateTime")
    public Date getYear_UpdateTime() {
        return year_UpdateTime;
    }

    public void setYear_UpdateTime(Date year_UpdateTime) {
        this.year_UpdateTime = year_UpdateTime;
    }

    @Column(name = "year_WSC")
    public String getYear_WSC() {
        return year_WSC;
    }

    public void setYear_WSC(String year_WSC) {
        this.year_WSC = year_WSC;
    }

    @Column(name = "year_WThInfoID")
    public Integer getYear_WThInfoID() {
        return year_WThInfoID;
    }

    public void setYear_WThInfoID(Integer year_WThInfoID) {
        this.year_WThInfoID = year_WThInfoID;
    }

    @Column(name = "visa_BusinessLicense")
    public String getVisa_BusinessLicense() {
        return visa_BusinessLicense;
    }

    public void setVisa_BusinessLicense(String visa_BusinessLicense) {
        this.visa_BusinessLicense = visa_BusinessLicense;
    }

    @Column(name = "visa_ChineseEmbassy")
    public String getVisa_ChineseEmbassy() {
        return visa_ChineseEmbassy;
    }

    public void setVisa_ChineseEmbassy(String visa_ChineseEmbassy) {
        this.visa_ChineseEmbassy = visa_ChineseEmbassy;
    }

    @Column(name = "visa_ConsulateOfCity")
    public String getVisa_ConsulateOfCity() {
        return visa_ConsulateOfCity;
    }

    public void setVisa_ConsulateOfCity(String visa_ConsulateOfCity) {
        this.visa_ConsulateOfCity = visa_ConsulateOfCity;
    }

    @Column(name = "visa_CreateTime")
    public Date getVisa_CreateTime() {
        return visa_CreateTime;
    }

    public void setVisa_CreateTime(Date visa_CreateTime) {
        this.visa_CreateTime = visa_CreateTime;
    }

    @Column(name = "visa_DateOfBirth")
    public Date getVisa_DateOfBirth() {
        return visa_DateOfBirth;
    }

    public void setVisa_DateOfBirth(Date visa_DateOfBirth) {
        this.visa_DateOfBirth = visa_DateOfBirth;
    }

    @Column(name = "visa_DurationBeginTime")
    public Date getVisa_DurationBeginTime() {
        return visa_DurationBeginTime;
    }

    public void setVisa_DurationBeginTime(Date visa_DurationBeginTime) {
        this.visa_DurationBeginTime = visa_DurationBeginTime;
    }

    @Column(name = "visa_DurationEndTime")
    public Date getVisa_DurationEndTime() {
        return visa_DurationEndTime;
    }

    public void setVisa_DurationEndTime(Date visa_DurationEndTime) {
        this.visa_DurationEndTime = visa_DurationEndTime;
    }

    @Column(name = "visa_ExpDate")
    public Date getVisa_ExpDate() {
        return visa_ExpDate;
    }

    public void setVisa_ExpDate(Date visa_ExpDate) {
        this.visa_ExpDate = visa_ExpDate;
    }

    @Column(name = "visa_ExpressNo")
    public String getVisa_ExpressNo() {
        return visa_ExpressNo;
    }

    public void setVisa_ExpressNo(String visa_ExpressNo) {
        this.visa_ExpressNo = visa_ExpressNo;
    }

    @Column(name = "visa_ExpressTp")
    public String getVisa_ExpressTp() {
        return visa_ExpressTp;
    }

    public void setVisa_ExpressTp(String visa_ExpressTp) {
        this.visa_ExpressTp = visa_ExpressTp;
    }

    @Column(name = "visa_FullPassportName")
    public String getVisa_FullPassportName() {
        return visa_FullPassportName;
    }

    public void setVisa_FullPassportName(String visa_FullPassportName) {
        this.visa_FullPassportName = visa_FullPassportName;
    }

    @Column(name = "visa_Gender")
    public String getVisa_Gender() {
        return visa_Gender;
    }

    public void setVisa_Gender(String visa_Gender) {
        this.visa_Gender = visa_Gender;
    }

    @Column(name = "visa_Hotel")
    public String getVisa_Hotel() {
        return visa_Hotel;
    }

    public void setVisa_Hotel(String visa_Hotel) {
        this.visa_Hotel = visa_Hotel;
    }

    @Column(name = "visa_IsDisabled")
    public Boolean getVisa_IsDisabled() {
        return visa_IsDisabled;
    }

    public void setVisa_IsDisabled(Boolean visa_IsDisabled) {
        this.visa_IsDisabled = visa_IsDisabled;
    }

    @Column(name = "visa_Nationality")
    public String getVisa_Nationality() {
        return visa_Nationality;
    }

    public void setVisa_Nationality(String visa_Nationality) {
        this.visa_Nationality = visa_Nationality;
    }

    @Column(name = "visa_NeedPost")
    public Boolean getVisa_NeedPost() {
        return visa_NeedPost;
    }

    public void setVisa_NeedPost(Boolean visa_NeedPost) {
        this.visa_NeedPost = visa_NeedPost;
    }

    @Column(name = "visa_PassportNo")
    public String getVisa_PassportNo() {
        return visa_PassportNo;
    }

    public void setVisa_PassportNo(String visa_PassportNo) {
        this.visa_PassportNo = visa_PassportNo;
    }

    @Column(name = "visa_PassportPage")
    public String getVisa_PassportPage() {
        return visa_PassportPage;
    }

    public void setVisa_PassportPage(String visa_PassportPage) {
        this.visa_PassportPage = visa_PassportPage;
    }

    @Column(name = "visa_UpdateTime")
    public Date getVisa_UpdateTime() {
        return visa_UpdateTime;
    }

    public void setVisa_UpdateTime(Date visa_UpdateTime) {
        this.visa_UpdateTime = visa_UpdateTime;
    }

    @Column(name = "visa_WthInfoId")
    public Integer getVisa_WthInfoId() {
        return visa_WthInfoId;
    }

    public void setVisa_WthInfoId(Integer visa_WthInfoId) {
        this.visa_WthInfoId = visa_WthInfoId;
    }
}