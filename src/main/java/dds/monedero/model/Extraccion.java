package dds.monedero.model;

import java.time.LocalDate;

public class Extraccion extends Movimiento{
    public Extraccion(LocalDate fecha, double monto) {
        super(fecha, monto);
    }

    @Override
    public double calcularValor(Cuenta cuenta) {
        return cuenta.getSaldo() - getMonto();
    }

    @Override
    public double getMontoExtraccion() {
        return getMonto();
    }

    @Override
    public boolean isDeposito() {
        return false;
    }
}
