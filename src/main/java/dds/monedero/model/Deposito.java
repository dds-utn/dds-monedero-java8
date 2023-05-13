package dds.monedero.model;

import java.time.LocalDate;

public class Deposito extends Movimiento {

    public Deposito(LocalDate fecha, double monto, boolean esDeposito) {
        super(fecha, monto, esDeposito);
    }
}
