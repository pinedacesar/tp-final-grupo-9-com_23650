package com.tpfinalgrupo9spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "usuario", unique = true)
    private String username;

    @NotBlank
    @Column(name = "nombre")
    private String firstname;

    @NotBlank
    @Column(name = "apellido")
    private String lastname;

    @NotBlank
    @Column(name = "contrasenia")
    private String password;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(unique = true)
    private String dni;

    @Column(name = "direccion")
    private String address;

    @Column(name = "fecha_cumpleanios")
    private Date birthday_date;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @OneToMany(mappedBy="Accounts")
    private List<Accounts> accounts;

}
