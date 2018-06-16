package com.smart.mysimpleframework.service;

import com.smart.mysimpleframework.Helper.BeanHelper;
import com.smart.mysimpleframework.model.Customer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerServiceTest {

    private CustomerService customerService;


    @Before
    public void setUp() throws Exception {

        customerService = new CustomerService();
    }

    @Test
    public void getCustomerList() {

        List<Customer> customerList =
                customerService.getCustomerList();
        System.out.println(customerList);



    }

    @Test
    public void getCustomer() {
    }

    @Test
    public void createCustomer() {
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void deleteCustomer() {
    }

    @Test
    public void testInject() {
        try {
            Class.forName("com.smart.mysimpleframework.Helper.IOCHelper");
            //从容器里获取customerService 的实例查看
            CustomerService bean = BeanHelper.getBean(CustomerService.class);
            if (bean.customer!=null) {
                System.out.println("Inject is ok！！");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}