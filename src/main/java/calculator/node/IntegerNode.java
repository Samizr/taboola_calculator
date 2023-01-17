package main.java.calculator.node;

public class IntegerNode extends Node {
    private final int value;

    public IntegerNode(Node left, Node right, int value) {
        super(left, right);
        this.value = value;
    }


    public static String getMatchingRegex() {
        return "\\d+";
    }

    @Override
    public int eval() {
        return value;
    }
}
