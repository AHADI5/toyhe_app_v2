package com.toyhe.app.Auth.Repositories;

import com.toyhe.app.Auth.Model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelRespository extends JpaRepository<Model, Long> {
    Optional<Model> findByModelName(String entityName);
}
