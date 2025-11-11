package Aud.Ex2.CalculatorV2;

public class UnknownOperatorException extends RuntimeException {
    public UnknownOperatorException(char operator) {
        super(String.valueOf(operator));
    }
}
