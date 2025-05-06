package org.aguzman.junit5app.ejemplos.models;

import java.math.BigDecimal;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public Cuenta() {

    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Cuenta)) {
            return false;
        }

        Cuenta c = (Cuenta)obj;
        if (this.persona == null || this.saldo == null) {
            return false;
        }

        return this.persona.equals(c.getPersona()) && this.saldo.compareTo(c.getSaldo()) == 0;
    }
}
