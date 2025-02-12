package com.toyhe.app.Auth.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;  // Renamed from 'role_id' to match standard naming

    @Column(unique = true, nullable = false)
    private String roleName;

    private String roleDescription;

    @ManyToMany(mappedBy = "userRoles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    private List<AccessRights> modulePermissions;
}
