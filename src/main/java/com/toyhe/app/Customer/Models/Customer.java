package com.toyhe.app.Customer.Models;

import com.toyhe.app.Tickets.Model.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    long customerID   ;
    String customerName ;
    String customerEmail ;
    String customerAddress ;
    String phoneNumber ;
    long tripsNumber  ;
    @OneToMany(mappedBy = "customer")
    List<Ticket> tickets ;
}
