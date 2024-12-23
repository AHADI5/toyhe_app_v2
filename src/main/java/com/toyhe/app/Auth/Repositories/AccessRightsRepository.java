package com.toyhe.app.Auth.Repositories;

import com.toyhe.app.Auth.Model.AccessRights;
import com.toyhe.app.Auth.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessRightsRepository  extends JpaRepository<AccessRights, Long> {
    List<AccessRights> findAccessRightsByUser(User user);
}
