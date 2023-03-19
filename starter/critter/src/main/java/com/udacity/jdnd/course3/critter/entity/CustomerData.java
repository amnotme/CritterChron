package com.udacity.jdnd.course3.critter.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.List;

@Data // https://projectlombok.org/features/Data
@Table(name = "customer")
public class CustomerData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String phoneNumber;
    String notes;


}
