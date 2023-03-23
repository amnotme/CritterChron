package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.EmployeeDAO;
import com.udacity.jdnd.course3.critter.entity.EmployeeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * The type Employee dao.
 */
@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {

    /**
     * The Jdbc template.
     */
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;


    private static final String EMPLOYEE_NAME = "name";
    private static final String INSERT_EMPLOYEE =
        "INSERT INTO employee (name) " +
            "VALUES (:" + EMPLOYEE_NAME + ") ";

    private static final String SELECT_ALL_EMPLOYEES =
        "SELECT * FROM employee";

    private static final String SELECT_EMPLOYEE_BY_ID =
        "SELECT * FROM employee " +
            "WHERE id = :id";

    private static final RowMapper<EmployeeData> employeeDataMapper =
        new BeanPropertyRowMapper<>(EmployeeData.class);
    @Override
    public Long saveEmployee(EmployeeData employeeData) {
        KeyHolder key  = new GeneratedKeyHolder();
        jdbcTemplate.update(
            INSERT_EMPLOYEE,
            new MapSqlParameterSource()
                .addValue(EMPLOYEE_NAME, employeeData.getName()),
            key
        );
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public List<EmployeeData> getAllEmployees() {
        return jdbcTemplate.query(SELECT_ALL_EMPLOYEES, employeeDataMapper);
    }

    @Override
    public EmployeeData getEmployeeById(Long employeeId) {
        return jdbcTemplate.queryForObject(
            SELECT_EMPLOYEE_BY_ID,
            new MapSqlParameterSource()
                .addValue("id", employeeId),
                employeeDataMapper
        );
    }
}
