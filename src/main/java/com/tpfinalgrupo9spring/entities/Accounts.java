package com.tpfinalgrupo9spring.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//TODO: Configuracion a DB
public class Accounts {
    Long id; //pk, autoincremental
    String nombre; // tipo?
    String cbu; //unique, pk?
    String alias; //unique
    String sucursal;
    Double amount; //not null
    Long owner; // fk_user.id

}
