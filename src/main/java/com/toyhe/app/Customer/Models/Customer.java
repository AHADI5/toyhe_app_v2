package com.toyhe.app.Customer.Models;

import com.toyhe.app.Auth.Model.Address;
import com.toyhe.app.Tickets.Model.Ticket;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerID;
    private String customerName;
    private String customerEmail;
    private String phoneNumber;
    private long tripsNumber;
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    @OneToOne
    private Address address;
    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets;
    private boolean  isCompany  ;
}
