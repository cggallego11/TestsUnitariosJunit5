package org.aguzman.junit5app.ejemplos.models;

import org.aguzman.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Indica que la clase de prueba se ejecutará en el ciclo de vida de la clase
// Esto significa que los métodos @BeforeAll y @AfterAll se ejecutarán una vez para toda la clase, en lugar de una vez por cada método de prueba
class CuentaTest {

    Cuenta cuenta;

    //Este método se ejecuta antes de cada test
    @BeforeEach
    void initMetodoTest(){
        System.out.println("Iniciando el test");
         cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345"));
    }

    //Este método se ejecuta después de cada test
    @AfterEach
    void tearDown(){
        System.out.println("Finalizando el test");
    }

    //Este método se ejecuta una vez antes de todos los tests
    @BeforeAll
     void beforeAll(){
        System.out.println("Iniciando los tests");
    }

    //Este método se ejecuta una vez después de todos los tests
    @AfterAll
     void afterAll(){
        System.out.println("Finalizando los tests");
    }

    @Test
    @DisplayName("Test de nombre de cuenta")
    void testNombreCuenta() {

        //Preparación del escenario

        //cuenta.setPersona("Cristobal"); // Establezco el nombre de la persona como Cristobal

        //Lo que espero que devuelva el método getPersona()
        String esperado = "Cristobal"; // Espero que el nombre de la persona sea Cristobal

        //Lo que realmente devuelve el método getPersona()
        String real = cuenta.getPersona(); // Obtengo el nombre de la persona

        //Verifico que el nombre de la persona no sea nulo
        assertNotNull(real, () -> "La cuenta no puede ser nula");

        //Verifico que el nombre de la persona sea igual al esperado
        assertEquals(esperado,real, () -> "El nombre de la cuenta no es el que se esperaba"); // Comparo el nombre esperado con el real

        //Otra forma de verificar que el nombre de la persona sea igual al esperado
        assertTrue(real.equals("Cristobal"),() -> "Nombre cuenta esperada debe ser igual a la real"); // Verifico que el nombre real sea igual a Cristobal


        //ESTE TEST VERIFICA QUE
        //1. Al crear una cuenta con el nombre "Cristobal", el método getPersona() devuelve correctamente ese nombre.
        //2. El valor no sea null
        //3. El valor sea exactamente el esperado "Cristobal"
    }

    @Test
    @DisplayName("Test de saldo de cuenta")
    @Disabled // Deshabilito este test
    void testSaldoCuenta(){

        //Verifico que el saldo no sea nulo
        assertNotNull(cuenta.getSaldo());

        //Verifico que el saldo sea exactamente 1000.12345 (esto fallará porque el saldo es negativo)
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        //Verifico que el saldo NO sea negativo (esto también fallará porque el saldo es negativo)
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);

        //Verifico que el saldo sea positivo (esto fallará porque el saldo es negativo)
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);


        //ESTE TEST VERIFICA QUE
        //1. Se crea una cuenta con un saldo megativo : -1000.12345
        //2. Luego se hacen aserciones que esperan un saldo positivo, lo cual provocará fallos en el test
        //3. Por eso, el test está deshabilitado con @Disabled
    }

    @Test
    @DisplayName("Test de referencia de cuenta")
    void testReferenciaCuenta(){

        //Creo dos objetos Cuenta con los mismos datos, nombre y saldo
        Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

        //Verifico que las cuentas no sean iguales
        //Assertions.assertNotEquals(cuenta, cuenta2);

        //Verifico que las cuentas sean iguales
        Assertions.assertEquals(cuenta, cuenta2);

        //ESTE TEST VERIFICA QUE
        //1. Se crean dos cuentas con los mismos datos
        //2. Se espera que las cuentas sean iguales, ya que se han implementado correctamente los métodos equals() y hashCode() en la clase Cuenta
        //3. Si no se implementan correctamente, el test fallará
        //4. Se espera que las cuentas sean iguales, ya que tienen el mismo nombre y saldo
    }

    @Test
    @DisplayName("Test de referencia de cuenta con el mismo objeto")
    void testDebitoCuenta(){

        //Creo una cuenta con el nombre Cristobal y saldo 1000.00
        Cuenta cuenta = new Cuenta("Cristobal", new BigDecimal("1000.12345"));

        //Realizo un debito de 100.00
        cuenta.debito(new BigDecimal(100));

        //Verifico que el saldo no sea nulo
        assertNotNull(cuenta.getSaldo());

        //Verifico que la parte entera del saldo sea 900.00
        assertEquals(900, cuenta.getSaldo().intValue());

        //Verifico que el saldo completo (con decimales) sea exactamente 900.12345
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());

        //ESTE TEST VERIFICA QUE
        //1. Se resta correctamente el monto de saldo de la cuenta
        //2. Se mantiene la precisión decimal del saldo
        //3. No deja el saldo en nulo ni lanza errores inesperados
    }

    @Test
    @DisplayName("Test de credito de cuenta")
    void testCreditoCuenta(){

        //Realizo un credito de 100.00
        cuenta.credito(new BigDecimal(100));

        //Verifico que el saldo no sea nulo
        assertNotNull(cuenta.getSaldo());

        //Verifico que la parte entera del saldo sea 1100.00
        assertEquals(1100, cuenta.getSaldo().intValue());

        //Verifico que el saldo completo (con decimales) sea exactamente 1100.12345
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());

        //ESTE TEST VERIFICA QUE
        //1. Se suma correctamente el monto al saldo de la cuenta
        //2. Mantiene la precisión decimal del saldo
        //3. No deja el saldo en nulo ni lanza errores inesperados
    }

    @Test
    @DisplayName("Test de excepcion de dinero insuficiente")
    void testDineroInsuficienteExceptionCuenta() {

        //Verifico que al intentar debitar más dinero del disponible, se lanza una excepción
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1500)); //Intento debitar 1500.00, más de lo que hay
        });

        //Obtengo el mensaje de la excepción lanzada
        String actual = exception.getMessage();

        //Defino el mensaje que espero recibir al lanzar la excepción
        String esperado = "Dinero insuficiente";

        //Verifico que el mensaje de la excepción sea el esperado
        assertEquals(esperado, actual);

        //ESTE TEST VERIFICA QUE
        //1. El método debito() lanza una excepción del tipo DineroInsuficienteException cuando se intenta retirar más dinero del que hay en la cuenta
        //2. El mensaje de la excepción es el correcto: "Dinero insuficiente"
    }

    @Test
    @DisplayName("Test de transferencia de dinero entre cuentas")
    void transferirDineroCuentas(){

        //Creo dos objetos de la clase Cuenta con nombres y saldos iniciales
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        //Creo un banco y le asigno un nombre
        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");


        //Realizo una transferencia de 500 desde cuenta2 (Andres) a cuenta1 (Jhon Doe)
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        //Verifico que el saldo de cuenta2 se haya reducido correctamente
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());

        //Verifico que el saldo de la cuenta1 haya aumentado correctamente
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

        //ESTE TEST VERIFICA QUE
        //1. Resta correctamente el monto de la cuenta origen (cuenta2)
        //2. Suma correctamente el monto a la cuenta destino (cuenta1)
        //3. Mantiene la precisión decimal en los saldos
    }

    @Test
    @DisplayName("Test de transferencia de dinero entre cuentas con assertAll")
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

        //ESTE TEST VERIFICA QUE
        //La transferencia de dinero entre cuentas funcione correctamente
        //Las relaciones entre objetos Banco y Cuenta estén bien implementadas
        //El banco mantiene correctamente sus cuentas
        //Se puede acceder a la información del banco desde una cuenta

    }

    @Test
    @EnabledOnOs(OS.WINDOWS) // Este test solo se ejecuta en Windows
    void testSoloWindows(){

    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC}) // Este test solo se ejecuta en Linux y Mac
    void testSoloLinuxMac(){

    }

    @Test
    @DisabledOnOs(OS.WINDOWS) // Este test no se ejecuta en Windows
    void noWindows(){

    }

    @Test
    void imprimirSystemProperties(){
        System.getProperties().forEach((k,v) -> {
            System.out.println(k + " : " + v);
        });
    }

    @Test
    @DisplayName("Test de saldo de cuenta")
    @Disabled // Deshabilito este test
    void testSaldoCuentaDev(){

        boolean esDev = "DEV".equals(System.getProperty("ambiente")); // Verifico si el ambiente es DEV

        //No se ejecuta el test si el ambiente no es DEV
        assumeTrue(esDev); // Asumo que el ambiente es DEV, si no lo es, el test no se ejecuta

        //Verifico que el saldo no sea nulo
        assertNotNull(cuenta.getSaldo());

        //Verifico que el saldo sea exactamente 1000.12345 (esto fallará porque el saldo es negativo)
        Assertions.assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        //Verifico que el saldo NO sea negativo (esto también fallará porque el saldo es negativo)
        Assertions.assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);

        //Verifico que el saldo sea positivo (esto fallará porque el saldo es negativo)
        Assertions.assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);


    }
}