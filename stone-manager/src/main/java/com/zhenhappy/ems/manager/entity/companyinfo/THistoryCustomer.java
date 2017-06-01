package com.zhenhappy.ems.manager.entity.companyinfo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangxd on 2016-05-24.
 */
@Entity
@Table(name = "t_history_customer", schema = "dbo")
public class THistoryCustomer {
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

    public THistoryCustomer() {
        super();
    }

    public THistoryCustomer(Integer country) {
        this.country = country;
    }

    public THistoryCustomer(Integer id, String cateory, String company, Integer country, String address,
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

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "cateory")
    public String getCateory() {
        return cateory;
    }

    public void setCateory(String cateory) {
        this.cateory = cateory;
    }

    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "country")
    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "telphone")
    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fixtelphone")
    public String getFixtelphone() {
        return fixtelphone;
    }

    public void setFixtelphone(String fixtelphone) {
        this.fixtelphone = fixtelphone;
    }

    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "website")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(name = "backupaddress")
    public String getBackupaddress() {
        return backupaddress;
    }

    public void setBackupaddress(String backupaddress) {
        this.backupaddress = backupaddress;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "owner")
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "updatetime")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Column(name = "updateowner")
    public String getUpdateowner() {
        return updateowner;
    }

    public void setUpdateowner(String updateowner) {
        this.updateowner = updateowner;
    }

    @Column(name = "isDelete")
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cateory != null ? cateory.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (telphone != null ? telphone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fixtelphone != null ? fixtelphone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (backupaddress != null ? backupaddress.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (updateowner != null ? updateowner.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        THistoryCustomer other = (THistoryCustomer) obj;
        if (cateory == null) {
            if (other.cateory != null)
                return false;
        } else if (!cateory.equals(other.cateory))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (contact == null) {
            if (other.contact != null)
                return false;
        } else if (!contact.equals(other.contact))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (telphone == null) {
            if (other.telphone != null)
                return false;
        } else if (!telphone.equals(other.telphone))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (fixtelphone == null) {
            if (other.fixtelphone != null)
                return false;
        } else if (!fixtelphone.equals(other.fixtelphone))
            return false;
        if (fax == null) {
            if (other.fax != null)
                return false;
        } else if (!fax.equals(other.fax))
            return false;
        if (website == null) {
            if (other.website != null)
                return false;
        } else if (!website.equals(other.website))
            return false;
        if (backupaddress == null) {
            if (other.backupaddress != null)
                return false;
        } else if (!backupaddress.equals(other.backupaddress))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        return true;
    }
}
