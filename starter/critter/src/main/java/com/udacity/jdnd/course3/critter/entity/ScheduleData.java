package com.udacity.jdnd.course3.critter.entity;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;


@Data
@Table(name = "schedule")
public class ScheduleData {

    @Id
    private Long id;

    private LocalDate date;
}
