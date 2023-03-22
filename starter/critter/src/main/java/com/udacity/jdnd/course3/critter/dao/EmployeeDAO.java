package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.EmployeeData;

import java.util.List;


public interface EmployeeDAO {
    Long saveEmployee(EmployeeData employeeData);

    List<EmployeeData> getAllEmployees();

    EmployeeData getEmployeeById(Long employeeId);
}
