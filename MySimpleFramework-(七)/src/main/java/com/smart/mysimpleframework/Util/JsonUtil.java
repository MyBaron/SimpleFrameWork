package com.smart.mysimpleframework.Util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/*
*  json 工具类
* */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /*
     *  将POJO 转为JSON
     * */
    public static <T> String toJson(T obj) {
        String json=null;
        try {
            json= OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("POJO转换JSON 错误 ",e);
        }
        return json;
    }


    /*
     *  将json 转为POJO
     * */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo = null;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("JSON转换成POJO错误",e);
        }
        return pojo;
    }
}
