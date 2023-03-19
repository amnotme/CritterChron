package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dao.CustomerDAO;
import com.udacity.jdnd.course3.critter.dao.PetDAO;
import com.udacity.jdnd.course3.critter.daoImpl.CustomerDAOImpl;
import com.udacity.jdnd.course3.critter.daoImpl.PetDAOImpl;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.CustomerData;
import com.udacity.jdnd.course3.critter.entity.PetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Autowired
    PetDAO petDAO = new PetDAOImpl();

    @Autowired
    ObjectMapper objectMapper;

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {

        Long customerId =  customerDAO.saveCustomer(objectMapper.convertValue(customerDTO, CustomerData.class));
        return objectMapper.convertValue(customerDAO.getCustomer(customerId), CustomerDTO.class);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        List<PetData> pets;
        List<Long> petIds = new ArrayList<>();

        for (CustomerData customerData : customerDAO.getAllCustomers()) {
            pets = petDAO.getPetsByOwnerID(customerData.getId());
            CustomerDTO customer = objectMapper.convertValue(customerData, CustomerDTO.class);
            if (pets.size() > 0) {
                for (PetData petData: pets)
                    petIds.add(petData.getId());
                customer.setPetIds(petIds);
            }
            customers.add(customer);
        }
        return customers;
    }

    public CustomerDTO getOwnerByPet(Long petId) {
        List<Long> petIds = new ArrayList<>();
        CustomerDTO customer = objectMapper.convertValue(customerDAO.getCustomer(petId), CustomerDTO.class);

        for (PetData petData : petDAO.getPetsByOwnerID(customer.getId()))
            petIds.add(petData.getId());
        customer.setPetIds(petIds);
        return customer;
    }
}
