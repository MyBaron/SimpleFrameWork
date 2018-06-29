package com.smart.mysimpleframework.controller;


import com.smart.mysimpleframework.annotation.Action;
import com.smart.mysimpleframework.annotation.Controller;
import com.smart.mysimpleframework.annotation.Inject;
import com.smart.mysimpleframework.bean.Data;
import com.smart.mysimpleframework.bean.Param;
import com.smart.mysimpleframework.bean.View;
import com.smart.mysimpleframework.model.Customer;
import com.smart.mysimpleframework.service.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    /*
     *  进入客户列表界面
     * */
    @Action("get:/customer")
    public View index(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }

    /*
     *  显示客户基本信息
     * */
    @Action("get:/customer_show")
    public View show(Param param) {
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_show.jsp").addModel("customer", customer);
    }


    /*
     *  进入创建客户界面
     * */
    @Action("get:/customer_create")
    public View create(Param param) {
        return new View("customer_create.jsp");
    }

    /*
     *  处理创建客户请求
     * */
    @Action("post:/customer_create")
    public Data createSubmit(Param param) {
        Map<String, Object> map = param.getMap();
        boolean result = customerService.createCustomer(map);
        return new Data(result);
    }

    /*
     *  进入编辑客户 界面
     * */
    @Action("get:/customer_edit")
    public View edit(Param param) {
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_edit.jsp").addModel("customer", customer);
    }

    /*
    *  处理编辑客户请求
    * */
    //TODO


    /*
    *  处理删除客户请求
    * */
    //TODO
}
