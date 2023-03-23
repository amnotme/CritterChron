package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.CustomerDAO;
import com.udacity.jdnd.course3.critter.entity.CustomerData;
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
 * The type Customer dao.
 */
@Repository
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * The Jdbc template.
     */
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private static final String CUSTOMER_NAME = "name";

    private static final String CUSTOMER_PHONE = "phone_number";

    private static final String CUSTOMER_NOTES = "notes";

    private static final String INSERT_CUSTOMER =
        "INSERT INTO customer (name, phone_number, notes) " +
            "VALUES (:" + CUSTOMER_NAME + ", :" + CUSTOMER_PHONE + ", :" + CUSTOMER_NOTES + ")";

    private static final String SELECT_ALL_CUSTOMERS =
        "SELECT " +
            "id, " +
            "name, " +
            "phone_number, " +
            "notes " +
        "FROM customer";

    private static final String SELECT_CUSTOMER_BY_ID =
        "SELECT * FROM customer " +
            "WHERE id = :id";

    private static final RowMapper<CustomerData> customerDataMapper =
        new BeanPropertyRowMapper<>(CustomerData.class);

    @Override
    public Long saveCustomer(CustomerData customerData) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(
            INSERT_CUSTOMER,
            new MapSqlParameterSource()
                .addValue(CUSTOMER_NAME, customerData.getName())
                .addValue(CUSTOMER_PHONE, customerData.getPhoneNumber())
                .addValue(CUSTOMER_NOTES, customerData.getNotes()),
            key
        );
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public List<CustomerData> getAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, customerDataMapper);
    }

    @Override
    public CustomerData getCustomer(Long id) {
        return jdbcTemplate.queryForObject(
            SELECT_CUSTOMER_BY_ID,
            new MapSqlParameterSource().addValue("id", id),
            new BeanPropertyRowMapper<>(CustomerData.class)
        );
    }
}
