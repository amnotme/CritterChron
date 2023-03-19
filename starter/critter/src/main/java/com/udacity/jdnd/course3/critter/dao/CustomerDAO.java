package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.CustomerData;

import java.util.List;

public interface CustomerDAO {
    Long saveCustomer(CustomerData customerData);

    List<CustomerData> getAllCustomers();

    CustomerData getCustomer(Long id);
}
