package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dao.PetDAO;
import com.udacity.jdnd.course3.critter.daoImpl.PetDAOImpl;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.PetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Pet service.
 */
@Service
public class PetService {

    /**
     * The Pet dao.
     */
    @Autowired
    PetDAO petDAO = new PetDAOImpl();

    /**
     * The Object mapper.
     */
    @Autowired
    ObjectMapper objectMapper;


    /**
     * Add pet pet dto.
     *
     * @param petDTO the pet dto
     * @return the pet dto
     */
    public PetData addPet(PetDTO petDTO) {
        try {
            Long petId = petDAO.savePet(objectMapper.convertValue(petDTO, PetData.class));
            return petDAO.getPetById(petId);
        } catch (Exception exception) {
            return null;
        }
    }


    /**
     * Gets pets by owner.
     *
     * @param ownerId the owner id
     * @return the pets by owner
     */
    public List<PetData> getPetsByOwner(Long ownerId) {
        try {
            return petDAO.getPetsByOwnerID(ownerId);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets pet by id.
     *
     * @param petId the pet id
     * @return the pet by id
     */
    public PetData getPetById(Long petId) {
        try {
            return petDAO.getPetById(petId);
        } catch (Exception exception) {
            return null;
        }
    }
}
