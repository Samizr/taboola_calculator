package main.java.calculator;

import java.util.LinkedHashMap;

public class VariableStore extends LinkedHashMap<String, Integer> {
    public VariableStore() {
        super();
    }

    public void assign(String var, Integer value) {
        super.put(var, value);
    }

    public void addToVariable(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        assign(var, super.get(var) + value);
    }

    public void subtractFromVariable(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        assign(var, super.get(var) - value);
    }

}
