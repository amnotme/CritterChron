package com.udacity.jdnd.course3.critter.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.List;

/**
 * The type Customer data.
 */
@Data // https://projectlombok.org/features/Data
@Table(name = "customer")
public class CustomerData {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    /**
     * The Name.
     */
    String name;
    /**
     * The Phone number.
     */
    String phoneNumber;
    /**
     * The Notes.
     */
    String notes;
}
