package main.java.calculator.node;

import main.java.calculator.CalculatorException;

public class RootNode extends Node{
    @Override
    public Integer eval() {
        return null;
    }

    @Override
    public boolean settable() {
        return false;
    }

    @Override
    public void setValue(String string) {
        //This function should never be called for root nodes.
        assert(true);
    }
}
