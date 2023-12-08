package com.tpfinalgrupo9spring.repositories;

import com.tpfinalgrupo9spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);

}
