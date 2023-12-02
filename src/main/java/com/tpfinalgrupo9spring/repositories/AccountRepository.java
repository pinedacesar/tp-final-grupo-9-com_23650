package com.tpfinalgrupo9spring.repositories;

import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    // contar por cantida de cuentas de usuario

    long countByOwner(UserEntity owner);
    boolean existsByAlias(String alias);
    boolean existsByCbu(String cbu);

}
