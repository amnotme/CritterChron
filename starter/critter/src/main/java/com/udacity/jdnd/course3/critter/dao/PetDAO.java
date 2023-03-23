package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.PetData;

import java.util.List;

/**
 * The interface Pet dao.
 */
public interface PetDAO {

    /**
     * Save pet long.
     *
     * @param petData the pet data
     * @return the long
     */
    Long savePet(PetData petData);

    /**
     * Gets all pets.
     *
     * @return the all pets
     */
    List<PetData> getAllPets();

    /**
     * Gets pet by id.
     *
     * @param petId the pet id
     * @return the pet by id
     */
    PetData getPetById(Long petId);

    /**
     * Gets pets by owner id.
     *
     * @param ownerId the owner id
     * @return the pets by owner id
     */
    List<PetData> getPetsByOwnerID(Long ownerId);

}
