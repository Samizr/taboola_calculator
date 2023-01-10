package main.calculator;

import java.util.ArrayList;
import java.util.HashMap;

public class Calculator {

    private HashMap<String, Integer> variables;

    public void parse(String line) throws Exception {
        ArrayList<Token> tokens = Tokenizer.tokenize(line);
        validate(tokens);
        System.out.println(tokens);
    }
        // get left char
        // if terminal
            // Translate to int or get value of variable
            // get next operation (if not operation or end of string fail)
                // if * , need to know if terminal or "(", if terminal do operation and continue
                // if + breakdown next
            // break down next value

        // if "(" break_down further
        // if ")" return from breakdown
        // deal with increments


    private void validate (ArrayList<Token> tokens) {
        if (tokens.size() < 1 || !tokens.get(0).isVariable()) {
            throw new CalculatorException("Line must start with a variable");
        }
        if (tokens.size() < 2 || !tokens.get(1).isAssignable()) {
            throw new CalculatorException("Missing assignment in second terminal");
        }
        if (tokens.size() < 3) {
            throw new CalculatorException("No terminals after assigneable terminal");
        }
        int openBrackets = 0;
        boolean prevIsNumber = false;
        boolean prevIsOperation = false;

        for (Token token: tokens.subList(2, tokens.size())) {
            if (token.isAssignable()) {
                throw new CalculatorException("More than one assigneable in line");
            }
            if (token.isOperation()) {
                if (prevIsOperation) {
                    throw new CalculatorException("Two consecutive operations");
                }
                prevIsOperation = true;
                prevIsNumber = false;
            }
            if (token.isInteger() || token.isVariable() || token.isPostIncrement() || token.isPreIncrement()) {
                if (prevIsNumber) {
                    throw new CalculatorException("Two consecutive integers/variables");
                }
                prevIsOperation = false;
                prevIsNumber = true;
            }
            if (token.isOpenBracket()) {
                openBrackets++;
            }
            if (token.isClosedBracket()) {
                openBrackets--;
                if (openBrackets < 0) {
                    throw new CalculatorException("Closed bracket missing open bracket");
                }
            }

        }
        if (openBrackets > 0) {
            throw new CalculatorException("Expression has too many open brackets");
        }
        if (prevIsOperation) {
            throw new CalculatorException("Operation not completed");
        }
    }
    //TODO: These or special CalculatorExceptions
    //TODO: Are we supporting x = -5 or y = -3 + 1
}