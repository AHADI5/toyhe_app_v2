package com.toyhe.app.Trips.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Emplacement {
    @Id
    long emplacementId;
    @Column(unique=true)
    String emplacementName;
    String description;

}
