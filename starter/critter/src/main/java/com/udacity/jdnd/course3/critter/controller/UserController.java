package com.udacity.jdnd.course3.critter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The Pet service.
     */
    @Autowired
    PetService petService;

    /**
     * The Object mapper.
     */
    @Autowired
    ObjectMapper objectMapper;


    /**
     * Save customer customer dto.
     *
     * @param customerDTO the customer dto
     * @return the customer dto
     */
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return objectMapper.convertValue(userService.addCustomer(customerDTO), CustomerDTO.class);
    }

    /**
     * Get all customers list.
     *
     * @return the list
     */
    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customers = new ArrayList<>();

        for (CustomerData customerData : userService.getAllCustomers()) {
            List<PetData> pets = petService.getPetsByOwner(customerData.getId());
            CustomerDTO customer = objectMapper.convertValue(customerData, CustomerDTO.class);

            if (pets != null)
                customer.setPetIds(pets.stream().map(PetData::getId).collect(Collectors.toList()));
            customers.add(customer);
        }
        return customers;
    }

    /**
     * Get owner by pet customer dto.
     *
     * @param petId the pet id
     * @return the customer dto
     */
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        try {
            CustomerData customerData = userService.getOwnerByPet(petId);
            if (customerData == null) return null;
            CustomerDTO customer = objectMapper.convertValue(customerData, CustomerDTO.class);
            List<PetData> pets = petService.getPetsByOwner(customer.getId());
            customer
                .setPetIds(pets.stream()
                .map(PetData::getId)
                .collect(Collectors.toList()));

            return customer;
        } catch (Exception exception) {
            logger.error("There doesn't seem to be customer tied to pet " + petId);
            return null;
        }
    }

    /**
     * Save employee employee dto.
     *
     * @param employeeDTO the employee dto
     * @return the employee dto
     */
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long employeeId = userService.addEmployee(employeeDTO);
        List<Long> skillIds = new ArrayList<>();
        List<Long> daysIds = new ArrayList<>();

        EmployeeDTO employee = objectMapper.convertValue(userService.getEmployeeById(employeeId), EmployeeDTO.class);
        if (employeeDTO.getSkills() != null) {
            for (EmployeeSkill skill : employeeDTO.getSkills()) {
                SkillsData skillData = new SkillsData();
                skillData.setSkillName(skill);
                skillData.setEmployeeId(employeeId);
                skillIds.add(userService.addSkill(skillData));
            }
            Set<EmployeeSkill> employeeSkills = userService
                .getSkillsByIdList(skillIds)
                .stream()
                .map(SkillsData::getSkillName)
                .collect(Collectors.toSet());

            employee.setSkills(employeeSkills);
        }

        if (employeeDTO.getDaysAvailable() != null) {
            for (DayOfWeek day: employeeDTO.getDaysAvailable()) {
                DayOfWeekData dayOfWeekData = new DayOfWeekData();
                dayOfWeekData.setDayName(day);
                dayOfWeekData.setEmployeeId(employeeId);
                daysIds.add(userService.addDay(dayOfWeekData));
            }
            Set<DayOfWeek> daysOfWeeks = userService
                .getDaysByDaysIdList(daysIds)
                .stream()
                .map(DayOfWeekData::getDayName)
                .collect(Collectors.toSet());

            employee.setDaysAvailable(daysOfWeeks);
        }

        return employee;
    }

    /**
     * Gets employee.
     *
     * @param employeeId the employee id
     * @return the employee
     */
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO = objectMapper
            .convertValue(userService.getEmployeeById(employeeId), EmployeeDTO.class);

        Set<EmployeeSkill> employeeSkills = userService
            .getSkillsByEmployeeId(employeeId)
            .stream()
            .map(SkillsData::getSkillName)
            .collect(Collectors.toSet());

        Set<DayOfWeek> employeeDaysAvailable = userService
            .getDaysByEmployeeId(employeeId)
            .stream()
            .map(DayOfWeekData::getDayName)
            .collect(Collectors.toSet());

        employeeDTO.setSkills(employeeSkills);
        employeeDTO.setDaysAvailable(employeeDaysAvailable);

        return employeeDTO;
    }

    /**
     * Sets availability.
     *
     * @param daysAvailable the days available
     * @param employeeId    the employee id
     */
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    /**
     * Find employees for service list.
     *
     * @param employeeRequestDTO the employee request dto
     * @return the list
     */
    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {

        Set<EmployeeSkill> skills = new HashSet<>(employeeRequestDTO.getSkills());
        LocalDate date = employeeRequestDTO.getDate();

        Set<Long> employeeIds = userService.findEmployeesForService(skills, date);
        List<EmployeeDTO> employeeDTOS = employeeIds
            .stream()
            .map(id -> objectMapper.convertValue(userService.getEmployeeById(id), EmployeeDTO.class))
            .collect(Collectors.toList());

        for (EmployeeDTO employeeDTO: employeeDTOS) {
            Set<SkillsData> skillsDataSet = userService.getSkillsByEmployeeId(employeeDTO.getId());
            Set<DayOfWeekData> dayOfWeekDataSet = userService.getDaysByEmployeeId(employeeDTO.getId());
            employeeDTO.setSkills(skillsDataSet.stream().map(SkillsData::getSkillName).collect(Collectors.toSet()));
            employeeDTO.setDaysAvailable(dayOfWeekDataSet.stream().map(DayOfWeekData::getDayName).collect(Collectors.toSet()));
        }
        return employeeDTOS;
    }

}
