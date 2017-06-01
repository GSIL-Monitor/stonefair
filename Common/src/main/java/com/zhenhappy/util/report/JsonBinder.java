package com.zhenhappy.util.report;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

/**
 * Created by wangxd on 2016/4/18.
 */
public class JsonBinder {
    private static Logger logger = LogManager.getLogger(JsonBinder.class);
    private ObjectMapper mapper;
    @SuppressWarnings("deprecation")
    private JsonBinder( JsonSerialize.Inclusion inclusion) {
        mapper = new ObjectMapper();

        //设置输出包含的属性
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);

        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        mapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    public JsonBinder(){

    }
    /**
     * 创建输出全部属性到Json字符串的Binder.
     */
    public static JsonBinder buildNormalBinder() {
        return new JsonBinder(JsonSerialize.Inclusion.ALWAYS);
    }
    /**
     * 创建只输出非空属性到Json字符串的Binder.
     */
    public static JsonBinder buildNonNullBinder() {
        return new JsonBinder(JsonSerialize.Inclusion.NON_NULL);
    }
    /**
     * 创建只输出初始值被改变的属性到Json字符串的Binder.
     */
    public static JsonBinder buildNonDefaultBinder() {
        return new JsonBinder(JsonSerialize.Inclusion.NON_DEFAULT);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Binder.
     */
    public static JsonBinder buildNonEmptyBinder() {
        return new JsonBinder(JsonSerialize.Inclusion.NON_EMPTY);
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     *
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }
}
