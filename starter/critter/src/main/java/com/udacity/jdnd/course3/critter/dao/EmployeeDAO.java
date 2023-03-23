package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.EmployeeData;

import java.util.List;


/**
 * The interface Employee dao.
 */
public interface EmployeeDAO {
    /**
     * Save employee long.
     *
     * @param employeeData the employee data
     * @return the long
     */
    Long saveEmployee(EmployeeData employeeData);

    /**
     * Gets all employees.
     *
     * @return the all employees
     */
    List<EmployeeData> getAllEmployees();

    /**
     * Gets employee by id.
     *
     * @param employeeId the employee id
     * @return the employee by id
     */
    EmployeeData getEmployeeById(Long employeeId);
}
