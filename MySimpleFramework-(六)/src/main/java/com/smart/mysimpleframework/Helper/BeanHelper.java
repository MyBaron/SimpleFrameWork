package com.smart.mysimpleframework.Helper;


import com.smart.mysimpleframework.Util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
*  Bean 助手类
*  加载类，建立Bean容器
* */
public final class BeanHelper {

    private static final Logger log = LoggerFactory.getLogger(BeanHelper.class);

    /*
     *  建立一个Bean容器，（存放Bean类与Bean实例的映射关系）
     * */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();


    static {
        /*
        *  调用getBeanClassSet 方法
        *  获取bean类方法，也就是有Controller 或Service 等注解的类
        * */
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        classSet.forEach(s-> {
            try {
                BEAN_MAP.put(s,s.newInstance());
            } catch (Exception e) {
                log.error("创建类失败",e);
                e.printStackTrace();
            }
        });


    }

    /*
     *  获取Bean容器
     * */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /*
     *  获取Bean 实例
     * */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            log.error("没有获取到{0}实例",cls.getSimpleName());
            throw new RuntimeException("没有获取到实例"+cls.getSimpleName());
        }
        return (T)BEAN_MAP.get(cls);
    }

}
