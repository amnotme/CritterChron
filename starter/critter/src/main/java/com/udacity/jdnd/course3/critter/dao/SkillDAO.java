package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.EmployeeData;
import com.udacity.jdnd.course3.critter.entity.SkillsData;

import java.util.List;
import java.util.Set;

/**
 * The interface Skill dao.
 */
public interface SkillDAO {
    /**
     * Save skill long.
     *
     * @param skillsData the skills data
     * @return the long
     */
    Long saveSkill(SkillsData skillsData);

    /**
     * Gets skills by employee id.
     *
     * @param employeeId the employee id
     * @return the skills by employee id
     */
    Set<SkillsData> getSkillsByEmployeeId(Long employeeId);

    /**
     * Gets skills by id list.
     *
     * @param skillIds the skill ids
     * @return the skills by id list
     */
    Set<SkillsData> getSkillsByIdList(List<Long> skillIds);

    /**
     * Gets employees by skill name.
     *
     * @param skills the skills
     * @return the employees by skill name
     */
    Set<SkillsData> getEmployeesBySkillName(Set<EmployeeSkill> skills);
}
