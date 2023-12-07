package com.tpfinalgrupo9spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tpfinalgrupo9spring.entities.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name="owner_id", nullable=false)
    @JsonBackReference
    UserEntity owner; // fk_user.id

    Boolean isActive;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;


}
