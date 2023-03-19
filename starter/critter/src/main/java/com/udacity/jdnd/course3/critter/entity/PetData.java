package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.dto.PetType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "pet")
public class PetData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    PetType type;

    String name;

    Long ownerId;

    LocalDate birthDate;

    String notes;


}
