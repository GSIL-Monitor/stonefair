package com.zhenhappy.ems.teatime;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-06-28.
 * 用于设置前台展商相关功能涉及到的时间
 */
@Entity
@Table(name = "t_exhibitor_teafair_time", schema = "dbo")
public class TExhibitorTeaTime {
    private Integer id;
    private String tea_Fair_Show_Date_Zh;
    private String exhibitor_Info_Submit_Deadline_Zh;
    private String tea_Fair_Show_Date_En;
    private String exhibitor_Info_Submit_Deadline_En;
    private String tea_Fair_Show_Year;
    private String tea_Fair_Show_Begin_Date;
    private String tea_Fair_Data_End_Html;
    private String tea_Fair_Contact_Submit_Deadline_Zh;
    private String tea_Fair_Contact_Submit_Deadline_En;
    private String tea_Fair_Invoice_Submit_Deadline_Zh;
    private String tea_Fair_Invoice_Submit_Deadline_En;
    private String tea_Fair_Visa_Submit_Deadline_Zh;
    private String tea_Fair_Visa_Submit_Deadline_En;

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
    @Column(name = "tea_Fair_Show_Date_Zh")
    public String getTea_Fair_Show_Date_Zh() {
        return tea_Fair_Show_Date_Zh;
    }

    public void setTea_Fair_Show_Date_Zh(String tea_Fair_Show_Date_Zh) {
        this.tea_Fair_Show_Date_Zh = tea_Fair_Show_Date_Zh;
    }

    @Basic
    @Column(name = "exhibitor_Info_Submit_Deadline_Zh")
    public String getExhibitor_Info_Submit_Deadline_Zh() {
        return exhibitor_Info_Submit_Deadline_Zh;
    }

    public void setExhibitor_Info_Submit_Deadline_Zh(String exhibitor_Info_Submit_Deadline_Zh) {
        this.exhibitor_Info_Submit_Deadline_Zh = exhibitor_Info_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "tea_Fair_Show_Date_En")
    public String getTea_Fair_Show_Date_En() {
        return tea_Fair_Show_Date_En;
    }

    public void setTea_Fair_Show_Date_En(String tea_Fair_Show_Date_En) {
        this.tea_Fair_Show_Date_En = tea_Fair_Show_Date_En;
    }

    @Basic
    @Column(name = "exhibitor_Info_Submit_Deadline_En")
    public String getExhibitor_Info_Submit_Deadline_En() {
        return exhibitor_Info_Submit_Deadline_En;
    }

    public void setExhibitor_Info_Submit_Deadline_En(String exhibitor_Info_Submit_Deadline_En) {
        this.exhibitor_Info_Submit_Deadline_En = exhibitor_Info_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "tea_Fair_Show_Year")
    public String getTea_Fair_Show_Year() {
        return tea_Fair_Show_Year;
    }

    public void setTea_Fair_Show_Year(String tea_Fair_Show_Year) {
        this.tea_Fair_Show_Year = tea_Fair_Show_Year;
    }

    @Basic
    @Column(name = "tea_Fair_Show_Begin_Date")
    public String getTea_Fair_Show_Begin_Date() {
        return tea_Fair_Show_Begin_Date;
    }

    public void setTea_Fair_Show_Begin_Date(String tea_Fair_Show_Begin_Date) {
        this.tea_Fair_Show_Begin_Date = tea_Fair_Show_Begin_Date;
    }

    @Basic
    @Column(name = "tea_Fair_Data_End_Html")
    public String getTea_Fair_Data_End_Html() {
        return tea_Fair_Data_End_Html;
    }

    public void setTea_Fair_Data_End_Html(String tea_Fair_Data_End_Html) {
        this.tea_Fair_Data_End_Html = tea_Fair_Data_End_Html;
    }

    @Basic
    @Column(name = "tea_Fair_Contact_Submit_Deadline_Zh")
    public String getTea_Fair_Contact_Submit_Deadline_Zh() {
        return tea_Fair_Contact_Submit_Deadline_Zh;
    }

    public void setTea_Fair_Contact_Submit_Deadline_Zh(String tea_Fair_Contact_Submit_Deadline_Zh) {
        this.tea_Fair_Contact_Submit_Deadline_Zh = tea_Fair_Contact_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "tea_Fair_Contact_Submit_Deadline_En")
    public String getTea_Fair_Contact_Submit_Deadline_En() {
        return tea_Fair_Contact_Submit_Deadline_En;
    }

    public void setTea_Fair_Contact_Submit_Deadline_En(String tea_Fair_Contact_Submit_Deadline_En) {
        this.tea_Fair_Contact_Submit_Deadline_En = tea_Fair_Contact_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "tea_Fair_Invoice_Submit_Deadline_En")
    public String getTea_Fair_Invoice_Submit_Deadline_En() {
        return tea_Fair_Invoice_Submit_Deadline_En;
    }

    public void setTea_Fair_Invoice_Submit_Deadline_En(String tea_Fair_Invoice_Submit_Deadline_En) {
        this.tea_Fair_Invoice_Submit_Deadline_En = tea_Fair_Invoice_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "tea_Fair_Invoice_Submit_Deadline_Zh")
    public String getTea_Fair_Invoice_Submit_Deadline_Zh() {
        return tea_Fair_Invoice_Submit_Deadline_Zh;
    }

    public void setTea_Fair_Invoice_Submit_Deadline_Zh(String tea_Fair_Invoice_Submit_Deadline_Zh) {
        this.tea_Fair_Invoice_Submit_Deadline_Zh = tea_Fair_Invoice_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "tea_Fair_Visa_Submit_Deadline_Zh")
    public String getTea_Fair_Visa_Submit_Deadline_Zh() {
        return tea_Fair_Visa_Submit_Deadline_Zh;
    }

    public void setTea_Fair_Visa_Submit_Deadline_Zh(String tea_Fair_Visa_Submit_Deadline_Zh) {
        this.tea_Fair_Visa_Submit_Deadline_Zh = tea_Fair_Visa_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "tea_Fair_Visa_Submit_Deadline_En")
    public String getTea_Fair_Visa_Submit_Deadline_En() {
        return tea_Fair_Visa_Submit_Deadline_En;
    }

    public void setTea_Fair_Visa_Submit_Deadline_En(String tea_Fair_Visa_Submit_Deadline_En) {
        this.tea_Fair_Visa_Submit_Deadline_En = tea_Fair_Visa_Submit_Deadline_En;
    }
}
