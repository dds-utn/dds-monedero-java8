package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner() {
    cuenta.poner(1500);
  }

  @Test
  void PonerMontoNegativo() {
    final int montoNegativo = -1500;

    assertThrows(MontoNegativoException.class, () -> cuenta.poner(montoNegativo),
        montoNegativo + ": el monto a ingresar debe ser un valor positivo"
    );
  }

  @Test
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
        },
        "Ya excedio los 3 depositos diarios"
    );
  }

  @Test
  void ExtraerMasQueElSaldo() {
    final double saldoActual = 90;

    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(saldoActual);
          cuenta.sacar(1001);
        },
        "No puede sacar mas de " + saldoActual + " $"
    );
  }

  @Test
  public void ExtraerMasDe1000() {
    final double limite = cuenta.getMontoExtraidoA(LocalDate.now());
    cuenta.setSaldo(5000);

    assertThrows(MaximoExtraccionDiarioException.class, () -> cuenta.sacar(1001),
        "No puede extraer mas de $ " + 1000 + " diarios, límite: " + limite
    );
  }

  @Test
  public void ExtraerMontoNegativo() {
    final double montoNegativo = -500;

    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(montoNegativo),
        montoNegativo + ": el monto a ingresar debe ser un valor positivo"
    );
  }

}