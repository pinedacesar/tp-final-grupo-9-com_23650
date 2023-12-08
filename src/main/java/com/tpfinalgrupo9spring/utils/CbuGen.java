package com.tpfinalgrupo9spring.utils;

import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.exceptions.CbuGenerationException;
import org.springframework.stereotype.Component;

@Component
public class CbuGen {
    // ------------  Métodos a revisar  --------------
    private String generarCBU(String sucursal, String tipo, String cuil, String numCuenta) {
        String cbu = "";
        cbu = cbu.concat(sucursal).concat("0000").concat(cuil).concat(numCuenta);
        return cbu;
    }

    public String create_cbu(UserEntity usuario, Accounts cuenta, long numAccounts) throws CbuGenerationException {
        try {
            String dniVal =usuario.getDni();
            if (dniVal.length()>10)
                dniVal=dniVal.substring(0,9);
            String branchVal=String.valueOf(cuenta.getSucursal());
            if (branchVal.length()>4)
                branchVal=branchVal.substring(0,3);

            String entity = completarConCeros("1", 4);
            String dni = completarConCeros(dniVal, 10);
            String branch = completarConCeros(branchVal, 4);
            String type = completarConCeros(String.valueOf(cuenta.getTipo().ordinal()), 3);

            String cbu = entity + branch + dni + type + numAccounts;
            cuenta.setCbu(Long.valueOf(cbu).toString());
            return cbu;
        } catch (Exception e) {
            throw new CbuGenerationException("Error al generar el CBU: " + e.getMessage());
        }
    }

    private static String completarConCeros(String input, int longitudObjetivo) {
        StringBuilder sb = new StringBuilder(input);
        while (sb.length() < longitudObjetivo) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }
}

