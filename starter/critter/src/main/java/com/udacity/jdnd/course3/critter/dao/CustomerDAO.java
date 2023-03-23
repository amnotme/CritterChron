package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.CustomerData;

import java.util.List;

/**
 * The interface Customer dao.
 */
public interface CustomerDAO {
    /**
     * Save customer long.
     *
     * @param customerData the customer data
     * @return the long
     */
    Long saveCustomer(CustomerData customerData);

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    List<CustomerData> getAllCustomers();

    /**
     * Gets customer.
     *
     * @param id the id
     * @return the customer
     */
    CustomerData getCustomer(Long id);
}
