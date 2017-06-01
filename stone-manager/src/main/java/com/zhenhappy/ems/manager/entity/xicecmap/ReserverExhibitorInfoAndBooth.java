package com.zhenhappy.ems.manager.entity.xicecmap;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/22.
 */
public class ReserverExhibitorInfoAndBooth implements Serializable {
    private String boothNum;
    private String tagName;

    public ReserverExhibitorInfoAndBooth() {
    }

    public ReserverExhibitorInfoAndBooth(String boothNum, String tagName) {
        this.boothNum = boothNum;
        this.tagName = tagName;
    }

    public String getBoothNum() {
        return boothNum;
    }

    public void setBoothNum(String boothNum) {
        this.boothNum = boothNum;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
