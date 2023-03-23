package com.udacity.jdnd.course3.critter.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;

    private List<Long> employeeIds;

    private List<Long> petIds;

    private LocalDate date;

    private Set<EmployeeSkill> activities;


    /**
     * Get id long.
     *
     * @return the long
     */
    public long getId(){
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Gets employee ids.
     *
     * @return the employee ids
     */
    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    /**
     * Sets employee ids.
     *
     * @param employeeIds the employee ids
     */
    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    /**
     * Gets pet ids.
     *
     * @return the pet ids
     */
    public List<Long> getPetIds() {
        return petIds;
    }

    /**
     * Sets pet ids.
     *
     * @param petIds the pet ids
     */
    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets activities.
     *
     * @return the activities
     */
    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    /**
     * Sets activities.
     *
     * @param activities the activities
     */
    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
