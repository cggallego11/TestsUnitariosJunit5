package org.aguzman.junit5app.ejemplos.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345")); // Creo una cuenta con el nombre Cristobal y saldo 1000.00

        //cuenta.setPersona("Cristobal"); // Establezco el nombre de la persona como Cristobal

        String esperado = "CRISTOBAL"; // Espero que el nombre de la persona sea Cristobal
        String real = cuenta.getPersona(); // Obtengo el nombre de la persona

        Assertions.assertEquals(esperado,real); // Comparo el nombre esperado con el real
        Assertions.assertTrue(real.equals("CRISTOBAL")); // Verifico que el nombre real sea igual a Cristobal
    }

}