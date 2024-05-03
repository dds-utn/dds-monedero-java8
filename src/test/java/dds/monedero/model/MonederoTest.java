package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("Es posible poner $1500 en una cuenta vacía")
  void Poner() {
    cuenta.poner(BigDecimal.valueOf(1500));
  }

  @Test
  @DisplayName("No es posible poner montos negativos")
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(BigDecimal.valueOf(-1500)));
  }

  @Test
  @DisplayName("Es posible realizar múltiples depósitos consecutivos")
  void TresDepositos() {
    cuenta.poner(BigDecimal.valueOf(1500));
    cuenta.poner(BigDecimal.valueOf(456));
    cuenta.poner(BigDecimal.valueOf(1900));
  }

  @Test
  @DisplayName("No es posible superar la máxima cantidad de depositos diarios")
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(BigDecimal.valueOf(1500));
      cuenta.poner(BigDecimal.valueOf(456));
      cuenta.poner(BigDecimal.valueOf(1900));
      cuenta.poner(BigDecimal.valueOf(245));
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el saldo disponible")
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
      cuenta.setSaldo(BigDecimal.valueOf(90));
      cuenta.sacar(BigDecimal.valueOf(1001));
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el límite diario")
  void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(BigDecimal.valueOf(5000));
      cuenta.sacar(BigDecimal.valueOf(1001));
    });
  }

  @Test
  @DisplayName("No es posible extraer un monto negativo")
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(BigDecimal.valueOf(-500)));
  }

}