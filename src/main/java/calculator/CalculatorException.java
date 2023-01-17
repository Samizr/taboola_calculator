package main.java.calculator;

public class CalculatorException extends RuntimeException {
    public CalculatorException(String message, int exceptionPosition) {
        super(String.format("%s at terminal with index %d", message, exceptionPosition));
    }

    public CalculatorException(String message) {
        super(message);
    }
}

