package main.java.calculator.node;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OperatorNode extends Node {
    enum OperatorType {
        MUL("*"),
        ADD("+"),
        SUB("-"),
        DIV("/");

        private final String operation;

        OperatorType(String operation) {
            this.operation = operation;
        }
    }

    private OperatorType operator;

    public OperatorNode() {
        super();
    }

    public OperatorNode(Node left, Node right, Node parent) {
        super(left, right, parent);
    }

    public static String getMatchingRegex() {
        StringBuilder sb = new StringBuilder("[");
        for (OperatorType type: OperatorType.values())
            sb.append("\\%s".formatted(type.operation));
        return sb.append("]").toString();
    }

    @Override
    public Integer eval() {
        switch (operator) {
            case MUL -> {
                return left.eval() * right.eval();
            }
            case DIV -> {
                return left.eval() / right.eval();
            }
            case ADD -> {
                return left.eval() + right.eval();
            }
            default -> {
                return left.eval() - right.eval();
            }

        }
    }

    @Override
    public void setValue(String operator) {
        switch (operator) {
            case ("*") -> this.operator = OperatorType.MUL;
            case ("/") -> this.operator = OperatorType.DIV;
            case ("+") -> this.operator = OperatorType.ADD;
            case ("-") -> this.operator = OperatorType.SUB;
            default -> throw new IllegalStateException("Expected operator but got: " + operator);
            //todo: unify exceptions.
        }
    }
    @Override
    public boolean settable() {
        return true;
    }
}
