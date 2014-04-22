package dds.monedero.exceptions;

public class MontoNegativoException extends RuntimeException {
  public MontoNegativoException(String message) {
    super(message);
  }
}