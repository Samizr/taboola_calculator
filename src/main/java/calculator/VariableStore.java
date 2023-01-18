package main.java.calculator;

import java.util.LinkedHashMap;

public class VariableStore extends LinkedHashMap<String, Integer> {
    public VariableStore() {
        super();
    }

    public void assign(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        super.put(var, value);
    }

    public Integer addToVariable(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        Integer finalValue = super.get(var) + value;
        assign(var, finalValue);
        return finalValue;
    }

    public Integer subtractFromVariable(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        Integer finalValue = super.get(var) - value;
        assign(var, finalValue);
        return finalValue;
    }

}
