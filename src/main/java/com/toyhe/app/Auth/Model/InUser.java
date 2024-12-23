package com.toyhe.app.Auth.Model;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class InUser extends User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String department;
    private String gender;

}
