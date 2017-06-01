package com.zhenhappy.util.report;

import java.io.Serializable;

/**
 * Created by wangxd on 2016/4/18.
 */
public class JsonDataForReport implements Serializable {
    private String name;
    private Integer value;

    public JsonDataForReport() {}

    public JsonDataForReport(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String cityName) {
        this.name = cityName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
