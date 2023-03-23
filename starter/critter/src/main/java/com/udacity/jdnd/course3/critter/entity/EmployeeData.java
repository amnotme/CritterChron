package com.udacity.jdnd.course3.critter.entity;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

/**
 * The type Employee data.
 */
@Data
@Table(name = "employee")
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nationalized
    private String name;
}
