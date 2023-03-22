package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;
import com.udacity.jdnd.course3.critter.entity.ScheduleData;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        if (scheduleDTO.getDate().getDayOfWeek() == null)
            return null;

        LocalDate date = scheduleDTO.getDate();
        Long scheduleId = scheduleService.addScheduleRecord(date);

        if (scheduleDTO.getEmployeeIds() != null)
            scheduleService.insertScheduleAndEmployees(scheduleId, scheduleDTO.getEmployeeIds());

        if (scheduleDTO.getPetIds() != null)
            scheduleService.insertScheduleAndPets(scheduleId, scheduleDTO.getPetIds());

        if (scheduleDTO.getActivities() != null)
            scheduleService.insertScheduleAndSkills(scheduleId, scheduleDTO.getActivities());

        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleData> schedules = scheduleService.getAllSchedules();

        if (schedules.isEmpty())
            return null;

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for (ScheduleData schedule: schedules) {
            List<JoinScheduleData> scheduleDataList = scheduleService.getAllScheduleDataByScheduleId(schedule.getId());

            ScheduleDTO scheduleDTO = new ScheduleDTO();
            List<Long> petIds = new ArrayList<>();
            Set<EmployeeSkill> skillList = new HashSet<>();
            List<Long> employeeList = new ArrayList<>();
            if (scheduleDataList != null) {

                for (JoinScheduleData data: scheduleDataList) {
                    scheduleDTO.setDate(schedule.getDate());
                    scheduleDTO.setId(data.getScheduleId());

                    if (data.getPetId() != null)
                        petIds.add(data.getPetId());
                    if (data.getEmployeeId() != null)
                        employeeList.add(data.getEmployeeId());
                    if (data.getSkillName() != null)
                        skillList.add(data.getSkillName());
                }
                scheduleDTO.setPetIds(petIds);
                scheduleDTO.setActivities(skillList);
                scheduleDTO.setEmployeeIds(employeeList);
            }
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }
}
