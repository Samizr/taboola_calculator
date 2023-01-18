package main.java.calculator;

public class CalculatorException extends RuntimeException {
    public CalculatorException(String message, String token) {
        super(String.format("%s near token %s", message, token));
    }

    public CalculatorException(String message) {
        super(message);
    }
}

