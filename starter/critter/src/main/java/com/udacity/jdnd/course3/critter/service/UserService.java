package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dao.*;
import com.udacity.jdnd.course3.critter.daoImpl.*;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class UserService {

    @Autowired
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Autowired
    PetDAO petDAO = new PetDAOImpl();

    @Autowired
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Autowired
    SkillDAO skillDAO = new SkillDAOImpl();

    @Autowired
    DayOfWeekDAO dayOfWeekDAO = new DayOfWeekDAOImpl();

    @Autowired
    ObjectMapper objectMapper;

    public CustomerDTO addCustomer(CustomerDTO customerDTO) {

        Long customerId =  customerDAO.saveCustomer(objectMapper.convertValue(customerDTO, CustomerData.class));
        return objectMapper.convertValue(customerDAO.getCustomer(customerId), CustomerDTO.class);
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Long employeeId = employeeDAO.saveEmployee(objectMapper.convertValue(employeeDTO, EmployeeData.class));
        List<Long> skillIds = new ArrayList<>();
        List<Long> daysIds = new ArrayList<>();

        EmployeeDTO employee = objectMapper.convertValue(employeeDAO.getEmployeeById(employeeId), EmployeeDTO.class);
        if (employeeDTO.getSkills() != null) {
            for (EmployeeSkill skill : employeeDTO.getSkills()) {
                SkillsData skillData = new SkillsData();
                skillData.setSkillName(skill);
                skillData.setEmployeeId(employeeId);
                skillIds.add(skillDAO.saveSkill(skillData));
            }
            Set<EmployeeSkill> employeeSkills = new HashSet<>();
            for (SkillsData skill: skillDAO.getSkillsByIdList(skillIds))
                employeeSkills.add(skill.getSkillName());

            employee.setSkills(employeeSkills);
        }

        if (employeeDTO.getDaysAvailable() != null) {
            for (DayOfWeek day: employeeDTO.getDaysAvailable()) {
                DayOfWeekData dayOfWeekData = new DayOfWeekData();
                dayOfWeekData.setDayName(day);
                dayOfWeekData.setEmployeeId(employeeId);
                daysIds.add(dayOfWeekDAO.saveDay(dayOfWeekData));
            }
            Set<DayOfWeek> daysOfWeeks = new HashSet<>();
            for (DayOfWeekData dayOfWeekData: dayOfWeekDAO.getDaysByDaysId(daysIds))
                daysOfWeeks.add(dayOfWeekData.getDayName());

            employee.setDaysAvailable(daysOfWeeks);
        }
        return employee;
    }

    public EmployeeDTO getEmployeeById(Long employeeId) {
        Set<EmployeeSkill> employeeSkills = new HashSet<>();
        Set<DayOfWeek> employeeDaysAvailable = new HashSet<>();

        EmployeeDTO employeeDTO = objectMapper.convertValue(employeeDAO.getEmployeeById(employeeId), EmployeeDTO.class);
        for (SkillsData skillsData: skillDAO.getSkillsByEmployeeId(employeeId))
            employeeSkills.add(skillsData.getSkillName());

        for (DayOfWeekData dayOfWeekData: dayOfWeekDAO.getDaysByEmployeeId(employeeId))
            employeeDaysAvailable.add(dayOfWeekData.getDayName());

        employeeDTO.setSkills(employeeSkills);
        employeeDTO.setDaysAvailable(employeeDaysAvailable);
        return employeeDTO;
    }

    public void setEmployeeAvailability(Set<DayOfWeek> availableDays, Long employeeId) {

        EmployeeDTO employee = objectMapper.convertValue(employeeDAO.getEmployeeById(employeeId), EmployeeDTO.class);

        if (employee == null) return;
        if (availableDays == null) return;

        dayOfWeekDAO.deleteDaysOfWeekByEmployeeId(employeeId);

        for (DayOfWeek day: availableDays) {
            DayOfWeekData dayOfWeekData = new DayOfWeekData();
            dayOfWeekData.setDayName(day);
            dayOfWeekData.setEmployeeId(employeeId);
            dayOfWeekDAO.saveDay(dayOfWeekData);
        }
    }
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        for (CustomerData customerData : customerDAO.getAllCustomers()) {
            List<PetData> pets = petDAO.getPetsByOwnerID(customerData.getId());
            CustomerDTO customer = objectMapper.convertValue(customerData, CustomerDTO.class);
            if (pets != null) {
                for (PetData petData: pets)
                    petIds.add(petData.getId());
                customer.setPetIds(petIds);
            }
            customers.add(customer);
        }
        return customers;
    }

    public Set<Long> findEmployeesForService(EmployeeRequestDTO requestDTO) {
        Set<Long> availableEmployeeIds = new HashSet<>();
        Set<SkillsData> skillSet = new HashSet<>();
        Set<DayOfWeekData> dayOfWeekDataSet = new HashSet<>();
        int skillCountPerRequest = 0;

        if (requestDTO.getDate() != null)
            dayOfWeekDataSet = dayOfWeekDAO.getEmployeesByDayName(requestDTO.getDate().getDayOfWeek());

        if (requestDTO.getSkills() != null) {
            skillCountPerRequest = requestDTO.getSkills().size();
            skillSet = skillDAO.getEmployeesBySkillName(requestDTO.getSkills());
        }

        for (DayOfWeekData dayOfWeekData: dayOfWeekDataSet) {
            int skillCountPerEmployee = 0;
            for (SkillsData skillsData : skillSet) {
                if (Objects.equals(dayOfWeekData.getEmployeeId(), skillsData.getEmployeeId())) {
                    skillCountPerEmployee++;
                    if (skillCountPerEmployee == skillCountPerRequest)
                        availableEmployeeIds.add(dayOfWeekData.getEmployeeId());
                }
            }
        }
        return availableEmployeeIds;
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
