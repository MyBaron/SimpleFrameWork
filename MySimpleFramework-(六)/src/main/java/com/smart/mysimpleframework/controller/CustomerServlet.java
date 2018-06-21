package com.smart.mysimpleframework.controller;


import com.smart.mysimpleframework.annotation.Action;
import com.smart.mysimpleframework.annotation.Controller;

@Controller
public class CustomerServlet {


//    获取所有用户信息
    @Action("get:/customer_show/getList")
    public String getList() {
        //TODO
        return null;
    }


//    根据id获取用户信息
    @Action("get:/customer_show/getCustomer")
    public String getList(Integer id) {
        //TODO
        return null;
    }


}
