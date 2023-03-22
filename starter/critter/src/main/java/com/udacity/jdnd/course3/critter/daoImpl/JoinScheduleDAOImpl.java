package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.JoinScheduleDAO;
import com.udacity.jdnd.course3.critter.dto.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JoinScheduleDAOImpl implements JoinScheduleDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SCHEDULE_ID = "schedule_id";

    private static final String EMPLOYEE_ID = "employee_id";

    private static final String SKILL_NAME = "skill_name";

    private static final String PET_ID = "pet_id";

    private static final String SELECT_ALL_SCHEDULES =
        "SELECT * FROM join_schedule ";

    private static final String SELECT_ALL_SCHEDULES_BY_SCHEDULE_ID =
        "SELECT * FROM join_schedule WHERE " + SCHEDULE_ID + " = :" + SCHEDULE_ID;

    private static final String INSERT_SCHEDULE_AND_EMPLOYEE =
        "INSERT INTO join_schedule (" + SCHEDULE_ID + ", " + EMPLOYEE_ID + ") " +
            "VALUES (:" + SCHEDULE_ID + ", :" + EMPLOYEE_ID + ")";

    private static final String INSERT_SCHEDULE_AND_PET =
        "INSERT INTO join_schedule (" + SCHEDULE_ID + ", " + PET_ID + ") " +
            "VALUES (:" + SCHEDULE_ID + ", :" + PET_ID + ")";

    private static final String INSERT_SCHEDULE_AND_SKILL =
        "INSERT INTO join_schedule (" + SCHEDULE_ID + ", " + SKILL_NAME + ") " +
            "VALUES (:" + SCHEDULE_ID + ", :" + SKILL_NAME + ")";

    private static final RowMapper<JoinScheduleData> joinScheduleDataMapper =
        new BeanPropertyRowMapper<>(JoinScheduleData.class);

    @Override
    public void insertScheduleAndEmployee(Long scheduleId, Long employeeId) {
        jdbcTemplate.update (
            INSERT_SCHEDULE_AND_EMPLOYEE,
            new MapSqlParameterSource()
                .addValue(SCHEDULE_ID, scheduleId)
                .addValue(EMPLOYEE_ID, employeeId)
        );
    }

    @Override
    public void insertScheduleAndPet(Long scheduleId, Long petId) {
        jdbcTemplate.update (
            INSERT_SCHEDULE_AND_PET,
            new MapSqlParameterSource()
                .addValue(SCHEDULE_ID, scheduleId)
                .addValue(PET_ID, petId)
        );
    }

    @Override
    public void insertScheduleAndSkill(Long scheduleId, EmployeeSkill skill) {
        jdbcTemplate.update (
            INSERT_SCHEDULE_AND_SKILL,
            new MapSqlParameterSource()
                .addValue(SCHEDULE_ID, scheduleId)
                .addValue(SKILL_NAME, skill.name())
        );
    }

    @Override
    public List<JoinScheduleData> getScheduleByScheduleId(Long scheduleId) {
        return jdbcTemplate.query(
            SELECT_ALL_SCHEDULES_BY_SCHEDULE_ID,
                new MapSqlParameterSource().addValue("schedule_id", scheduleId),
                joinScheduleDataMapper
        );
    }
}
