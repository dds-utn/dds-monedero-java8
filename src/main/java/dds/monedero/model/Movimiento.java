package dds.monedero.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

public class Movimiento {
	private Date fecha;
	private BigDecimal monto;
	private boolean esDeposito;

	public BigDecimal getMonto() {
		return monto;
	}

	public Date getFecha() {
		return fecha;
	}

	public Movimiento(Date fecha, BigDecimal monto, boolean esDeposito) {
		this.fecha = fecha;
		this.monto = monto;
		this.esDeposito = esDeposito;
	}

	public boolean fueDepositado(Date fecha) {
		return isDeposito() && esDeLaFecha(fecha);
	}

	public boolean fueExtraido(Date fecha) {
		return isExtraccion() && esDeLaFecha(fecha);
	}

	/**
	 * Uso un date format para elminar los segundos... no es muy feliz pero no
	 * se me ocurre nada mejor.
	 */
	public boolean esDeLaFecha(Date fecha) {
		DateFormat dateFormat = DateFormat.getInstance();
		return dateFormat.format(this.fecha).equals(dateFormat.format(fecha));
	}

	/* */

	public boolean isDeposito() {
		return esDeposito;
	}

	public boolean isExtraccion() {
		return esDeposito;
	}

	// ********************************************************
	// ** Validaciones
	// ********************************************************

	/*
	 * def ObtenerGanancia(Cuenta cuenta) { if (cuenta.nombre == "Dodino")
	 * 
	 * if (esDeposito) { var cuentaJavi = new Cuenta() var cuentaChris = new
	 * Cuenta() var cuentaPablo = new Cuenta() cuentajavi.saldo += cuenta.saldo
	 * * 0.25 cuentaChris.saldo += cuenta.saldo * 0.25 cuentaPablo.saldo +=
	 * cuenta.saldo * 0.5 cuenta.saldo = 0 }
	 * 
	 * }
	 */

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
