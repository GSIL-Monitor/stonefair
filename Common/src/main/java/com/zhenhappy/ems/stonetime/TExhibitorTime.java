package com.zhenhappy.ems.stonetime;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-06-28.
 * 用于设置前台展商相关功能涉及到的时间
 */
@Entity
@Table(name = "t_exhibitor_time", schema = "dbo")
public class TExhibitorTime {
    private Integer id;
    private String company_Info_Submit_Deadline_Zh;
    private String company_Info_Submit_Deadline_En;
    private String participant_List_Submit_Deadline_Zh;
    private String participant_List_Submit_Deadline_En;
    private String invoice_Information_Submit_Deadline_Zh;
    private String invoice_Information_Submit_Deadline_En;
    private String advertisement_Submit_Deadline_Zh;
    private String advertisement_Submit_Deadline_En;
    private String company_Info_Insert_Submit_Deadline_Zh;
    private String company_Info_Insert_Submit_Deadline_En;
    private String stone_fair_end_year;
    private String stone_fair_begin_year;
    private String company_Info_Data_End_Html;
    private String visa_Info_Submit_Deadline_Zh;
    private String visa_Info_Submit_Deadline_En;
    private String stone_Fair_Show_Date_Zh;
    private String stone_Fair_Show_Date_En;
    private Integer menu_move_switch;
    private Integer area_time;
    private String exhibitor_end_time;

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
    @Column(name = "company_Info_Submit_Deadline_Zh")
    public String getCompany_Info_Submit_Deadline_Zh() {
        return company_Info_Submit_Deadline_Zh;
    }

    public void setCompany_Info_Submit_Deadline_Zh(String company_Info_Submit_Deadline_Zh) {
        this.company_Info_Submit_Deadline_Zh = company_Info_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "company_Info_Submit_Deadline_En")
    public String getCompany_Info_Submit_Deadline_En() {
        return company_Info_Submit_Deadline_En;
    }

    public void setCompany_Info_Submit_Deadline_En(String company_Info_Submit_Deadline_En) {
        this.company_Info_Submit_Deadline_En = company_Info_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "participant_List_Submit_Deadline_Zh")
    public String getParticipant_List_Submit_Deadline_Zh() {
        return participant_List_Submit_Deadline_Zh;
    }

    public void setParticipant_List_Submit_Deadline_Zh(String participant_List_Submit_Deadline_Zh) {
        this.participant_List_Submit_Deadline_Zh = participant_List_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "participant_List_Submit_Deadline_En")
    public String getParticipant_List_Submit_Deadline_En() {
        return participant_List_Submit_Deadline_En;
    }

    public void setParticipant_List_Submit_Deadline_En(String participant_List_Submit_Deadline_En) {
        this.participant_List_Submit_Deadline_En = participant_List_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "invoice_Information_Submit_Deadline_Zh")
    public String getInvoice_Information_Submit_Deadline_Zh() {
        return invoice_Information_Submit_Deadline_Zh;
    }

    public void setInvoice_Information_Submit_Deadline_Zh(String invoice_Information_Submit_Deadline_Zh) {
        this.invoice_Information_Submit_Deadline_Zh = invoice_Information_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "invoice_Information_Submit_Deadline_En")
    public String getInvoice_Information_Submit_Deadline_En() {
        return invoice_Information_Submit_Deadline_En;
    }

    public void setInvoice_Information_Submit_Deadline_En(String invoice_Information_Submit_Deadline_En) {
        this.invoice_Information_Submit_Deadline_En = invoice_Information_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "advertisement_Submit_Deadline_Zh")
    public String getAdvertisement_Submit_Deadline_Zh() {
        return advertisement_Submit_Deadline_Zh;
    }

    public void setAdvertisement_Submit_Deadline_Zh(String advertisement_Submit_Deadline_Zh) {
        this.advertisement_Submit_Deadline_Zh = advertisement_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "advertisement_Submit_Deadline_En")
    public String getAdvertisement_Submit_Deadline_En() {
        return advertisement_Submit_Deadline_En;
    }

    public void setAdvertisement_Submit_Deadline_En(String advertisement_Submit_Deadline_En) {
        this.advertisement_Submit_Deadline_En = advertisement_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "company_Info_Insert_Submit_Deadline_Zh")
    public String getCompany_Info_Insert_Submit_Deadline_Zh() {
        return company_Info_Insert_Submit_Deadline_Zh;
    }

    public void setCompany_Info_Insert_Submit_Deadline_Zh(String company_Info_Insert_Submit_Deadline_Zh) {
        this.company_Info_Insert_Submit_Deadline_Zh = company_Info_Insert_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "company_Info_Insert_Submit_Deadline_En")
    public String getCompany_Info_Insert_Submit_Deadline_En() {
        return company_Info_Insert_Submit_Deadline_En;
    }

    public void setCompany_Info_Insert_Submit_Deadline_En(String company_Info_Insert_Submit_Deadline_En) {
        this.company_Info_Insert_Submit_Deadline_En = company_Info_Insert_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "stone_fair_end_year")
    public String getStone_fair_end_year() {
        return stone_fair_end_year;
    }

    public void setStone_fair_end_year(String stone_fair_end_year) {
        this.stone_fair_end_year = stone_fair_end_year;
    }

    @Basic
    @Column(name = "stone_fair_begin_year")
    public String getStone_fair_begin_year() {
        return stone_fair_begin_year;
    }

    public void setStone_fair_begin_year(String stone_fair_begin_year) {
        this.stone_fair_begin_year = stone_fair_begin_year;
    }

    @Basic
    @Column(name = "company_Info_Data_End_Html")
    public String getCompany_Info_Data_End_Html() {
        return company_Info_Data_End_Html;
    }

    public void setCompany_Info_Data_End_Html(String company_Info_Data_End_Html) {
        this.company_Info_Data_End_Html = company_Info_Data_End_Html;
    }

    @Basic
    @Column(name = "visa_Info_Submit_Deadline_Zh")
    public String getVisa_Info_Submit_Deadline_Zh() {
        return visa_Info_Submit_Deadline_Zh;
    }

    public void setVisa_Info_Submit_Deadline_Zh(String visa_Info_Submit_Deadline_Zh) {
        this.visa_Info_Submit_Deadline_Zh = visa_Info_Submit_Deadline_Zh;
    }

    @Basic
    @Column(name = "visa_Info_Submit_Deadline_En")
    public String getVisa_Info_Submit_Deadline_En() {
        return visa_Info_Submit_Deadline_En;
    }

    public void setVisa_Info_Submit_Deadline_En(String visa_Info_Submit_Deadline_En) {
        this.visa_Info_Submit_Deadline_En = visa_Info_Submit_Deadline_En;
    }

    @Basic
    @Column(name = "stone_Fair_Show_Date_Zh")
    public String getStone_Fair_Show_Date_Zh() {
        return stone_Fair_Show_Date_Zh;
    }

    public void setStone_Fair_Show_Date_Zh(String stone_Fair_Show_Date_Zh) {
        this.stone_Fair_Show_Date_Zh = stone_Fair_Show_Date_Zh;
    }

    @Basic
    @Column(name = "stone_Fair_Show_Date_En")
    public String getStone_Fair_Show_Date_En() {
        return stone_Fair_Show_Date_En;
    }

    public void setStone_Fair_Show_Date_En(String stone_Fair_Show_Date_En) {
        this.stone_Fair_Show_Date_En = stone_Fair_Show_Date_En;
    }

    @Basic
    @Column(name = "menu_move_switch")
    public Integer getMenu_move_switch() {
        return menu_move_switch;
    }

    public void setMenu_move_switch(Integer menu_move_switch) {
        this.menu_move_switch = menu_move_switch;
    }

    @Basic
    @Column(name = "area_time")
    public Integer getArea_time() {
        return area_time;
    }

    public void setArea_time(Integer area_time) {
        this.area_time = area_time;
    }

    @Basic
    @Column(name = "exhibitor_end_time")
    public String getExhibitor_end_time() {
        return exhibitor_end_time;
    }

    public void setExhibitor_end_time(String exhibitor_end_time) {
        this.exhibitor_end_time = exhibitor_end_time;
    }
}
