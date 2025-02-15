package com.toyhe.app.Hr.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Position {
    @Id
    @GeneratedValue
    long positionID  ;
    String positionName ;
    String description ;
    @OneToOne
    Employee employee ;
    @ManyToOne
    Department department ;

}
