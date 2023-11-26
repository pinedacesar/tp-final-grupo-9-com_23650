package com.tpfinalgrupo9spring.repositories;

import com.tpfinalgrupo9spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByAlias(String alias);
    boolean existsByCBU(String cbu);

}
