package com.zhenhappy.ems.manager.entity.xicecmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/22.
 */
public class SelloutExhibitorInfoAndBooth implements Serializable {
    private String boothNum;
    private String company;

    public SelloutExhibitorInfoAndBooth() {
    }

    public SelloutExhibitorInfoAndBooth(String boothNum, String company) {
        this.boothNum = boothNum;
        this.company = company;
    }

    public String getBoothNum() {
        return boothNum;
    }

    public void setBoothNum(String boothNum) {
        this.boothNum = boothNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
