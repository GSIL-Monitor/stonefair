package com.zhenhappy.ems.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/23.
 */
@Entity
@Table(name = "visitor_Info", schema = "dbo")
public class TVisitorInfo implements java.io.Serializable {

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
    private Integer country;
    private String province;
    private String city;
    private String address;
    private String backupEmail;
    private String mobileCode;
    private String mobilePhone;
    private String telCode;
    private String tel;
    private String telCode2;
    private String faxCode;
    private String fax;
    private String faxCode2;
    private String website;
    private String industry;
    private String industryOther;
    private String remark;
    private String createIp;
    private Date createTime;
    private String updateIp;
    private Date updateTime;
    private Integer sendEmailNum;
    private Date sendEmailTime;
    private Integer sendMsgNum;
    private Date sendMsgTime;
    private String langFlag;
    private String visitDate;
    private String beenToFair;
    private String beenToRole;
    private Boolean isRecieveEmail;
    private Boolean isRecieveDoc;
    private Boolean isMobile;
    private Boolean isjudged;
    private Integer isProfessional;  //0：表示非专业客商；1：表示政府部门；2：表示专业客商
    private Boolean isAccommodation;
    private Boolean isDisabled;
    private Boolean isReaded;
    private String tmp_Country;
    private String tmp_Postcode;
    private String tmp_Interest;
    private String tmp_InterestOthers;
    private String tmp_Knowfrom;
    private String tmp_KnowfromOthers;
    private String tmp_V_name1;
    private String tmp_V_title1;
    private String tmp_V_position1;
    private String tmp_V_contact1;
    private String tmp_V_name2;
    private String tmp_V_title2;
    private String tmp_V_position2;
    private String tmp_V_contact2;
    private String tmp_V_name3;
    private String tmp_V_title3;
    private String tmp_V_position3;
    private String tmp_V_contact3;
    private String guid;
    private Integer govementFlag;
    private String rabbi;
    private Integer customer_type;

    // Constructors

    /** default constructor */
    public TVisitorInfo() {
    }

    /** minimal constructor */
    public TVisitorInfo(Integer id) {
        this.id = id;
    }

    public TVisitorInfo(Integer id, String email, String checkingNo,
                     String password, String firstName, String lastName, String sex,
                     String company, String position, Integer country, String province,
                     String city, String address, String backupEmail,
                     String mobilePhoneCode, String mobilePhone, String telephoneCode,
                     String telephone, String telephoneCode2, String faxCode,
                     String fax, String faxCode2, String website, String industry,String industryOther, String remark,
                     String createIp, Date createTime, String updateIp,
                     Date updateTime, Integer sendEmailNum,Date sendEmailTime, Integer sendMsgNum,Date sendMsgTime,
                     String langFlag,String visitDate,String beenToFair,String beenToRole,Boolean isRecieveEmail,
                     Boolean isRecieveDoc,Boolean isMobile,Boolean isjudged,Integer isProfessional,Boolean isAccommodation,
                     Boolean isDisabled, Boolean isReaded,
                     String tmp_Country,String tmp_Postcode,String tmp_Interest,String tmp_InterestOthers,
                     String tmp_Knowfrom,String tmp_KnowfromOthers,String tmp_V_name1,String tmp_V_title1,
                     String tmp_V_position1,String tmp_V_contact1,String tmp_V_name2,String tmp_V_title2,
                     String tmp_V_position2,String tmp_V_contact2,String tmp_V_name3,String tmp_V_title3,
                     String tmp_V_position3,String tmp_V_contact3,String guid, Integer govement, String rabbi, Integer customer_type) {
        this.id = id;
        this.email = email;
        this.checkingNo = checkingNo;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.company = company;
        this.position = position;
        this.country = country;
        this.province = province;
        this.city = city;
        this.address = address;
        this.backupEmail = backupEmail;
        this.mobileCode = mobilePhoneCode;
        this.mobilePhone = mobilePhone;
        this.telCode = telephoneCode;
        this.tel = telephone;
        this.telCode2 = telephoneCode2;
        this.faxCode = faxCode;
        this.fax = fax;
        this.faxCode2 = faxCode2;
        this.website = website;
        this.industry = industry;
        this.industryOther = industryOther;
        this.remark = remark;
        this.createIp = createIp;
        this.createTime = createTime;
        this.updateIp = updateIp;
        this.updateTime = updateTime;
        this.sendEmailNum = sendEmailNum;
        this.sendEmailTime = sendEmailTime;
        this.sendMsgNum = sendMsgNum;
        this.sendMsgTime = sendMsgTime;
        this.langFlag = langFlag;
        this.visitDate = visitDate;
        this.beenToFair = beenToFair;
        this.beenToRole = beenToRole;
        this.isRecieveEmail = isRecieveEmail;
        this.isRecieveDoc = isRecieveDoc;
        this.isMobile = isMobile;
        this.isjudged = isjudged;
        this.isProfessional = isProfessional;
        this.isAccommodation = isAccommodation;
        this.isDisabled = isDisabled;
        this.isReaded = isReaded;
        this.tmp_Country = tmp_Country;
        this.tmp_Postcode = tmp_Postcode;
        this.tmp_Interest = tmp_Interest;
        this.tmp_InterestOthers = tmp_InterestOthers;
        this.tmp_Knowfrom = tmp_Knowfrom;
        this.tmp_KnowfromOthers = tmp_KnowfromOthers;
        this.tmp_V_name1 = tmp_V_name1;
        this.tmp_V_title1 = tmp_V_title1;
        this.tmp_V_position1 = tmp_V_position1;
        this.tmp_V_contact1 = tmp_V_contact1;
        this.tmp_V_name2 = tmp_V_name2;
        this.tmp_V_title2 = tmp_V_title2;
        this.tmp_V_position2 = tmp_V_position2;
        this.tmp_V_contact2 = tmp_V_contact2;
        this.tmp_V_name3 = tmp_V_name3;
        this.tmp_V_title3 = tmp_V_title3;
        this.tmp_V_position3 = tmp_V_position3;
        this.tmp_V_contact3 = tmp_V_contact3;
        this.guid = guid;
        this.govementFlag = govement;
        this.rabbi = rabbi;
        this.customer_type = customer_type;
    }

    // Property accessors
    @Id
    @Column(name = "ID", unique = true, nullable = false)
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
    public Integer getCountry() {
        return this.country;
    }

    public void setCountry(Integer country) {
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

    @Column(name = "MobileCode", length = 50)
    public String getMobileCode() {
        return this.mobileCode;
    }

    public void setMobileCode(String mobilePhoneCode) {
        this.mobileCode = mobilePhoneCode;
    }

    @Column(name = "Mobile", length = 250)
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "TelCode", length = 50)
    public String getTelCode() {
        return this.telCode;
    }

    public void setTelCode(String telephoneCode) {
        this.telCode = telephoneCode;
    }

    @Column(name = "Tel", length = 250)
    public String getTel() {
        return this.tel;
    }

    public void setTel(String telephone) {
        this.tel = telephone;
    }

    @Column(name = "TelCode2")
    public String getTelCode2() {
        return this.telCode2;
    }

    public void setTelCode2(String telephoneCode2) {
        this.telCode2 = telephoneCode2;
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
    public String getCreateIp() {
        return this.createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    @Column(name = "CreateTime", length = 23)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UpdateIP")
    public String getUpdateIp() {
        return this.updateIp;
    }

    public void setUpdateIp(String updatedIp) {
        this.updateIp = updatedIp;
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

    @Column(name = "SendEmailTime", length = 23)
    public Date getSendEmailTime() {
        return this.sendEmailTime;
    }

    public void setSendEmailTime(Date sendEmailDate) {
        this.sendEmailTime = sendEmailDate;
    }

    @Column(name = "IsDisabled")
    public Boolean getIsDisabled() {
        return this.isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Column(name = "Industry")
    public String getIndustry() { return industry; }

    public void setIndustry(String industry) { this.industry = industry; }

    @Column(name = "IndustryOthers")
    public String getIndustryOther() { return industryOther; }

    public void setIndustryOther(String industryOther) { this.industryOther = industryOther; }

    @Column(name = "SendMsgNum")
    public Integer getSendMsgNum() { return sendMsgNum; }

    public void setSendMsgNum(Integer sendMsgNum) { this.sendMsgNum = sendMsgNum; }

    @Column(name = "SendMsgTime")
    public Date getSendMsgTime() { return sendMsgTime; }

    public void setSendMsgTime(Date sendMsgDate) { this.sendMsgTime = sendMsgDate; }

    @Column(name = "LangFlag")
    public String getLangFlag() { return langFlag; }

    public void setLangFlag(String langFlag) { this.langFlag = langFlag; }

    @Column(name = "VisitDate")
    public String getVisitDate() { return visitDate; }

    public void setVisitDate(String visitDate) { this.visitDate = visitDate; }

    @Column(name = "BeenToFair")
    public String getBeenToFair() { return beenToFair; }

    public void setBeenToFair(String beenToFair) { this.beenToFair = beenToFair; }

    @Column(name = "BeenToRole")
    public String getBeenToRole() { return beenToRole; }

    public void setBeenToRole(String beenToRole) { this.beenToRole = beenToRole; }

    @Column(name = "RecieveEmail")
    public Boolean getIsRecieveEmail() { return isRecieveEmail; }

    public void setIsRecieveEmail(Boolean recieveEmail) { isRecieveEmail = recieveEmail; }

    @Column(name = "RecieveDoc")
    public Boolean getIsRecieveDoc() { return isRecieveDoc; }

    public void setIsRecieveDoc(Boolean recieveDoc) { isRecieveDoc = recieveDoc; }

    @Column(name = "IsMobile")
    public Boolean getIsMobile() { return isMobile; }

    public void setIsMobile(Boolean mobile) { isMobile = mobile; }

    @Column(name = "Isjudged")
    public Boolean getIsjudged() { return isjudged; }

    public void setIsjudged(Boolean isjudged) { this.isjudged = isjudged; }

    @Column(name = "IsProfessional")
    public Integer getIsProfessional() { return isProfessional; }

    public void setIsProfessional(Integer professional) { isProfessional = professional; }

    @Column(name = "IsAccommodation")
    public Boolean getIsAccommodation() { return isAccommodation; }

    public void setIsAccommodation(Boolean accommodation) { isAccommodation = accommodation; }

    @Column(name = "IsReaded")
    public Boolean getIsReaded() { return isReaded; }

    public void setIsReaded(Boolean readed) { isReaded = readed; }

    @Column(name = "tmp_Country")
    public String getTmp_Country() { return tmp_Country; }

    public void setTmp_Country(String tmp_Country) { this.tmp_Country = tmp_Country; }

    @Column(name = "tmp_Postcode")
    public String getTmp_Postcode() { return tmp_Postcode; }

    public void setTmp_Postcode(String tmp_Postcode) { this.tmp_Postcode = tmp_Postcode; }

    @Column(name = "tmp_Interest")
    public String getTmp_Interest() { return tmp_Interest; }

    public void setTmp_Interest(String tmp_Interest) { this.tmp_Interest = tmp_Interest; }

    @Column(name = "tmp_InterestOthers")
    public String getTmp_InterestOthers() { return tmp_InterestOthers; }

    public void setTmp_InterestOthers(String tmp_InterestOthers) { this.tmp_InterestOthers = tmp_InterestOthers; }

    @Column(name = "tmp_Knowfrom")
    public String getTmp_Knowfrom() { return tmp_Knowfrom; }

    public void setTmp_Knowfrom(String tmp_Knowfrom) { this.tmp_Knowfrom = tmp_Knowfrom; }

    @Column(name = "tmp_KnowfromOthers")
    public String getTmp_KnowfromOthers() { return tmp_KnowfromOthers; }

    public void setTmp_KnowfromOthers(String tmp_KnowfromOthers) { this.tmp_KnowfromOthers = tmp_KnowfromOthers; }

    @Column(name = "tmp_V_name1")
    public String getTmp_V_name1() { return tmp_V_name1; }

    public void setTmp_V_name1(String tmp_V_name1) { this.tmp_V_name1 = tmp_V_name1; }

    @Column(name = "tmp_V_title1")
    public String getTmp_V_title1() { return tmp_V_title1; }

    public void setTmp_V_title1(String tmp_V_title1) { this.tmp_V_title1 = tmp_V_title1; }

    @Column(name = "tmp_V_position1")
    public String getTmp_V_position1() { return tmp_V_position1; }

    public void setTmp_V_position1(String tmp_V_position1) { this.tmp_V_position1 = tmp_V_position1; }

    @Column(name = "tmp_V_contact1")
    public String getTmp_V_contact1() { return tmp_V_contact1; }

    public void setTmp_V_contact1(String tmp_V_contact1) { this.tmp_V_contact1 = tmp_V_contact1; }

    @Column(name = "tmp_V_name2")
    public String getTmp_V_name2() { return tmp_V_name2; }

    public void setTmp_V_name2(String tmp_V_name2) { this.tmp_V_name2 = tmp_V_name2; }

    @Column(name = "tmp_V_title2")
    public String getTmp_V_title2() { return tmp_V_title2; }

    public void setTmp_V_title2(String tmp_V_title2) { this.tmp_V_title2 = tmp_V_title2; }

    @Column(name = "tmp_V_position2")
    public String getTmp_V_position2() { return tmp_V_position2; }

    public void setTmp_V_position2(String tmp_V_position2) { this.tmp_V_position2 = tmp_V_position2; }

    @Column(name = "tmp_V_contact2")
    public String getTmp_V_contact2() { return tmp_V_contact2; }

    public void setTmp_V_contact2(String tmp_V_contact2) { this.tmp_V_contact2 = tmp_V_contact2; }

    @Column(name = "tmp_V_name3")
    public String getTmp_V_name3() { return tmp_V_name3; }

    public void setTmp_V_name3(String tmp_V_name3) { this.tmp_V_name3 = tmp_V_name3; }

    @Column(name = "tmp_V_title3")
    public String getTmp_V_title3() { return tmp_V_title3; }

    public void setTmp_V_title3(String tmp_V_title3) { this.tmp_V_title3 = tmp_V_title3; }

    @Column(name = "tmp_V_position3")
    public String getTmp_V_position3() { return tmp_V_position3; }

    public void setTmp_V_position3(String tmp_V_position3) { this.tmp_V_position3 = tmp_V_position3; }

    @Column(name = "tmp_V_contact3")
    public String getTmp_V_contact3() { return tmp_V_contact3; }

    public void setTmp_V_contact3(String tmp_V_contact3) { this.tmp_V_contact3 = tmp_V_contact3; }

    @Column(name = "GUID")
    public String getGuid() { return this.guid; }

    public void setGuid(String guid) { this.guid = guid; }

    @Column(name = "govement")
    public Integer getGovement() {
        return govementFlag;
    }

    public void setGovement(Integer govement) {
        this.govementFlag = govement;
    }

    @Column(name = "rabbi")
    public String getRabbi() {
        return rabbi;
    }

    public void setRabbi(String rabbi) {
        this.rabbi = rabbi;
    }

    @Column(name = "customer_type")
    public Integer getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(Integer customer_type) {
        this.customer_type = customer_type;
    }
}
