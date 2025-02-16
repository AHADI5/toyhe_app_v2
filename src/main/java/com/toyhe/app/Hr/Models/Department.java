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
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;
    private String departmentName;
    private String description;

    @ManyToOne
    private Employee manager;
    @OneToMany
    List<Employee> employees;
    @ManyToOne
    private Department parentDepartment;

    @OneToMany
    private List<Department> DepartmentChildren;
    @OneToMany(mappedBy = "department")
    List<Position> positions;

}
