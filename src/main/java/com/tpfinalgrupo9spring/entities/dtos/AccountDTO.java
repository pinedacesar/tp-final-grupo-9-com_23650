package com.tpfinalgrupo9spring.entities.dtos;


import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.enums.AccountType;
import lombok.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
//@NoArgsConstructor
public class AccountDTO {

    private Long id;

    private AccountType tipo;

    private String cbu;

    private String alias;

    private BigDecimal amount;
    private String sucursal;

    private Long ownerId;
    private UserEntity owner;
    private Boolean isActive;

}
