package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dao.JoinScheduleDAO;
import com.udacity.jdnd.course3.critter.dao.ScheduleDAO;
import com.udacity.jdnd.course3.critter.daoImpl.JoinScheduleDAOImpl;
import com.udacity.jdnd.course3.critter.daoImpl.ScheduleDAOImpl;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;
import com.udacity.jdnd.course3.critter.entity.ScheduleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * The type Schedule service.
 */
@Service
public class ScheduleService {

    /**
     * The Schedule dao.
     */
    @Autowired
    ScheduleDAO scheduleDAO = new ScheduleDAOImpl();

    /**
     * The Join schedule dao.
     */
    @Autowired
    JoinScheduleDAO joinScheduleDAO = new JoinScheduleDAOImpl();

    /**
     * Add schedule record long.
     *
     * @param date the date
     * @return the long
     */
    public Long addScheduleRecord(LocalDate date) {
        try {
            return scheduleDAO.createScheduleRecord(date);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets all schedules.
     *
     * @return the all schedules
     */
    public List<ScheduleData> getAllSchedules() {
        try {
            return scheduleDAO.getAllSchedules();
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets schedules by schedule ids.
     *
     * @param scheduleIds the schedule ids
     * @return the schedules by schedule ids
     */
    public List<ScheduleData> getSchedulesByScheduleIds(List<Long> scheduleIds) {
        try {
            return scheduleDAO.getSchedulesByScheduleIds(scheduleIds);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Insert schedule and employees.
     *
     * @param scheduleId the schedule id
     * @param employeIds the employe ids
     */
    public void insertScheduleAndEmployees(Long scheduleId, List<Long> employeIds) {
        try {
            employeIds.forEach(id -> joinScheduleDAO.insertScheduleAndEmployee(scheduleId, id));
        } catch (Exception exception) {
            return ;
        }
    }

    /**
     * Insert schedule and pets.
     *
     * @param scheduleId the schedule id
     * @param petIds     the pet ids
     */
    public void insertScheduleAndPets(Long scheduleId, List<Long> petIds) {
        try {
            petIds.forEach(id -> joinScheduleDAO.insertScheduleAndPet(scheduleId, id));
        } catch (Exception exception) {
            return ;
        }
    }

    /**
     * Insert schedule and skills.
     *
     * @param scheduleId the schedule id
     * @param skills     the skills
     */
    public void insertScheduleAndSkills(Long scheduleId, Set<EmployeeSkill> skills) {
        try {
            skills.forEach(skill -> joinScheduleDAO.insertScheduleAndSkill(scheduleId, skill));
        } catch (Exception exception) {
            return ;
        }
    }

    /**
     * Gets all schedule data by schedule id.
     *
     * @param scheduleId the schedule id
     * @return the all schedule data by schedule id
     */
    public List<JoinScheduleData> getAllScheduleDataByScheduleId(Long scheduleId) {
        try {
            return joinScheduleDAO.getScheduleByScheduleId(scheduleId);
        } catch (Exception exception) {
            return null;
        }
    }


    /**
     * Gets schedules by employee id.
     *
     * @param employeeId the employee id
     * @return the schedules by employee id
     */
    public List<JoinScheduleData> getSchedulesByEmployeeId(Long employeeId) {
        try {
            return joinScheduleDAO.getSchedulesByEmployeeId(employeeId);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets schedules by pet id.
     *
     * @param petId the pet id
     * @return the schedules by pet id
     */
    public List<JoinScheduleData> getSchedulesByPetId(Long petId) {
        try {
            return joinScheduleDAO.getSchedulesByPetId(petId);
        } catch (Exception exception) {
            return null;
        }
    }
}
