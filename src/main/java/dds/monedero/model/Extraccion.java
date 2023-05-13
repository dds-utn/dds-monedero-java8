package dds.monedero.model;

import java.time.LocalDate;

public class Extraccion extends Movimiento{
    public Extraccion(LocalDate fecha, double monto, boolean esDeposito) {
        super(fecha, monto, esDeposito);
    }
}
