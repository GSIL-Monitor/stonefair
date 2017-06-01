package com.zhenhappy.util.report;

import java.util.List;

/**
 * Created by Administrator on 2016/4/17.
 */
public class Series {
    public String name;
    public String type;
    public List<Integer> data;//这里要用int 不能用String 不然前台显示不正常（特别是在做数学运算的时候）

    public Series( String name, String type, List<Integer> data) {
        super();
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
