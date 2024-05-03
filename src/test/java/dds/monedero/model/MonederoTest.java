package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner() {
    cuenta.poner(BigDecimal.valueOf(1500));
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(BigDecimal.valueOf(-1500)));
  }

  @Test
  void TresDepositos() {
    cuenta.poner(BigDecimal.valueOf(1500));
    cuenta.poner(BigDecimal.valueOf(456));
    cuenta.poner(BigDecimal.valueOf(1900));
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(BigDecimal.valueOf(1500));
          cuenta.poner(BigDecimal.valueOf(456));
          cuenta.poner(BigDecimal.valueOf(1900));
          cuenta.poner(BigDecimal.valueOf(245));
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(BigDecimal.valueOf(90));
          cuenta.sacar(BigDecimal.valueOf(1001));
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(BigDecimal.valueOf(5000));
      cuenta.sacar(BigDecimal.valueOf(1001));
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(BigDecimal.valueOf(-500)));
  }

}