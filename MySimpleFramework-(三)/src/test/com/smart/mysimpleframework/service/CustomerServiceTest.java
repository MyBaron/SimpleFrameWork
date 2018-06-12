package com.smart.mysimpleframework.service;

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
}