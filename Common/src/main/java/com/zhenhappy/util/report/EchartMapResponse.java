package com.zhenhappy.util.report;

import com.zhenhappy.ems.dto.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxd on 2016/4/18.
 */
public class EchartMapResponse extends BaseResponse {
    public String legend;//数据分组
    public List<String> mapProvince = new ArrayList<String>();//横坐标
    public String mapData;//纵坐标
    public String mapDataEx;//纵坐标

    public EchartMapResponse() {
    }

    public EchartMapResponse(String legend, List<String> categoryList, String data, String dataEx) {
        this.legend = legend;
        this.mapProvince = categoryList;
        this.mapData = data;
        this.mapDataEx = dataEx;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public List<String> getCategory() {
        return mapProvince;
    }

    public void setCategory(List<String> category) {
        this.mapProvince = category;
    }

    public String getData() {
        return mapData;
    }

    public void setData(String data) {
        this.mapData = data;
    }

    public String getMapDataEx() {
        return mapDataEx;
    }

    public void setMapDataEx(String mapDataEx) {
        this.mapDataEx = mapDataEx;
    }
}
