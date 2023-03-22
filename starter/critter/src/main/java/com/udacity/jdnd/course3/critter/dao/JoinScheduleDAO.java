package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;

import java.util.List;

public interface JoinScheduleDAO {

    void insertScheduleAndEmployee(Long scheduleId, Long employeeId);

    void insertScheduleAndPet(Long scheduleId, Long petId);

    void insertScheduleAndSkill(Long scheduleId, EmployeeSkill skill);

    List<JoinScheduleData> getScheduleByScheduleId(Long scheduleId);
}
