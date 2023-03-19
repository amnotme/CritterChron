package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dao.CustomerDAO;
import com.udacity.jdnd.course3.critter.daoImpl.CustomerDAOImpl;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Autowired
    ObjectMapper objectMapper;

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {

        Long customerId =  customerDAO.saveCustomer(objectMapper.convertValue(customerDTO, CustomerData.class));
        return objectMapper.convertValue(customerDAO.getCustomer(customerId), CustomerDTO.class);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();

        for (CustomerData customerData : customerDAO.getAllCustomers()) {
            customers.add(objectMapper.convertValue(customerData, CustomerDTO.class));
        }

        return customers;
    }
}
