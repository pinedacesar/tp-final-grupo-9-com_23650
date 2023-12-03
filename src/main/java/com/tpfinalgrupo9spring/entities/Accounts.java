package com.tpfinalgrupo9spring.entities;

import com.tpfinalgrupo9spring.entities.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//TODO: Configuracion a DB
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; //pk, autoincremental
//    @NotBlank
//    @Column(name = "nombre")
//    String name; // tipo?
    @Enumerated(value=EnumType.STRING)
    AccountType tipo;
    @NotBlank
    @Column(name = "cbu", unique = true)
    String cbu; //unique, pk?
    @NotBlank
    @Column(name = "alias", unique = true)
    String alias; //unique
    @NotBlank
    @Column(name = "sucursal")
    String sucursal;

    @Column(name = "amount")
    BigDecimal amount; //not null

    @ManyToOne
//    @JoinColumn(name="owner_id", nullable=false)
    UserEntity owner; // fk_user.id

    Boolean isActive;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

}
