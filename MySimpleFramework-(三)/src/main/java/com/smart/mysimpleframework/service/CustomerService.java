package com.smart.mysimpleframework.service;


import com.smart.mysimpleframework.Util.DatabaseUtil;
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

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);



    /*
     *  获取客户列表
     * */
    public List<Customer> getCustomerList() {
        String sql = "select * from customer";
        List<Customer> list = DatabaseUtil.queryEntityList(Customer.class, sql,  null);
        return list;


    }

    /*
    *  获取客户
    * */

    public Customer getCustomer(long id) {
        String sql = "select * from customer where id = ?";
        return DatabaseUtil.queryEntity(Customer.class, sql, id);

    }

    /*
    *  创建客户
    * */

    public  boolean createCustomer(Map<String,Object> fieldMap) {

        return DatabaseUtil.insertEntity(Customer.class,fieldMap);

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
        return DatabaseUtil.deleteEntity(Customer.class, id);
    }
}
