package com.zhenhappy.ems.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangxd on 2016/4/11.
 */
@Entity
@Table(name = "visitor_Log_Msg", schema = "dbo")
public class TVisitorMsgLog {
    private Integer id;
    private Integer CustomerID;
    private String LogSubject;
    private String LogContent;
    private String MsgSubject;
    private String MsgContent;
    private String MsgFrom;
    private String MsgTo;
    private Date CreateTime;
    private String CreateIP;
    private Integer Reply;
    private Integer Status;
    private String GUID;

    public TVisitorMsgLog() {
        super();
    }

    public TVisitorMsgLog(Integer id, Integer CustomerID, String LogSubject,
                          String LogContent, String MsgSubject, String MsgContent,
                          String MsgFrom, String MsgTo, Date CreateTime,
                          String CreateIP, Integer Reply, Integer Status, String GUID) {
        super();
        this.id = id;
        this.CustomerID = CustomerID;
        this.LogSubject = LogSubject;
        this.LogContent = LogContent;
        this.MsgSubject = MsgSubject;
        this.MsgContent = MsgContent;
        this.MsgFrom = MsgFrom;
        this.MsgTo = MsgTo;
        this.CreateTime = CreateTime;
        this.CreateIP = CreateIP;
        this.Reply = Reply;
        this.Status = Status;
        this.GUID = GUID;
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

    @Basic
    @Column(name = "CustomerID")
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    @Basic
    @Column(name = "LogSubject")
    public String getLogSubject() {
        return LogSubject;
    }

    public void setLogSubject(String logSubject) {
        LogSubject = logSubject;
    }

    @Basic
    @Column(name = "LogContent")
    public String getLogContent() {
        return LogContent;
    }

    public void setLogContent(String logContent) {
        LogContent = logContent;
    }

    @Basic
    @Column(name = "MsgSubject")
    public String getMsgSubject() {
        return MsgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        MsgSubject = msgSubject;
    }

    @Basic
    @Column(name = "MsgContent")
    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    @Basic
    @Column(name = "MsgFrom")
    public String getMsgFrom() {
        return MsgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        MsgFrom = msgFrom;
    }

    @Basic
    @Column(name = "MsgTo")
    public String getMsgTo() {
        return MsgTo;
    }

    public void setMsgTo(String msgTo) {
        MsgTo = msgTo;
    }

    @Basic
    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    @Basic
    @Column(name = "CreateIP")
    public String getCreateIP() {
        return CreateIP;
    }

    public void setCreateIP(String createIP) {
        CreateIP = createIP;
    }

    @Basic
    @Column(name = "Reply")
    public Integer getReply() {
        return Reply;
    }

    public void setReply(Integer reply) {
        Reply = reply;
    }

    @Basic
    @Column(name = "Status")
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    @Basic
    @Column(name = "GUID")
    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TVisitorMsgLog that = (TVisitorMsgLog) o;

        if (CustomerID != null ? !CustomerID.equals(that.CustomerID) : that.CustomerID != null) return false;
        if (LogSubject != null ? !LogSubject.equals(that.LogSubject) : that.LogSubject != null) return false;
        if (LogContent != null ? !LogContent.equals(that.LogContent) : that.LogContent != null) return false;
        if (MsgSubject != null ? !MsgSubject.equals(that.MsgSubject) : that.MsgSubject != null) return false;
        if (MsgContent != null ? !MsgContent.equals(that.MsgContent) : that.MsgContent != null) return false;
        if (MsgFrom != null ? !MsgFrom.equals(that.MsgFrom) : that.MsgFrom != null) return false;
        if (MsgTo != null ? !MsgTo.equals(that.MsgTo) : that.MsgTo != null) return false;
        if (CreateTime != null ? !CreateTime.equals(that.CreateTime) : that.CreateTime != null) return false;
        if (CreateIP != null ? !CreateIP.equals(that.CreateIP) : that.CreateIP != null) return false;
        if (Reply != null ? !Reply.equals(that.Reply) : that.Reply != null) return false;
        if (Status != null ? !Status.equals(that.Status) : that.Status != null) return false;
        if (GUID != null ? !GUID.equals(that.GUID) : that.GUID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = CustomerID != null ? CustomerID.hashCode() : 0;
        result = 31 * result + (LogSubject != null ? LogSubject.hashCode() : 0);
        result = 31 * result + (LogContent != null ? LogContent.hashCode() : 0);
        result = 31 * result + (MsgSubject != null ? MsgSubject.hashCode() : 0);
        result = 31 * result + (MsgContent != null ? MsgContent.hashCode() : 0);
        result = 31 * result + (MsgFrom != null ? MsgFrom.hashCode() : 0);
        result = 31 * result + (MsgTo != null ? MsgTo.hashCode() : 0);
        result = 31 * result + (CreateTime != null ? CreateTime.hashCode() : 0);
        result = 31 * result + (CreateIP != null ? CreateIP.hashCode() : 0);
        result = 31 * result + (Reply != null ? Reply.hashCode() : 0);
        result = 31 * result + (Status != null ? Status.hashCode() : 0);
        result = 31 * result + (GUID != null ? GUID.hashCode() : 0);

        return result;
    }
}
