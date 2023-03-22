package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.EmployeeData;
import com.udacity.jdnd.course3.critter.entity.SkillsData;

import java.util.List;
import java.util.Set;

public interface SkillDAO {
    Long saveSkill(SkillsData skillsData);

    Set<SkillsData> getSkillsByEmployeeId(Long employeeId);

    Set<SkillsData> getSkillsByIdList(List<Long> skillIds);

    Set<SkillsData> getEmployeesBySkillName(Set<EmployeeSkill> skills);
}
