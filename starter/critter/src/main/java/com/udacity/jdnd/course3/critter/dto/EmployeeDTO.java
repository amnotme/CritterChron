package com.udacity.jdnd.course3.critter.dto;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private long id;

    private String name;

    private Set<EmployeeSkill> skills;

    private Set<DayOfWeek> daysAvailable;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets skills.
     *
     * @return the skills
     */
    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    /**
     * Sets skills.
     *
     * @param skills the skills
     */
    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    /**
     * Gets days available.
     *
     * @return the days available
     */
    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    /**
     * Sets days available.
     *
     * @param daysAvailable the days available
     */
    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
