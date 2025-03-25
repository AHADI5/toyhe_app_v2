package com.toyhe.app.Customer.Models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("NON_COMPANY")
@SuperBuilder
public class NonCompanyCustomer extends Customer {
    private String nationality;
    private Date dateOfBirth;
    private String placeOfBirth;
    private boolean isCompany = false; // Always false

}
