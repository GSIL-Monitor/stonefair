package com.zhenhappy.ems.manager.entity.visitorgroup;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-12-26.
 */
@Entity
@Table(name = "t_group_Member", schema = "dbo")
public class TVisitorMemberInfo {
    private Integer id;
    private String checkingNo;
    private String memberName;
    private Integer groupID;
    private Integer inviterID;
    private String email;
    private String mobile;
    private String password;
    private String company;
    private String position;
    private String address;
    private Date createTime;
    private String createIP;
    private Date updateTime;
    private String updateIP;
    private Integer isLeader;
    private Integer isDisabled;
    private String gUID;
    private Integer emailNum;
    private Integer msgNum;

    public TVisitorMemberInfo() {
    }

    public TVisitorMemberInfo(Integer id) {
        this.id = id;
    }

    public TVisitorMemberInfo(Integer id, String checkingNo, String memberName, Integer groupID, Integer inviterID,
                              String email, String mobile, String password, String company, String position, String address,
                              Date createTime, String createIP, Date updateTime, String updateIP, Integer isLeader, Integer isDisabled,
                              String gUID, Integer emailNum, Integer msgNum) {
        this.id = id;
        this.checkingNo = checkingNo;
        this.memberName = memberName;
        this.groupID = groupID;
        this.inviterID = inviterID;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.company = company;
        this.position = position;
        this.address = address;
        this.createTime = createTime;
        this.createIP = createIP;
        this.updateTime = updateTime;
        this.updateIP = updateIP;
        this.isLeader = isLeader;
        this.isDisabled = isDisabled;
        this.gUID = gUID;
        this.emailNum = emailNum;
        this.msgNum = msgNum;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "CheckingNo")
    public String getCheckingNo() {
        return checkingNo;
    }

    public void setCheckingNo(String checkingNo) {
        this.checkingNo = checkingNo;
    }

    @Column(name = "Company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "CreateIP")
    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "GroupID")
    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    @Column(name = "GUID")
    public String getgUID() {
        return gUID;
    }

    public void setgUID(String gUID) {
        this.gUID = gUID;
    }

    @Column(name = "InviterID")
    public Integer getInviterID() {
        return inviterID;
    }

    public void setInviterID(Integer inviterID) {
        this.inviterID = inviterID;
    }

    @Column(name = "IsDisabled")
    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Column(name = "IsLeader")
    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    @Column(name = "MemberName")
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Column(name = "Mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "Position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column(name = "UpdateIP")
    public String getUpdateIP() {
        return updateIP;
    }

    public void setUpdateIP(String updateIP) {
        this.updateIP = updateIP;
    }

    @Column(name = "UpdateTime")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "EmailNum")
    public Integer getEmailNum() {
        return emailNum;
    }

    public void setEmailNum(Integer emailNum) {
        this.emailNum = emailNum;
    }

    @Column(name = "MsgNum")
    public Integer getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(Integer msgNum) {
        this.msgNum = msgNum;
    }
}
