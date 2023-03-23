package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import lombok.Data;

import org.springframework.data.annotation.Id;

import javax.persistence.Table;

/**
 * The type Join schedule data.
 */
@Data
@Table(name = "join_schedule")
public class JoinScheduleData {

    @Id
    private Long scheduleId;

    @Id
    private Long employeeId;

    @Id
    private EmployeeSkill skillName;

    @Id
    private Long petId;
}
