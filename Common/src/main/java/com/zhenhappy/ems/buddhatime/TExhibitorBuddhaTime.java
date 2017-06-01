package com.zhenhappy.ems.buddhatime;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-06-28.
 * 用于设置前台展商相关功能涉及到的时间
 */
@Entity
@Table(name = "t_exhibitor_buddha_time", schema = "dbo")
public class TExhibitorBuddhaTime {
    private Integer id;
    private String buddha_Fair_Show_Date;
    private String exhibitor_Info_Submit_Deadline;
    private String buddha_Fair_Show_Date_Begin;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "buddha_Fair_Show_Date")
    public String getBuddha_Fair_Show_Date() {
        return buddha_Fair_Show_Date;
    }

    public void setBuddha_Fair_Show_Date(String buddha_Fair_Show_Date) {
        this.buddha_Fair_Show_Date = buddha_Fair_Show_Date;
    }

    @Column(name = "exhibitor_Info_Submit_Deadline")
    public String getExhibitor_Info_Submit_Deadline() {
        return exhibitor_Info_Submit_Deadline;
    }

    public void setExhibitor_Info_Submit_Deadline(String exhibitor_Info_Submit_Deadline) {
        this.exhibitor_Info_Submit_Deadline = exhibitor_Info_Submit_Deadline;
    }

    @Column(name = "buddha_Fair_Show_Date_Begin")
    public String getBuddha_Fair_Show_Date_Begin() {
        return buddha_Fair_Show_Date_Begin;
    }

    public void setBuddha_Fair_Show_Date_Begin(String buddha_Fair_Show_Date_Begin) {
        this.buddha_Fair_Show_Date_Begin = buddha_Fair_Show_Date_Begin;
    }
}
