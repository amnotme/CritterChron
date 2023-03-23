package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.dto.PetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The type Pet data.
 */
@Data
@Table(name = "pet")
public class PetData {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    /**
     * The Type.
     */
    @Enumerated(EnumType.STRING)
    PetType type;

    /**
     * The Name.
     */
    String name;

    /**
     * The Owner id.
     */
    Long ownerId;

    /**
     * The Birth date.
     */
    LocalDate birthDate;

    /**
     * The Notes.
     */
    String notes;

}
