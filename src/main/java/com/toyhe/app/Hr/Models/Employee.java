package com.toyhe.app.Hr.Models;

import jakarta.persistence.*;
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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long employeeId;

    //Personal information
    String firstName;
    String lastName;
    String email;
    String phone;
    boolean isManager;
    String specialization;
    Degree degree;

    //Job information
    @OneToMany(mappedBy = "manager")
    List<Department> managedDepartment;
    @ManyToOne
    @JoinColumn(name = "departement_id")
    Department department;

    @OneToOne
    Position position ;
    @OneToMany
    List<Employee> subordinates;

    @OneToMany
    List<Department> managedDepartments;




}
