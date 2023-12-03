package com.tpfinalgrupo9spring.entities;

import java.util.Random;

public class CbuGenerator {

    public static String generarCbu() {
        Random random = new Random();
        StringBuilder cbu = new StringBuilder();

        // Agregar un prefijo específico si es necesario
        cbu.append("1234");

        // Generar números aleatorios para el resto del CBU
        for (int i = 0; i < 18; i++) {
            cbu.append(random.nextInt(10));
        }

        return cbu.toString();
    }

    public static void main(String[] args) {
        String cbuGenerado = generarCbu();
        System.out.println("CBU generado: " + cbuGenerado);
    }
}
