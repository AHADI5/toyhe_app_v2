package com.toyhe.app.Auth.Repositories;


import com.toyhe.app.Auth.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findUserRoleByRoleName(String userRoleName);
    Boolean  existsByRoleName(String roleName);
}
