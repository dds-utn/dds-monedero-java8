package dds.monedero.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {

	private BigDecimal saldo = new BigDecimal(0);
	private List<Movimiento> movimientos;

	// ********************************************************
	// ** Constructors & initialization
	// ********************************************************

	public Cuenta() {
		this.saldo = new BigDecimal(0);
		this.inicializar();
	}

	public Cuenta(double montoInicial) {
		this.saldo = new BigDecimal(montoInicial);
		this.inicializar();
	}

	public void inicializar() {
		this.movimientos = new ArrayList<Movimiento>();
	}

	// ********************************************************
	// ** Actions
	// ********************************************************

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public void poner(BigDecimal cuanto) {
		if (cuanto.doubleValue() <= 0) {
			throw new MontoNegativoException(cuanto
					+ ": el monto a ingresar debe ser un valor positivo");
		}

		if (this.getMovimientos().stream()
				.filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
			throw new MaximaCantidadDepositosException("Ya excedio los " + 3
					+ " depositos diarios");
		}

		new Movimiento(new Date(), cuanto, true).agregateA(this);
	}

	public void sacar(BigDecimal cuanto) {
		if (cuanto.doubleValue() <= 0) {
			throw new MontoNegativoException(cuanto
					+ ": el monto a ingresar debe ser un valor positivo");
		}
		if (this.getSaldo().subtract(cuanto).doubleValue() < 0) {
			throw new SaldoMenorException("No puede sacar mas de "
					+ this.getSaldo() + " $");
		}
		BigDecimal montoExtraidoHoy = this.getMontoExtraidoA(new Date());
		BigDecimal limite = new BigDecimal(1000).subtract(montoExtraidoHoy);
		if (cuanto.doubleValue() > limite.doubleValue()) {
			throw new MaximoExtraccionDiarioException(
					"No puede extraer mas de $ " + 1000 + " diarios, l�mite: "
							+ limite);
		}
		new Movimiento(new Date(), cuanto, false).agregateA(this);
	}

	// ********************************************************
	// ** Movimientos
	// ********************************************************

	public void agregarMovimiento(Date fecha, BigDecimal cuanto,
			boolean esDeposito) {
		Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
		this.movimientos.add(movimiento);
	}

	public BigDecimal getMontoExtraidoA(Date fecha) {
		return this
				.getMovimientos()
				.stream()
				.filter(movimiento -> !movimiento.isDeposito()
						&& movimiento.getFecha().equals(fecha))
				.map(Movimiento::getMonto)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}
