package main.calculator;

import java.util.HashMap;
import java.util.LinkedList;

public class Calculator {

    private HashMap<String, Integer> variables;
    private Boolean prevIsNumber = false;
    private Boolean prevIsOperator = false;
    public void parse(String line) throws Exception {
        LinkedList<Token> tokens = Tokenizer.tokenize(line);

        if (tokens.size() < 1 || !tokens.getFirst().isVariable()) {
            throw new CalculatorException("Line must start with a variable");
        }
        String leftVar = tokens.removeFirst().getValue();

        if (tokens.size() < 1 || !tokens.getFirst().isAssign()) {
            throw new CalculatorException("Missing assignment in second terminal");
        }
        String assignType = tokens.removeFirst().getValue();

        if (tokens.size() < 3) {
            throw new CalculatorException("No terminals after assignable terminal");
        }
        assign(leftVar, assignType, eval(tokens, 2));




//        validate(tokens);
        System.out.println(tokens);
    }

    private int eval(LinkedList<Token> expression, int currentPosition) {
        if (expression.isEmpty())
            return 0;

        int leftValue = evalNumeric(expression.removeFirst(), currentPosition); //TODO make it also open (.
        if(expression.isEmpty())
            return leftValue;

        //TODO HERE ---> Now make it understand operations :)
    }

    private int evalNumeric(Token token, int currentPosition) {
        if (!token.isNumeric()) {
            throw new CalculatorException("Expecting token that has value", currentPosition);
        }
    }



//                if (token.isAssign()) {
//                    throw new CalculatorException("More than one assignable in line");
//                }
//                if (token.isOperator()) {
//                    if (prevIsOperation) {
//                        throw new CalculatorException("Two consecutive operations");
//                    }
//                    prevIsOperation = true;
//                    prevIsNumber = false;
//                }
//                if (token.isInteger() || token.isVariable() || token.isPostIncrement() || token.isPreIncrement()) {
//                    if (prevIsNumber) {
//                        throw new CalculatorException("Two consecutive integers/variables");
//                    }
//                    prevIsOperation = false;
//                    prevIsNumber = true;
//                }
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



    private void validate (LinkedList<Token> tokens) {
        if (tokens.size() < 1 || !tokens.get(0).isVariable()) {
            throw new CalculatorException("Line must start with a variable");
        }
        if (tokens.size() < 2 || !tokens.get(1).isAssign()) {
            throw new CalculatorException("Missing assignment in second terminal");
        }
        if (tokens.size() < 3) {
            throw new CalculatorException("No terminals after assignable terminal");
        }
        int openBrackets = 0;
        boolean prevIsNumber = false;
        boolean prevIsOperation = false;

        for (Token token: tokens.subList(2, tokens.size())) {
            if (token.isAssign()) {
                throw new CalculatorException("More than one assignable in line");
            }
            if (token.isOperator()) {
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

    private void assign(String var, String assignType, int value) {
        //TODO: implement assign types
        variables.put(var, value);
    }
    //TODO: These or special CalculatorExceptions
    //TODO: Are we supporting x = -5 or y = -3 + 1
}