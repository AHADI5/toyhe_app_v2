package com.toyhe.app.Auth.Repositories;

import com.toyhe.app.Auth.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
