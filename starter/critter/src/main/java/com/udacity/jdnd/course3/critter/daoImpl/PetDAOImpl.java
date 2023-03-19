package com.udacity.jdnd.course3.critter.daoImpl;

import com.udacity.jdnd.course3.critter.dao.PetDAO;
import com.udacity.jdnd.course3.critter.entity.PetData;
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


@Repository
@Transactional
public class PetDAOImpl implements PetDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;


    private static final String PET_TYPE = "type";

    private static final String PET_NAME = "name";

    private static final String OWNER_ID = "owner_id";

    private static final String PET_BIRTH_DATE = "birth_date";

    private static final String PET_NOTES = "notes";

    private static final String INSERT_PET =
        "INSERT INTO pet (type, name, owner_id, birth_date, notes) " +
            "VALUES " +
            "(:" + PET_TYPE +
            ", :" + PET_NAME +
            ", :" + OWNER_ID +
            ", :" + PET_BIRTH_DATE +
            ", :" + PET_NOTES +
            ")";

    private static final String SELECT_ALL_PETS =
        "SELECT * FROM pet ";

    private static final String SELECT_PET_BY_ID =
        "SELECT * FROM pet " +
            "WHERE id = :id";

    private static final RowMapper<PetData> petDataMapper =
        new BeanPropertyRowMapper<>(PetData.class);

    @Override
    public Long savePet(PetData petData) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(
            INSERT_PET,
            new MapSqlParameterSource()
                    .addValue(PET_TYPE, petData.getType())
                    .addValue(PET_NAME, petData.getName())
                    .addValue(OWNER_ID, petData.getOwnerId())
                    .addValue(PET_BIRTH_DATE, petData.getBirthDate())
                    .addValue(PET_NOTES, petData.getNotes()),
                key
        );
        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public List<PetData> getAllPets() {
        return jdbcTemplate.query(SELECT_ALL_PETS, petDataMapper);
    }

    @Override
    public PetData getPetById(Long petId) {
        return jdbcTemplate.queryForObject(
            SELECT_PET_BY_ID,
            new MapSqlParameterSource().addValue("id", petId),
            new BeanPropertyRowMapper<>(PetData.class)
        );
    }
}
