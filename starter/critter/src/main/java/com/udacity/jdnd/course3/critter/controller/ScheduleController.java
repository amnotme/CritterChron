package com.udacity.jdnd.course3.critter.controller;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;
import com.udacity.jdnd.course3.critter.entity.PetData;
import com.udacity.jdnd.course3.critter.entity.ScheduleData;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    /**
     * The Schedule service.
     */
    @Autowired
    ScheduleService scheduleService;

    /**
     * The Pet service.
     */
    @Autowired
    PetService petService;

    /**
     * Create schedule schedule dto.
     *
     * @param scheduleDTO the schedule dto
     * @return the schedule dto
     */
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

        List<JoinScheduleData> scheduleDataList = scheduleService.getAllScheduleDataByScheduleId(scheduleId);

        ScheduleDTO scheduleDTOResponse = new ScheduleDTO();
        List<Long> petIds = new ArrayList<>();
        Set<EmployeeSkill> skillList = new HashSet<>();
        List<Long> employeeList = new ArrayList<>();
        if (scheduleDataList != null) {

            for (JoinScheduleData data: scheduleDataList) {
                scheduleDTOResponse.setDate(date);
                scheduleDTOResponse.setId(data.getScheduleId());

                if (data.getPetId() != null)
                    petIds.add(data.getPetId());
                if (data.getEmployeeId() != null)
                    employeeList.add(data.getEmployeeId());
                if (data.getSkillName() != null)
                    skillList.add(data.getSkillName());
            }
            scheduleDTOResponse.setPetIds(petIds);
            scheduleDTOResponse.setActivities(skillList);
            scheduleDTOResponse.setEmployeeIds(employeeList);
        }
        return scheduleDTOResponse;
    }

    /**
     * Gets all schedules.
     *
     * @return the all schedules
     */
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleData> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (schedules.isEmpty()) return null;

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

                    if (data.getPetId() != null) petIds.add(data.getPetId());

                    if (data.getEmployeeId() != null) employeeList.add(data.getEmployeeId());

                    if (data.getSkillName() != null) skillList.add(data.getSkillName());
                }
                scheduleDTO.setPetIds(petIds);
                scheduleDTO.setActivities(skillList);
                scheduleDTO.setEmployeeIds(employeeList);
            }
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }

    /**
     * Gets schedule for pet.
     *
     * @param petId the pet id
     * @return the schedule for pet
     */
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<JoinScheduleData> joinScheduleData = scheduleService.getSchedulesByPetId(petId);

        if (joinScheduleData == null) return null;

        Set<Long> scheduleIds = joinScheduleData
            .stream()
            .filter(data -> Objects.equals(data.getPetId(), petId))
            .map(JoinScheduleData::getScheduleId)
            .collect(Collectors.toSet());

        List<ScheduleData> schedules = scheduleService.getSchedulesByScheduleIds(Lists.newArrayList(scheduleIds));

        return this.processScheduleData(schedules);
    }

    /**
     * Gets schedule for employee.
     *
     * @param employeeId the employee id
     * @return the schedule for employee
     */
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<JoinScheduleData> joinScheduleData = scheduleService.getSchedulesByEmployeeId(employeeId);

        if (joinScheduleData == null) return null;

        Set<Long> scheduleIds = joinScheduleData
            .stream()
            .filter(data -> data.getEmployeeId() == employeeId)
            .map(JoinScheduleData::getScheduleId)
            .collect(Collectors.toSet());

        List<ScheduleData> schedules = scheduleService.getSchedulesByScheduleIds(Lists.newArrayList(scheduleIds));

        return this.processScheduleData(schedules);
    }

    /**
     * Gets schedule for customer.
     *
     * @param customerId the customer id
     * @return the schedule for customer
     */
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Long> petIds = petService
            .getPetsByOwner(customerId)
            .stream()
            .map(PetData::getId)
            .collect(Collectors.toList());

        Set<ScheduleDTO> scheduleDTOSet = new HashSet<>();
        for (Long petId: petIds) {
            List<JoinScheduleData> joinScheduleData = scheduleService.getSchedulesByPetId(petId);

            if (joinScheduleData == null) return null;

            Set<Long> scheduleIds = joinScheduleData
                .stream()
                .filter(data -> Objects.equals(data.getPetId(), petId))
                .map(JoinScheduleData::getScheduleId)
                .collect(Collectors.toSet());

            List<ScheduleData> schedules = scheduleService.getSchedulesByScheduleIds(Lists.newArrayList(scheduleIds));

            scheduleDTOSet.addAll(this.processScheduleData(schedules));
        }
        return Lists.newArrayList(scheduleDTOSet);
    }

    /**
     * @param schedules
     * @return
     */
    private List<ScheduleDTO> processScheduleData(List<ScheduleData> schedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for (ScheduleData schedule: schedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            List<Long> petIds = new ArrayList<>();
            Set<EmployeeSkill> skillList = new HashSet<>();
            List<Long> employeeList = new ArrayList<>();

            for (JoinScheduleData data: scheduleService.getAllScheduleDataByScheduleId(schedule.getId())) {

                if (Objects.equals(data.getScheduleId(), schedule.getId())) {
                    scheduleDTO.setDate(schedule.getDate());
                    scheduleDTO.setId(data.getScheduleId());

                    if (data.getPetId() != null) petIds.add(data.getPetId());

                    if (data.getEmployeeId() != null) employeeList.add(data.getEmployeeId());

                    if (data.getSkillName() != null) skillList.add(data.getSkillName());
                }
            }
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setActivities(skillList);
            scheduleDTO.setEmployeeIds(employeeList);

            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }
}
