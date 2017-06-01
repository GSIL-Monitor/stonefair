package com.zhenhappy.ems.entity;

import com.zhenhappy.util.report.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxd9 on 2016/4/17.
 */
public class EchartData {
    public List<String> legend = new ArrayList<String>();//数据分组
    public List<String> category = new ArrayList<String>();//横坐标
    public List<Series> series = new ArrayList<Series>();//纵坐标


    public EchartData(List<String> legendList, List<String> categoryList, List<Series> seriesList) {
        super();
        this.legend = legendList;
        this.category = categoryList;
        this.series = seriesList;
    }
}
