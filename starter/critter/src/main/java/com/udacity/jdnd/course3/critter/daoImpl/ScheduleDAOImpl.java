package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.ScheduleDAO;
import com.udacity.jdnd.course3.critter.entity.JoinScheduleData;
import com.udacity.jdnd.course3.critter.entity.ScheduleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class ScheduleDAOImpl implements ScheduleDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SCHEDULE_ID = "schedule_id";

    private static final String SCHEDULE_DATE = "date";

    private static final String SELECT_ALL_SCHEDULES =
        "SELECT * FROM schedule ";

    private static final String INSERT_SCHEDULE_RECORD =
        "INSERT INTO schedule (" + SCHEDULE_DATE + ")" +
            "VALUES (:date)";

    private static final RowMapper<ScheduleData> scheduleDataMapper =
            new BeanPropertyRowMapper<>(ScheduleData.class);
    @Override
    public Long createScheduleRecord(LocalDate date) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update (
            INSERT_SCHEDULE_RECORD,
                new MapSqlParameterSource().addValue("date", date),
            key
        );
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public List<ScheduleData> getAllSchedules() {
        return jdbcTemplate.query(SELECT_ALL_SCHEDULES, scheduleDataMapper);
    }


}
