package com.smart.mysimpleframework.Helper;


import com.smart.mysimpleframework.Config.ConfigConstant;
import com.smart.mysimpleframework.Util.PropsUtil;

import java.util.Properties;

/*
*  配置加载类
*  加载属性文件
* */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS =
            PropsUtil.loadProps(ConfigConstant.CONFIG_FILE.getValue());

    /*
    *  获取JDBC 驱动
    * */

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER.getValue());
    }

    /*
    *  获取JDBC URL
    * */

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL.getValue());
    }

    /*
    *  获取 JDBC 用户名
    * */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME.getValue());
    }

    /*
    *
    *  获取 JDBC 密码
    * */

    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD.getValue());
    }


    /*
    *
    *  获取基础包
    * */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE.getValue());
    }


    /*
    *  获取应用JSP路径
    * */

    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH.getValue(), "/WEB-INF/view/");
    }

    /*
     *  获取静态资源路径
     * */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.ASSET_PATH.getValue(), "/asset/");
    }
}
