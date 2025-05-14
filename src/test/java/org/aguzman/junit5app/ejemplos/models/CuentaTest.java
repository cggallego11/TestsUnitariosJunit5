package org.aguzman.junit5app.ejemplos.models;

import org.aguzman.junit5app.ejemplos.exceptions.DineroInsuficienteException;
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
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("-1000.12345"));// Creo una cuenta con el nombre Cristobal y saldo 1000.00

        assertNotNull(cuenta.getSaldo()); // Verifico que el saldo no sea nulo

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

    @Test
    void testDebitoCuenta(){
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345")); // Creo una cuenta con el nombre Cristobal y saldo 1000.00
        cuenta.debito(new BigDecimal(100)); // Realizo un debito de 100.00

        assertNotNull(cuenta.getSaldo()); // Verifico que el saldo no sea nulo
        assertEquals(900, cuenta.getSaldo().intValue()); // Verifico que el saldo de la cuenta sea 900.00
        assertEquals("900.12345", cuenta.getSaldo().toPlainString()); // Verifico que el saldo de la cuenta sea 900.12345
    }

    @Test
    void testCreditoCuenta(){
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345")); // Creo una cuenta con el nombre Cristobal y saldo 1000.00
        cuenta.credito(new BigDecimal(100)); // Realizo un credito de 100.00

        assertNotNull(cuenta.getSaldo()); // Verifico que el saldo no sea nulo
        assertEquals(1100, cuenta.getSaldo().intValue()); // Verifico que el saldo de la cuenta sea 900.00
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString()); // Verifico que el saldo de la cuenta sea 900.12345
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345")); // Creo una cuenta con el nombre Cristobal y saldo 1000.00
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {

            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage(); // Obtengo el mensaje de la excepcion
        String esperado = "Dinero insuficiente"; // Espero que el mensaje de la excepcion sea Dinero Insuficiente
        assertEquals(esperado, actual); // Verifico que el mensaje de la excepcion sea el esperado
    }

    @Test
    void transferirDineroCuentas(){
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500")); // Creo una cuenta con el nombre Jhon Doe y saldo 2500
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989")); // Creo una cuenta con el nombre Cristobal y saldo 1000

        Banco banco = new Banco(); // Creo un banco
        banco.setNombre("Banco del Estado"); // Establezco el nombre del banco como Banco del Estado

        banco.transferir(cuenta2, cuenta1, new BigDecimal(500)); // Realizo una transferencia de 500.00 de la cuenta 2 a la cuenta 1

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()); // Verifico que el saldo de la cuenta 2 sea 1000.8989
        assertEquals("3000", cuenta1.getSaldo().toPlainString()); // Verifico que el saldo de la cuenta 1 sea 3000
    }

    @Test
    void testRelacionBancoCuentas(){

        //Preparación del escenario
        //Se crean dos objetos de la clase Cuenta con nombres y saldos iniciales
        //BigDecimal es una clase de Java que permite trabajar con números decimales de manera precisa
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500")); // Creo una cuenta con el nombre Jhon Doe y saldo 2500
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989")); // Creo una cuenta con el nombre Cristobal y saldo 1000


        //Se crea un objeto de la clase Banco
        //Se agregarán las cuentas creadas anteriormente al banco
        Banco banco = new Banco(); // Creo un banco
        banco.addCuenta(cuenta1); // Agrego la cuenta 1 al banco
        banco.addCuenta(cuenta2); // Agrego la cuenta 2 al banco

        //Se asigna un nombre al banco
        banco.setNombre("Banco del Estado"); // Establezco el nombre del banco como Banco del Estado
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500)); // Realizo una transferencia de 500.00 de la cuenta 2 a la cuenta 1

        //AssertAll agrupa todas las verificaciones. Si una falla, las demás aún se ejecutan
        assertAll(
                () -> { assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()); }, // Verifico que el saldo de la cuenta 2 sea 1000.8989
                () -> { assertEquals("3000", cuenta1.getSaldo().toPlainString()); }, // Verifico que el saldo de la cuenta 1 sea 3000
                () -> { assertEquals(2, banco.getCuentas().size()); }, // Verifico que el banco tenga 2 cuentas
                () -> { assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()); }, // Verifico que el nombre del banco de la cuenta 1 sea Banco del Estado
                () -> { assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("Andres"))); } // Verifico que el banco tenga una cuenta con el nombre Andres
        );

        //SECUENCIA
        //1. Se crean las cuentas
        //2. Se crea el banco y se agregan las cuentas
        //3. Se establece el nombre del banco
        //4. Se realiza la transferencia de dinero entre las cuentas
        //5. Se realizan las aserciones dentro de assertAll
    }
}