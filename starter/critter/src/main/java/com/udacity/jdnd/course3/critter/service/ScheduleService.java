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

@Service
public class ScheduleService {

    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    @Autowired
    ScheduleDAO scheduleDAO = new ScheduleDAOImpl();

    @Autowired
    JoinScheduleDAO joinScheduleDAO = new JoinScheduleDAOImpl();

    public Long addScheduleRecord(LocalDate date) {
        return scheduleDAO.createScheduleRecord(date);
    }


    public void insertScheduleAndEmployees(Long scheduleId, List<Long> employeIds) {
        for (Long id: employeIds)
            joinScheduleDAO.insertScheduleAndEmployee(scheduleId, id);
    }

    public void insertScheduleAndPets(Long scheduleId, List<Long> petIds) {
        for (Long id: petIds)
            joinScheduleDAO.insertScheduleAndPet(scheduleId, id);
    }

    public void insertScheduleAndSkills(Long scheduleId, Set<EmployeeSkill> skills) {
        for (EmployeeSkill skill: skills)
            joinScheduleDAO.insertScheduleAndSkill(scheduleId, skill);

    }

    public List<ScheduleData> getAllSchedules() {
        return scheduleDAO.getAllSchedules();
    }

    public List<JoinScheduleData> getAllScheduleDataByScheduleId(Long scheduleId) {
        return joinScheduleDAO.getScheduleByScheduleId(scheduleId);
    }
}
