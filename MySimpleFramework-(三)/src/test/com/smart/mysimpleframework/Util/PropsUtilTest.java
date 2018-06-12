package com.smart.mysimpleframework.Util;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class PropsUtilTest {

    @Test
    public void loadProps() {
        Properties properties = PropsUtil.loadProps("config.properties");
        if (properties != null) {
            String property = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url", "null");
            String username = properties.getProperty("jdbc.username", "null");
            System.out.println("property: "+property);
            System.out.println("usl:" + url);
            System.out.println("username: "+ username);
        }


    }
}