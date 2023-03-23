package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.DayOfWeekDAO;
import com.udacity.jdnd.course3.critter.entity.DayOfWeekData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The type Day of week dao.
 */
@Repository
@Transactional
public class DayOfWeekDAOImpl implements DayOfWeekDAO {
    /**
     * The Jdbc template.
     */
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    private static final String DAY_NAME = "day_name";

    private static final String DAY_EMPLOYEE_ID = "employee_id";

    private static final String INSERT_DAY =
        "INSERT INTO day_of_week (day_name, employee_id) " +
        "VALUES (:" + DAY_NAME + ", :" + DAY_EMPLOYEE_ID + " )";

    private static final String SELECT_DAYS_BY_EMPLOYEE_ID =
        "SELECT day_name, employee_id FROM day_of_week " +
            "WHERE employee_id = :" + DAY_EMPLOYEE_ID;

    private static final String SELECT_DAYS_BY_DAY_ID =
        "SELECT day_name, employee_id FROM day_of_week " +
            "WHERE id IN (:ids)";

    private static final String SELECT_EMPLOYEES_BY_DAY_NAME =
        "SELECT day_name, employee_id FROM day_of_week " +
            "WHERE day_name = :day ";

    private static final String DELETE_DAYS_BY_EMPLOYEE_ID =
        "DELETE FROM day_of_week " +
            "WHERE employee_id = :" + DAY_EMPLOYEE_ID;

    private static final RowMapper<DayOfWeekData> dayMapper =
        new BeanPropertyRowMapper<>(DayOfWeekData.class);

    @Override
    public Long saveDay(DayOfWeekData dayOfWeekData) {
        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(
            INSERT_DAY,
            new MapSqlParameterSource()
                .addValue(DAY_NAME, String.valueOf(dayOfWeekData.getDayName()))
                .addValue(DAY_EMPLOYEE_ID, dayOfWeekData.getEmployeeId()),
            key
        );
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public Set<DayOfWeekData> getDaysByEmployeeId(Long employeeId) {
        return
            new HashSet<>(jdbcTemplate.query(
                SELECT_DAYS_BY_EMPLOYEE_ID,
                new MapSqlParameterSource().addValue(DAY_EMPLOYEE_ID, employeeId),
                dayMapper
            )
        );
    }

    @Override
    public Set<DayOfWeekData> getDaysByDaysId(List<Long> daysIds) {
        return
            new HashSet<>(jdbcTemplate.query(
                SELECT_DAYS_BY_DAY_ID,
                new MapSqlParameterSource().addValue("ids", daysIds),
                dayMapper
            )
        );
    }

    @Override
    public Set<DayOfWeekData> getEmployeesByDayName(DayOfWeek dayOfWeek) {
        return
            new HashSet<>(jdbcTemplate.query(
                SELECT_EMPLOYEES_BY_DAY_NAME,
                new MapSqlParameterSource().addValue("day", String.valueOf(dayOfWeek)),
                dayMapper
            )
        );
    }

    @Override
    public void deleteDaysOfWeekByEmployeeId(Long employeeId) {
        jdbcTemplate.update(
            DELETE_DAYS_BY_EMPLOYEE_ID,
            new MapSqlParameterSource()
                .addValue(DAY_EMPLOYEE_ID, employeeId)
        );
    }
}
