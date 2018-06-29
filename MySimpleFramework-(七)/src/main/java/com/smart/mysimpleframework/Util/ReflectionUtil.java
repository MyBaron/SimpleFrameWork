package com.smart.mysimpleframework.Util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
*  反射工具类
* */
public class ReflectionUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);


    /*
    *  创建实例
    * */
    public static Object newInstance(Class<?> cls) {
        Object instance = null;
        try {
            instance = cls.newInstance();

        } catch (Exception e) {
            log.error("new Instance is error",e);
            e.printStackTrace();
        }

        return instance;
    }

    /*
     *  调用方法
     * */
    public static Object invokeMethod(Object o, Method method, Object... objects) {
        Object object = null;
        try {
            //禁用安全监测。
            method.setAccessible(true);
            object=method.invoke(o, objects);
        } catch (Exception e) {
          log.error("调用方法错误",e);
          e.printStackTrace();
        }

        return object;
    }

    /*
     *  设置成员变量的值
     * */
    public static void setField(Object obj, Field field, Object value) {
        try {

            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            log.error("设置成员变量失败",e);
            e.printStackTrace();
        }
    }

}
