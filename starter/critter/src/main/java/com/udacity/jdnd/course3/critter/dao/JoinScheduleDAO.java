package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;

import java.util.List;

/**
 * The interface Join schedule dao.
 */
public interface JoinScheduleDAO {

    /**
     * Insert schedule and employee.
     *
     * @param scheduleId the schedule id
     * @param employeeId the employee id
     */
    void insertScheduleAndEmployee(Long scheduleId, Long employeeId);

    /**
     * Insert schedule and pet.
     *
     * @param scheduleId the schedule id
     * @param petId      the pet id
     */
    void insertScheduleAndPet(Long scheduleId, Long petId);

    /**
     * Insert schedule and skill.
     *
     * @param scheduleId the schedule id
     * @param skill      the skill
     */
    void insertScheduleAndSkill(Long scheduleId, EmployeeSkill skill);

    /**
     * Gets schedule by schedule id.
     *
     * @param scheduleId the schedule id
     * @return the schedule by schedule id
     */
    List<JoinScheduleData> getScheduleByScheduleId(Long scheduleId);

    /**
     * Gets schedules by employee id.
     *
     * @param employeeId the employee id
     * @return the schedules by employee id
     */
    List<JoinScheduleData> getSchedulesByEmployeeId(Long employeeId);

    /**
     * Gets schedules by pet id.
     *
     * @param petId the pet id
     * @return the schedules by pet id
     */
    List<JoinScheduleData> getSchedulesByPetId(Long petId);

}
