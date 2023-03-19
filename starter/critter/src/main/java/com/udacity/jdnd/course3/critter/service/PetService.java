package com.udacity.jdnd.course3.critter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dao.PetDAO;
import com.udacity.jdnd.course3.critter.daoImpl.PetDAOImpl;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.PetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    PetDAO petDAO = new PetDAOImpl();

    @Autowired
    ObjectMapper objectMapper;


    public PetDTO addPet(PetDTO petDTO) {
        Long petId = petDAO.savePet(objectMapper.convertValue(petDTO, PetData.class));
        return objectMapper.convertValue(petDAO.getPetById(petId), PetDTO.class);
    }


    public List<PetDTO> getAllPets() {
        List<PetDTO> pets = new ArrayList<>();

        for (PetData petData : petDAO.getAllPets()) {
            pets.add(objectMapper.convertValue(petData, PetDTO.class));
        }
        return pets;
    }
}
