package com.smart.mysimpleframework.Util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropsUtil {
    private static final Logger log = LoggerFactory.getLogger(PropsUtil.class);


    /*
    *  加载属性文件
    * */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;

        //获取fileName的InputStream

        try {
            is = PropsUtil.class.getClassLoader().getResourceAsStream(fileName);
            props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            log.error("加载Properties错误",e);
            e.printStackTrace();

        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("流关闭失败",e);
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    /*
    *  获取属性值
    * */

    public static String getString(Properties properties,String key) {

        String property = properties.getProperty(key);
        if (!StringUtils.isBlank(property)) {
            return property.trim();
        }

        return null;

    }

    /*
    *   获取属性值，若为空则返回默认值
    * */

    public static String getString(Properties properties,String key, String defaultValue) {

        String property = properties.getProperty(key);
        if (!StringUtils.isBlank(property)) {
            return property.trim();
        }
        return defaultValue;
    }


}
