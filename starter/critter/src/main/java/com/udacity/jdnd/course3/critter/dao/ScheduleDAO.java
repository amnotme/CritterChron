package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.ScheduleData;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Schedule dao.
 */
public interface ScheduleDAO {

    /**
     * Create schedule record long.
     *
     * @param date the date
     * @return the long
     */
    Long createScheduleRecord(LocalDate date);

    /**
     * Gets all schedules.
     *
     * @return the all schedules
     */
    List<ScheduleData> getAllSchedules();

    /**
     * Gets schedules by schedule ids.
     *
     * @param scheduleIds the schedule ids
     * @return the schedules by schedule ids
     */
    List<ScheduleData> getSchedulesByScheduleIds(List<Long> scheduleIds);
}
