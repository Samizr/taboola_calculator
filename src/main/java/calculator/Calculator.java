package main.java.calculator;

import java.util.*;

public class Calculator {

    private final LinkedHashMap<String, Integer> variables;
    private int openBrackets;

    public Calculator() {
        variables = new LinkedHashMap<>();
        openBrackets = 0;
    }
    public void parse(String line) throws Exception {
        ListIterator<Token> tokenIterator = Tokenizer.tokenize(line).listIterator();

        Token terminal;
        if (!tokenIterator.hasNext() || !(terminal = tokenIterator.next()).isVariable()) {
            throw new CalculatorException("Line must start with a variable");
        }
        String leftVar = terminal.getValue();

        if (!tokenIterator.hasNext() || !(terminal = tokenIterator.next()).isAssign()) {
            throw new CalculatorException("Missing assignment in second terminal");
        }
        String assignType = terminal.getValue();

        if (!tokenIterator.hasNext()) {
            throw new CalculatorException("No terminals after assign");
        }
        assign(leftVar, assignType, eval(tokenIterator));
        if (openBrackets != 0) {
            throw new CalculatorException("Open brackets left unclosed");
        }

    }

//TODO: is it ok to pass iterator around in functions?
    private int tmpEvalNumOrBracket(ListIterator<Token> tokenIterator) {
        if (tokenIterator.next().isOpenBracket()) {
            // Open bracket --> evaluate it as entire expression (...) OP X
            openBrackets++;
            return eval(tokenIterator);
        } else {
            tokenIterator.previous();
            return evalNumeric(tokenIterator); //TODO make it also open (.
        }
    }
    //Allowed start/operation ( or num
    //Allowed after num - operation or )
    private int eval(ListIterator<Token> tokenIterator) {
        assert(tokenIterator.hasNext());
        int leftValue;
        // Peek at next token for bracket option
        leftValue = tmpEvalNumOrBracket(tokenIterator);

        if (!tokenIterator.hasNext())
            return leftValue;

        // Peek if closed bracket after number
        if (tokenIterator.next().isClosedBracket()) {
            openBrackets--;
            if (openBrackets < 0)
                throw new CalculatorException("Met closed bracket without corresponding open bracket", tokenIterator.previousIndex());
            return leftValue;
        }
        tokenIterator.previous();

        // Force operation=
        char operation = evalOperator(tokenIterator);
        while (operation == '*' || operation == '/') {
            int rightValue = tmpEvalNumOrBracket(tokenIterator);

            leftValue = calculate(leftValue, operation, rightValue); //TODO: if operator is a class, op.calculate(left,right) :)
            if (!tokenIterator.hasNext()) {
                return leftValue;
            }

            // Peek at next one. if it's closed return
            if (tokenIterator.next().isClosedBracket()) {
                openBrackets--;
                if (openBrackets < 0)
                    throw new CalculatorException("Met closed bracket without corresponding open bracket", tokenIterator.previousIndex());
                return leftValue;
            }
            tokenIterator.previous();
            // End of peek

            operation = evalOperator(tokenIterator);
        }
        return calculate(leftValue, operation, eval(tokenIterator));
    }
    private int calculate(int leftValue, char operation, int rightValue) {
        switch (operation) {
            case '+' -> {
                return leftValue + rightValue;
            }
            case '-' -> {
                return leftValue - rightValue;
            }
            case '*' -> {
                return leftValue * rightValue;
            }
            case '/' -> {
                return leftValue / rightValue;
            }
        }
        throw new CalculatorException("Unsupported operation"); //TODO: Questionable if this should be here, we already validated these in tokenizer.
    }

    private int evalNumeric(ListIterator<Token> tokenIterator) {
        if (!tokenIterator.hasNext()){
            throw new CalculatorException("Expression left open-ended");
        }
        Token terminal = tokenIterator.next();
        if (terminal.isInteger()) {
            return Integer.parseInt(terminal.getValue());
        }


        //TODO: Implement ()

        // Support different forms of variables
        String var = terminal.getValue();
        try {
            if (terminal.isVariable()) {
                return variables.get(var);
            } else if (terminal.isPreIncrement()) {
                var = var.replaceFirst("\\+\\+", "");
                return assign(var, "+=", 1);
            } else if (terminal.isPostIncrement()) {
                var = var.replaceFirst("\\+\\+", ""); //TODO - global vars all strings
                int preIncrement = variables.get(var);
                    assign(var, "+=", 1);
                    return preIncrement;
            }
        } catch (Exception e) {
            throw new CalculatorException("Variable " + var + " undefined"); //TODO: there must be a better way to do all these conditions
        }
        throw new CalculatorException("Expected variable or integer", tokenIterator.previousIndex());

    }

    private char evalOperator(ListIterator<Token> tokenIterator) { //TODO: should this be of type Operator?
        assert tokenIterator.hasNext();
        Token terminal = tokenIterator.next();
        if (terminal.isOperator()) {
            return terminal.getValue().charAt(0);
        }
        throw new CalculatorException("Expecting legal operator", tokenIterator.previousIndex());
    }

    private int assign(String var, String assignType, int value) { //TODO: assignables should be a class :(
        if (assignType.equals("+=")) {
            if (!variables.containsKey(var)) {
                throw new CalculatorException("Variable " + var + " undefined");
            }
            value += variables.get(var);
        } else if (assignType.equals("-=")) {
            if (!variables.containsKey(var)) {
                throw new CalculatorException("Variable " + var + " undefined");
            }
            value -= variables.get(var);
        }
        variables.put(var, value);
        return value;
    }
    //TODO: These or special CalculatorExceptions
    //TODO: Are we supporting x = -5 or y = -3 + 1
    //TODO: support preincs
    public String getState() {
        List<String> variablesAsStrings = new ArrayList<>();
        for (String key: variables.keySet()) {
            variablesAsStrings.add(String.format("%s=%d", key, variables.get(key)));
        }
        return String.format("(%s)", String.join(",",variablesAsStrings));
    }

    public void clearState() {
        variables.clear();
        assert(openBrackets == 0);
    }
}