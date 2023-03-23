package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * The type Skills data.
 */
@Data
@Table(name = "skill")
public class SkillsData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeSkill skillName;

    private Long employeeId;

}
