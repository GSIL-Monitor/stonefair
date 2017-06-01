package com.zhenhappy.ems.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangxd on 2016/4/11.
 */
@Entity
@Table(name = "visitor_Log_Mail", schema = "dbo")
public class TVisitorMailLog {
    private Integer id;
    private Integer CustomerID;
    private String LogSubject;
    private String LogContent;
    private String MailSubject;
    private String MailContent;
    private String MailFrom;
    private String MailTo;
    private Date CreateTime;
    private String CreateIP;
    private Integer Reply;
    private Integer Status;
    private String GUID;

    public TVisitorMailLog() {
        super();
    }

    public TVisitorMailLog(Integer id) {
        this.id = id;
    }

    public TVisitorMailLog(Integer CustomerID, String LogSubject,
                           String LogContent, String MailSubject, String MailContent,
                           String MailFrom,String MailTo,Date CreateTime,
                           String CreateIP,Integer Reply, Integer Status, String GUID) {
        this.CustomerID = CustomerID;
        this.LogSubject = LogSubject;
        this.LogContent = LogContent;
        this.MailSubject = MailSubject;
        this.MailContent = MailContent;
        this.MailFrom = MailFrom;
        this.MailTo = MailTo;
        this.CreateTime = CreateTime;
        this.CreateIP = CreateIP;
        this.Reply = Reply;
        this.Status = Status;
        this.GUID = GUID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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
    @Column(name = "MailSubject")
    public String getMailSubject() {
        return MailSubject;
    }

    public void setMailSubject(String mailSubject) {
        MailSubject = mailSubject;
    }

    @Basic
    @Column(name = "MailContent")
    public String getMailContent() {
        return MailContent;
    }

    public void setMailContent(String mailContent) {
        MailContent = mailContent;
    }

    @Basic
    @Column(name = "MailFrom")
    public String getMailFrom() {
        return MailFrom;
    }

    public void setMailFrom(String mailFrom) {
        MailFrom = mailFrom;
    }

    @Basic
    @Column(name = "MailTo")
    public String getMailTo() {
        return MailTo;
    }

    public void setMailTo(String mailTo) {
        MailTo = mailTo;
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

        TVisitorMailLog that = (TVisitorMailLog) o;

        if (CustomerID != null ? !CustomerID.equals(that.CustomerID) : that.CustomerID != null) return false;
        if (LogSubject != null ? !LogSubject.equals(that.LogSubject) : that.LogSubject != null) return false;
        if (LogContent != null ? !LogContent.equals(that.LogContent) : that.LogContent != null) return false;
        if (MailSubject != null ? !MailSubject.equals(that.MailSubject) : that.MailSubject != null) return false;
        if (MailContent != null ? !MailContent.equals(that.MailContent) : that.MailContent != null) return false;
        if (MailFrom != null ? !MailFrom.equals(that.MailFrom) : that.MailFrom != null) return false;
        if (MailTo != null ? !MailTo.equals(that.MailTo) : that.MailTo != null) return false;
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
        result = 31 * result + (MailSubject != null ? MailSubject.hashCode() : 0);
        result = 31 * result + (MailContent != null ? MailContent.hashCode() : 0);
        result = 31 * result + (MailFrom != null ? MailFrom.hashCode() : 0);
        result = 31 * result + (MailTo != null ? MailTo.hashCode() : 0);
        result = 31 * result + (CreateTime != null ? CreateTime.hashCode() : 0);
        result = 31 * result + (CreateIP != null ? CreateIP.hashCode() : 0);
        result = 31 * result + (Reply != null ? Reply.hashCode() : 0);
        result = 31 * result + (Status != null ? Status.hashCode() : 0);
        result = 31 * result + (GUID != null ? GUID.hashCode() : 0);

        return result;
    }
}
