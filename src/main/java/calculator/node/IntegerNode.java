package main.java.calculator.node;

public class IntegerNode extends Node {
    private String integerString;

    public IntegerNode(String integerString) {
        super();
        this.integerString = integerString;
    }

    public IntegerNode() {
        this(null);
    }

    public static String getMatchingRegex() {
        return "[-]?\\d+";
    }

    @Override
    public Integer eval() {
        return Integer.valueOf(integerString);
    }

    @Override
    public void setValue(String integerString) {
        this.integerString = integerString;
    }

    @Override
    public boolean settable() {
        return true;
    }
}
