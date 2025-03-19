package com.toyhe.app.Tickets.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Id
    @GeneratedValue
    private Long id;

    private double weight;
    private double volume;
    private String description;
}
