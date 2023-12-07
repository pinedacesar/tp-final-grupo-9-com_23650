package com.tpfinalgrupo9spring.entities.dtos;


import com.tpfinalgrupo9spring.entities.Accounts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String dni;
    private String address;
    private Date birthday_date;
    private List<Accounts> accounts;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
