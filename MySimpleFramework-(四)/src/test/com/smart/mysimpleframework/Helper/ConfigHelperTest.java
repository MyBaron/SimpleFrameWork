package com.smart.mysimpleframework.Helper;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigHelperTest {

    @Test
    public void getJdbcDriver() {
        String jdbcDriver = ConfigHelper.getJdbcDriver();
        System.out.println(jdbcDriver);
    }

    @Test
    public void getJdbcUrl() {
        String jdbcUrl = ConfigHelper.getJdbcUrl();
        System.out.println(jdbcUrl);
    }

    @Test
    public void getJdbcUsername() {
        System.out.println(ConfigHelper.getJdbcUsername());

    }

    @Test
    public void getJdbcPassword() {
        System.out.println(ConfigHelper.getJdbcPassword());
    }

    @Test
    public void getAppBasePackage() {
        System.out.println(ConfigHelper.getAppBasePackage());
    }

    @Test
    public void getAppJspPath() {
        System.out.println(ConfigHelper.getAppJspPath());
    }

    @Test
    public void getAppAssetPath() {
        System.out.println(ConfigHelper.getAppAssetPath());
    }
}