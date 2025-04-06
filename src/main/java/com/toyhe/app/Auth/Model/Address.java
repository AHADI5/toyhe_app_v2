package com.toyhe.app.Auth.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Address {
    @Id
    @GeneratedValue
    long id;
    long number;
    String province;
    String town ;
    String commune  ;
    String quarter ;
    String avenue ;
}
