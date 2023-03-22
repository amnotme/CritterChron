package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.SkillDAO;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.SkillsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@Transactional
public class SkillDAOImpl implements SkillDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SKILL_NAME = "skill_name";

    private static final String SKILL_EMPLOYEE_ID = "employee_id";

    private static final String INSERT_SKILL =
        "INSERT INTO skill (skill_name, employee_id) " +
            "VALUES " +
            "(:" + SKILL_NAME +
            ", :" + SKILL_EMPLOYEE_ID +
            " )";

    private static final String SELECT_SKILLS_BY_EMPLOYEE_ID =
        "SELECT skill_name, employee_id FROM skill " +
            "WHERE employee_id = :" + SKILL_EMPLOYEE_ID;


    private static final String SELECT_SKILLS_BY_SKILL_ID =
        "SELECT * FROM skill " +
            "WHERE id IN (:ids)";

    private static final String SELECT_SKILLS_BY_SKILL_NAME =
        "SELECT skill_name, employee_id FROM skill " +
            "WHERE skill_name IN (:skills) ";

    private static final RowMapper<SkillsData> skillMapper =
        new BeanPropertyRowMapper<>(SkillsData.class);


    public Long saveSkill(SkillsData skillsData) {
        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(
            INSERT_SKILL,
            new MapSqlParameterSource()
                .addValue(SKILL_NAME, String.valueOf(skillsData.getSkillName()))
                .addValue(SKILL_EMPLOYEE_ID, skillsData.getEmployeeId()),
                key
        );
        return Objects.requireNonNull(key.getKey()).longValue();
    }


    @Override
    public Set<SkillsData> getSkillsByEmployeeId(Long employeeId) {
        return
            new HashSet<>(jdbcTemplate.query(
                SELECT_SKILLS_BY_EMPLOYEE_ID,
                new MapSqlParameterSource().addValue("employee_id", employeeId),
                skillMapper
            )
        );
    }

    @Override
    public Set<SkillsData> getSkillsByIdList(List<Long> skillIds) {
        return
            new HashSet<>(jdbcTemplate.query(
                SELECT_SKILLS_BY_SKILL_ID,
                new MapSqlParameterSource("ids", skillIds),
                skillMapper
            )
        );
    }

    @Override
    public Set<SkillsData> getEmployeesBySkillName(Set<EmployeeSkill> skills) {
        List<String> skillList = skills.stream().map(Enum::toString).collect(Collectors.toList());

        return
            new HashSet<>(jdbcTemplate.query(
                SELECT_SKILLS_BY_SKILL_NAME,
                new MapSqlParameterSource().addValue("skills", skillList),
                skillMapper
            )
        );
    }
}
