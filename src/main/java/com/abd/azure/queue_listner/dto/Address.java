package com.abd.azure.queue_listner.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    private String id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}
