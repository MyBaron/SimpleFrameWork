package com.smart.mysimpleframework.service;


import com.smart.mysimpleframework.Util.PropsUtil;
import com.smart.mysimpleframework.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/*
*  提供客户数据服务
* */
public class CustomerService {

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    static{
        System.out.println("配置加载");
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");

        //JDBC流程第一步 加载驱动
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("加载jdbc驱动失败",e);
            e.printStackTrace();
        }
    }



    /*
     *  获取客户列表
     * */
    public List<Customer> getCustomerList() {
        Connection conn = null;

        try {
            List<Customer> list = new ArrayList<>();
            String sql = "select * from customer";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setTelephone(resultSet.getString("telephone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                list.add(customer);

            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    /*
    *  获取客户
    * */

    public Customer getCustomer(long id) {
        //TODO
        return null;
    }

    /*
    *  创建客户
    * */

    public  boolean createCustomer(Map<String,Object> fieldMap) {
        //TODO
        return false;

    }

    /*
    *  更新客户
    * */
    public boolean updateCustomer(long id,Map<String,Object> fieldMap) {
        //TODO
        return false;
    }

    /*
     *  删除客户
     * */
    public boolean deleteCustomer(long id) {
        //TODO
        return false;
    }
}
