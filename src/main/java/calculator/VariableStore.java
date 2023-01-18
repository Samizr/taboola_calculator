package main.java.calculator;

import java.util.LinkedHashMap;

public class VariableStore extends LinkedHashMap<String, Integer> {
    public VariableStore() {
        super();
    }

//    public void put(String var, Integer value) {
//        super.put(var, value);
//    }
    public void assign(String var, String assignType, Integer value) {
        switch (assignType) {
            case "+=" -> addToVariable(var, value);
            case "-=" -> subtractFromVariable(var, value);
            default -> put(var, value);
        }
    }

    public Integer addToVariable(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        Integer finalValue = super.get(var) + value;
        put(var, finalValue);
        return finalValue;
    }

    public Integer subtractFromVariable(String var, Integer value) {
        if (!super.containsKey(var)) {
            throw new CalculatorException(String.format("Variable %s undefined", var));
        }
        Integer finalValue = super.get(var) - value;
        put(var, finalValue);
        return finalValue;
    }

}
