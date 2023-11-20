package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.UserEntity;

//@Service
public class AccountService {

    // public final AccountRepository accountRepository;
    // public final UserRepository userRepository;

    public String create(Long owner, String tipo){
        Accounts account =new Accounts();

        UserEntity user =new UserEntity();
        // busqueda usuario x id en DB
        account.setNombre(tipo);
        account.setOwner(user.getId());
        account.setSucursal("111");
        //cambiar dni x cuil
        String numCuenta="001"; //contar en DB cantidad de owners con id del usr
        account.setCbu(generarCBU(account.getSucursal(), account.getNombre(),user.getDni(),numCuenta));

        //accountRepository.save(account);

        return "Cuenta creada satisfactoriamente";

    }


    private String generarCBU(String sucursal, String tipo, String cuil, String numCuenta){
        String cbu= "";

        cbu=cbu.concat(sucursal).concat("0000").concat(cuil).concat(numCuenta);

        return cbu;
    }
}


