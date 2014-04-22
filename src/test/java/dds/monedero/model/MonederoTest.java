package dds.monedero.model;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class MonederoTest {
	private Cuenta cuenta;

	@Before
	public void init() {
		cuenta = new Cuenta();
	}

	@Test
	public void Poner() {
		cuenta.poner(new BigDecimal(1500));
	}

	@Test
	public void PonerMontoNegativo() {
		try {
			cuenta.poner(new BigDecimal(-1500));
		} catch (MontoNegativoException ex) {

		}

	}

	@Test
	public void TresDepositos() {
		cuenta.poner(new BigDecimal(1500));
		cuenta.poner(new BigDecimal(456));
		cuenta.poner(new BigDecimal(1900));
	}

	@Test
	public void MasDeTresDepositos() {
		try {
			cuenta.poner(new BigDecimal(1500));
			cuenta.poner(new BigDecimal(456));
			cuenta.poner(new BigDecimal(1900));
			cuenta.poner(new BigDecimal(245));
		} catch (MaximaCantidadDepositosException ex) {

		}

	}

	@Test
	public void ExtraerMasQueElSaldo() {
		try {
			cuenta.setSaldo(new BigDecimal(90));
			cuenta.sacar(new BigDecimal(1001));
		} catch (SaldoMenorException ex) {

		}
	}

	@Test
	public void ExtraerMasDe1000() {
		try {
			cuenta.setSaldo(new BigDecimal(5000));
			cuenta.sacar(new BigDecimal(1001));
		} catch (MaximoExtraccionDiarioException ex) {

		}
	}

	@Test
	public void ExtraerMontoNegativo() {
		try {
			cuenta.sacar(new BigDecimal(-500));
		} catch (MontoNegativoException ex) {

		}
	}

}