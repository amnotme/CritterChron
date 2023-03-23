package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.DayOfWeekData;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * The interface Day of week dao.
 */
public interface DayOfWeekDAO {
    /**
     * Save day long.
     *
     * @param dayOfWeekData the day of week data
     * @return the long
     */
    Long saveDay(DayOfWeekData dayOfWeekData);

    /**
     * Gets days by employee id.
     *
     * @param employeeId the employee id
     * @return the days by employee id
     */
    Set<DayOfWeekData> getDaysByEmployeeId(Long employeeId);

    /**
     * Gets days by days id.
     *
     * @param daysIds the days ids
     * @return the days by days id
     */
    Set<DayOfWeekData> getDaysByDaysId(List<Long> daysIds);

    /**
     * Delete days of week by employee id.
     *
     * @param employeeId the employee id
     */
    void deleteDaysOfWeekByEmployeeId(Long employeeId);

    /**
     * Gets employees by day name.
     *
     * @param dayOfWeek the day of week
     * @return the employees by day name
     */
    Set<DayOfWeekData> getEmployeesByDayName(DayOfWeek dayOfWeek);
}
