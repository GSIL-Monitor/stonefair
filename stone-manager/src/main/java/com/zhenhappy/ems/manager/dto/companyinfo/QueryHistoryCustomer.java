package com.zhenhappy.ems.manager.dto.companyinfo;

import java.util.Date;

/**
 * Created by wangxd on 2016-05-24.
 */
public class QueryHistoryCustomer {
    private Integer id;
    private String cateory;
    private String company;
    private Integer country;
    private String address;
    private String contact;
    private String position;
    private String telphone;
    private String email;
    private String fixtelphone;
    private String fax;
    private String website;
    private String backupaddress;
    private String remark;
    private Integer owner;
    private Date createtime;
    private Date updatetime;
    private String updateowner;
    private Integer isDelete;

    public QueryHistoryCustomer() {
        super();
    }

    public QueryHistoryCustomer(Integer id, String cateory, String company, Integer country, String address,
                                String contact, String position, String telphone, String email,
                                String fixtelphone, String fax, String website, String backupaddress,
                                String remark, Integer owner, Date createtime, Date updatetime, String updateowner, Integer isDelete) {
        this.id = id;
        this.cateory = cateory;
        this.company = company;
        this.country = country;
        this.address = address;
        this.contact = contact;
        this.position = position;
        this.telphone = telphone;
        this.email = email;
        this.fixtelphone = fixtelphone;
        this.fax = fax;
        this.website = website;
        this.backupaddress = backupaddress;
        this.remark = remark;
        this.owner = owner;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.updateowner = updateowner;
        this.isDelete = isDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCateory() {
        return cateory;
    }

    public void setCateory(String cateory) {
        this.cateory = cateory;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFixtelphone() {
        return fixtelphone;
    }

    public void setFixtelphone(String fixtelphone) {
        this.fixtelphone = fixtelphone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBackupaddress() {
        return backupaddress;
    }

    public void setBackupaddress(String backupaddress) {
        this.backupaddress = backupaddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateowner() {
        return updateowner;
    }

    public void setUpdateowner(String updateowner) {
        this.updateowner = updateowner;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
