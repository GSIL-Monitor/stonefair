package com.zhenhappy.ems.manager.entity.visitorgroup;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-12-26.
 */
@Entity
@Table(name = "t_group_Info", schema = "dbo")
public class TVisitorGroupInfo {
    private Integer id;
    private String groupName;       //参观团名称
    private Integer leaderID;       //参观团团长对应ID
    private Date createTime;        //参观团成员注册时间
    private String createIP;        //参观团成员注册对应IP
    private Date updateTime;        //参观团成员更新时间
    private String updateIP;        //参观团成员更新对应IP
    private Integer isDisabled;     //是否可用
    private String gUID;

    public TVisitorGroupInfo() {
    }

    public TVisitorGroupInfo(Integer id, String groupName, Integer leaderID, Date createTime, String createIP,
                             Date updateTime, String updateIP, Integer isDisabled, String gUID) {
        this.id = id;
        this.groupName = groupName;
        this.leaderID = leaderID;
        this.createTime = createTime;
        this.createIP = createIP;
        this.updateTime = updateTime;
        this.updateIP = updateIP;
        this.isDisabled = isDisabled;
        this.gUID = gUID;
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

    @Column(name = "GroupName")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column(name = "GUID")
    public String getgUID() {
        return gUID;
    }

    public void setgUID(String gUID) {
        this.gUID = gUID;
    }

    @Column(name = "IsDisabled")
    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    @Column(name = "LeaderID")
    public Integer getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(Integer leaderID) {
        this.leaderID = leaderID;
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
}
