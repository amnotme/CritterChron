package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dao.*;
import com.udacity.jdnd.course3.critter.daoImpl.*;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * The type User service.
 */
@Service
@Transactional
public class UserService {

    /**
     * The Customer dao.
     */
    @Autowired
    CustomerDAO customerDAO = new CustomerDAOImpl();

    /**
     * The Pet dao.
     */
    @Autowired
    PetDAO petDAO = new PetDAOImpl();

    /**
     * The Employee dao.
     */
    @Autowired
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    /**
     * The Skill dao.
     */
    @Autowired
    SkillDAO skillDAO = new SkillDAOImpl();

    /**
     * The Day of week dao.
     */
    @Autowired
    DayOfWeekDAO dayOfWeekDAO = new DayOfWeekDAOImpl();

    /**
     * The Object mapper.
     */
    @Autowired
    ObjectMapper objectMapper;

    /**
     * Add customer dto.
     *
     * @param customerDTO the customer dto
     * @return the customer dto
     */
    public CustomerData addCustomer(CustomerDTO customerDTO) {
        try {
            Long customerId =  customerDAO.saveCustomer(objectMapper.convertValue(customerDTO, CustomerData.class));
            return customerDAO.getCustomer(customerId);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Add employee employee dto.
     *
     * @param employeeDTO the employee dto
     * @return the employee dto
     */
    public Long addEmployee(EmployeeDTO employeeDTO) {
        try {
            return employeeDAO.saveEmployee(objectMapper.convertValue(employeeDTO, EmployeeData.class));
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    public List<CustomerData> getAllCustomers() {
        try {
            return customerDAO.getAllCustomers();
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets owner by pet.
     *
     * @param petId the pet id
     * @return the owner by pet
     */
    public CustomerData getOwnerByPet(Long petId) throws Exception {
        try {
            PetData pet = petDAO.getPetById(petId);
            if (pet != null)
                return customerDAO.getCustomer(pet.getOwnerId());
            return null;
        } catch (Exception exception) {
             throw new Exception("Not a valid pet" + exception.getMessage());
        }
    }


    /**
     * Sets employee availability.
     *
     * @param availableDays the available days
     * @param employeeId    the employee id
     */
    public void setEmployeeAvailability(Set<DayOfWeek> availableDays, Long employeeId) {

        EmployeeData employee = employeeDAO.getEmployeeById(employeeId);

        if (employee == null) return;
        if (availableDays == null) return;

        try {
            dayOfWeekDAO.deleteDaysOfWeekByEmployeeId(employeeId);

            for (DayOfWeek day: availableDays) {
                DayOfWeekData dayOfWeekData = new DayOfWeekData();
                dayOfWeekData.setDayName(day);
                dayOfWeekData.setEmployeeId(employeeId);
                dayOfWeekDAO.saveDay(dayOfWeekData);
            }
        } catch(Exception exception) {
            return ;
        }
    }

    /**
     * Find Employees For Service
     *
     * @param skills the skills
     * @param date   the date
     * @return set
     */
    public Set<Long> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        try {
            Set<Long> availableEmployeeIds = new HashSet<>();
            Set<SkillsData> skillSet = new HashSet<>();
            Set<DayOfWeekData> dayOfWeekDataSet = new HashSet<>();
            int skillCountPerRequest = 0;

            if (date != null)
                dayOfWeekDataSet = dayOfWeekDAO.getEmployeesByDayName(date.getDayOfWeek());

            if (skills != null) {
                skillCountPerRequest = skills.size();
                skillSet = skillDAO.getEmployeesBySkillName(skills);
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
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets employee by id.
     *
     * @param employeeId the employee id
     * @return the employee by id
     */
    public EmployeeData getEmployeeById(Long employeeId) {
        try {
            return employeeDAO.getEmployeeById(employeeId);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets skills by employee id.
     *
     * @param employeeId the employee id
     * @return skills by employee id
     */
    public Set<SkillsData> getSkillsByEmployeeId(Long employeeId) {
        try {
            return skillDAO.getSkillsByEmployeeId(employeeId);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Add skill long.
     *
     * @param skillsData the skills data
     * @return the long
     */
    public Long addSkill(SkillsData skillsData) {
        try {
            return skillDAO.saveSkill(skillsData);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets skills by id list.
     *
     * @param skillIds the skill ids
     * @return the skills by id list
     */
    public Set<SkillsData> getSkillsByIdList(List<Long> skillIds) {
        try {
            return skillDAO.getSkillsByIdList(skillIds);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets days by employee id.
     *
     * @param employeeId the employee id
     * @return days by employee id
     */
    public Set<DayOfWeekData> getDaysByEmployeeId(Long employeeId) {
        try {
            return dayOfWeekDAO.getDaysByEmployeeId(employeeId);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Add day long.
     *
     * @param dayOfWeekData the day of week data
     * @return the long
     */
    public Long addDay(DayOfWeekData dayOfWeekData) {
        try {
            return dayOfWeekDAO.saveDay(dayOfWeekData);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets days by days id list.
     *
     * @param daysIds the days ids
     * @return the days by days id list
     */
    public Set<DayOfWeekData> getDaysByDaysIdList(List<Long> daysIds) {
        try {
            return dayOfWeekDAO.getDaysByDaysId(daysIds);
        } catch (Exception exception) {
            return null;
        }
    }
}
