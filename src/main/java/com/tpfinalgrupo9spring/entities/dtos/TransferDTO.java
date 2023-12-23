package com.tpfinalgrupo9spring.entities.dtos;

import com.tpfinalgrupo9spring.entities.UserEntity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransferDTO {
    private Long id;

    private Long origin;

    private Long target;

    private Date date;

    private BigDecimal amount;

    private UserEntity owner;
    private Long ownerId;
    private LocalDateTime created_at;

    private LocalDateTime updated_at;
    private Boolean completed;
}
