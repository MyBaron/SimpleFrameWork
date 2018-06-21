package com.smart.mysimpleframework.Config;


/*
*  定义配置文件的key
* */
public enum ConfigConstant {
    CONFIG_FILE("config.properties"),


    JDBC_DRIVER("jdbc.driver"),
    JDBC_URL("jdbc.url"),
    JDBC_USERNAME("jdbc.username"),
    JDBC_PASSWORD("jdbc.password"),

    APP_BASE_PACKAGE("base_package"),
    APP_JSP_PATH("jsp_path"),
    ASSET_PATH("asset_path"),
    ;


    private final String value;

    ConfigConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
