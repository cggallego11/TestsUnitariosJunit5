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

        String esperado = "Cristobal"; // Espero que el nombre de la persona sea Cristobal
        String real = cuenta.getPersona(); // Obtengo el nombre de la persona

        Assertions.assertEquals(esperado,real); // Comparo el nombre esperado con el real
        Assertions.assertTrue(real.equals("Cristobal")); // Verifico que el nombre real sea igual a Cristobal
    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345"));// Creo una cuenta con el nombre Cristobal y saldo 1000.00

        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue()); // Verifico que el saldo de la cuenta sea 1000.12345
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0); // Verifico que el saldo de la cuenta no sea negativo
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0); // Verifico que el saldo de la cuenta sea positivo

    }

    @Test
    void testReferenciaCuenta(){
       Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997")); // Creo una cuenta con el nombre John Doe y saldo 8900.9997
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

        // Assertions.assertNotEquals(cuenta, cuenta2); // Verifico que las cuentas no sean iguales
        Assertions.assertEquals(cuenta, cuenta2); // Verifico que las cuentas sean iguales
    }

}