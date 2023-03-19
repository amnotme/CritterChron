package com.udacity.jdnd.course3.critter.dao;

import com.udacity.jdnd.course3.critter.entity.PetData;

import java.util.List;

public interface PetDAO {

    Long savePet(PetData petData);

    List<PetData> getAllPets();

    PetData getPetById(Long petId);

    List<PetData> getPetsByOwnerID(Long ownerId);

}
