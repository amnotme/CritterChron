package com.udacity.jdnd.course3.critter.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;

@Data
@Table(name = "day_of_week")
public class DayOfWeekData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayName;

    private Long employeeId;
}
