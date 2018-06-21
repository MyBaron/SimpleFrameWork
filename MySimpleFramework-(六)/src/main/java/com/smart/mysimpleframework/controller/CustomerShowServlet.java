package com.smart.mysimpleframework.controller;


import com.smart.mysimpleframework.Helper.IOCHelper;
import com.smart.mysimpleframework.annotation.Controller;
import com.smart.mysimpleframework.annotation.Inject;
import com.smart.mysimpleframework.model.Customer;
import com.smart.mysimpleframework.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
*  获取客户端的数据
* */
@WebServlet("/customer_show")
@Controller
public class CustomerShowServlet  extends HttpServlet{

    @Inject
    private static CustomerService customerService;

//    static {
//        customerService = new CustomerService();
//    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String requestURI = req.getRequestURI();
        System.out.println("url:  "+requestURI);
        if (method != null) {
            if ("getList".equals(method)) {
                List<Customer> customerList =
                        customerService.getCustomerList();
                PrintWriter writer = resp.getWriter();
                System.out.println(customerList);
                writer.write(customerList.toString());
                writer.flush();
                writer.close();


            }
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
