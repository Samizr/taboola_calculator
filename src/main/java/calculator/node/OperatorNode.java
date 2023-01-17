package main.java.calculator.node;

import java.util.regex.Pattern;

public class OperatorNode extends Node {
    enum OperatorType {
        MUL("*"),
        ADD("+"),
        SUB("-"),
        DIV("-");

        private final String operation;

        OperatorType(String operation) {
            this.operation = operation;
        }
    }

    private final OperatorType operatorType;

    public OperatorNode(Node left, Node right, String operation) {
        super(left, right);
        switch (operation) {
            case ("*") -> operatorType = OperatorType.MUL;
            case ("/") -> operatorType = OperatorType.DIV;
            case ("+") -> operatorType = OperatorType.ADD;
            case ("-") -> operatorType = OperatorType.SUB;
            // We shouldn't reach this by design of parent parser.
            default -> throw new IllegalStateException("Unexpected operation: " + operation);
        }
    }

    public static String getMatchingRegex() {
        StringBuilder sb = new StringBuilder();
        for (OperatorType type : OperatorType.values()) {
            String operation = "\\" + type.operation;
            sb.append(operation);
        }
        return Pattern.quote(sb.toString());
    }

    @Override
    public int eval() {
        switch (operatorType) {
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
}
