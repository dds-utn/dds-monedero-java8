package dds.monedero.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movimiento {
	private LocalDate fecha;
	private BigDecimal monto;
	private boolean esDeposito;

	public BigDecimal getMonto() {
		return monto;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public Movimiento(LocalDate fecha, BigDecimal monto, boolean esDeposito) {
		this.fecha = fecha;
		this.monto = monto;
		this.esDeposito = esDeposito;
	}

	public boolean fueDepositado(LocalDate fecha) {
		return isDeposito() && esDeLaFecha(fecha);
	}

	public boolean fueExtraido(LocalDate fecha) {
		return isExtraccion() && esDeLaFecha(fecha);
	}

	public boolean esDeLaFecha(LocalDate fecha) {
		return this.fecha.equals(fecha);
	}

	public boolean isDeposito() {
		return esDeposito;
	}

	public boolean isExtraccion() {
		return esDeposito;
	}


	public void agregateA(Cuenta cuenta) {
		cuenta.setSaldo(this.calcularValor(cuenta));
		cuenta.agregarMovimiento(fecha, monto, esDeposito);
	}

	public BigDecimal calcularValor(Cuenta cuenta) {
		if (esDeposito) {
			return cuenta.getSaldo().add(this.getMonto());
		} else {
			return cuenta.getSaldo().subtract(this.getMonto());
		}
	}
}
