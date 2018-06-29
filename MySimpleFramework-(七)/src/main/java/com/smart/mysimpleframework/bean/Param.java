package com.smart.mysimpleframework.bean;


import java.util.Map;

/*
*
*  URL参数存储对象
*  用来存储前端发送过来的参数
* */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String,Object> map) {
        this.paramMap = map;

    }

    /*
     *  根据参数名获取long型参数
     * */
    public long getLong(String name) {
        Object o = paramMap.get(name);
        String s = String.valueOf(o);
        return Long.parseLong(s);

    }

    /*
    *  获取所有字段信息
    * */

    public Map<String, Object> getMap() {
        return paramMap;
    }
}
