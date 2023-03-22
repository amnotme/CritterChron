package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.ScheduleData;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDAO {

    Long createScheduleRecord(LocalDate date);

    List<ScheduleData> getAllSchedules();
}
