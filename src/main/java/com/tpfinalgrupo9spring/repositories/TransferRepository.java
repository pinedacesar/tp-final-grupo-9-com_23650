package com.tpfinalgrupo9spring.repositories;


import com.tpfinalgrupo9spring.entities.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfers, Long> {
}
