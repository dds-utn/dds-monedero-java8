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

  @Test(expected = MontoNegativoException.class)
  public void PonerMontoNegativo() {
    cuenta.poner(new BigDecimal(-1500));
  }

  @Test
  public void TresDepositos() {
    cuenta.poner(new BigDecimal(1500));
    cuenta.poner(new BigDecimal(456));
    cuenta.poner(new BigDecimal(1900));
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(new BigDecimal(1500));
    cuenta.poner(new BigDecimal(456));
    cuenta.poner(new BigDecimal(1900));
    cuenta.poner(new BigDecimal(245));
  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(new BigDecimal(90));
    cuenta.sacar(new BigDecimal(1001));
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(new BigDecimal(5000));
    cuenta.sacar(new BigDecimal(1001));
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(new BigDecimal(-500));
  }

}