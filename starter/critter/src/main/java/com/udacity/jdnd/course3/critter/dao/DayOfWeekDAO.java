package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.DayOfWeekData;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface DayOfWeekDAO {
    Long saveDay(DayOfWeekData dayOfWeekData);

    Set<DayOfWeekData> getDaysByEmployeeId(Long employeeId);

    Set<DayOfWeekData> getDaysByDaysId(List<Long> daysIds);

    void deleteDaysOfWeekByEmployeeId(Long employeeId);

    Set<DayOfWeekData> getEmployeesByDayName(DayOfWeek dayOfWeek);
}
