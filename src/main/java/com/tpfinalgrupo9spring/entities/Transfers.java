package com.tpfinalgrupo9spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    private Long origin;

    private Long target;

    private Date date;

    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    UserEntity owner; // fk_user.id

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
    private Boolean completed;
}
