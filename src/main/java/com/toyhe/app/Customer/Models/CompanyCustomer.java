package com.toyhe.app.Customer.Models;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("COMPANY")
@SuperBuilder
public class CompanyCustomer extends Customer {
    private int rccmNumber;
    private int nationalIdentificationNumber;
    private int taxRessortNumber;
    private boolean isCompany = true;
    private int creationYear  ;
}
