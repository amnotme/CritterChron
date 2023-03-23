package com.udacity.jdnd.course3.critter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.PetData;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    /**
     * The Pet service.
     */
    @Autowired
    PetService petService;

    /**
     * The Object mapper.
     */
    @Autowired
    ObjectMapper objectMapper;

    /**
     * Save pet pet dto.
     *
     * @param petDTO the pet dto
     * @return the pet dto
     */
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetData petData =  petService.addPet(petDTO);
        return objectMapper.convertValue(petData, PetDTO.class);
    }

    /**
     * Gets pet.
     *
     * @param petId the pet id
     * @return the pet
     */
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return objectMapper.convertValue(petService.getPetById(petId), PetDTO.class);
        } catch (Exception exception) {
            logger.error("There doesn't seem to be a pet " + petId);
            return null;
        }
    }

    /**
     * Gets pets by owner.
     *
     * @param ownerId the owner id
     * @return the pets by owner
     */
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService
            .getPetsByOwner(ownerId)
            .stream()
            .map(petData -> objectMapper.convertValue(petData, PetDTO.class))
            .collect(Collectors.toList());
    }
}
