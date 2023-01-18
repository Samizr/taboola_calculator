package main.java.calculator.token;

import main.java.calculator.CalculatorException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tokenizer {


    LinkedList<Token> tokens;
    public Tokenizer(String line){
        tokens = tokenize(line);
        validate(tokens);
    }

    public Token nextToken() {
        if (tokens.size() > 0) {
            return tokens.removeFirst();
        }
        return null;
    }

    private static LinkedList<Token> tokenize(String line) {
        LinkedList<Token> tokens = new LinkedList<>();
        int matchEnd = 0;
        Matcher matcher = compileAllTerminals().matcher(line);
        while (matcher.find()) {
            for (TokenType type : TokenType.values()) {
                String match = matcher.group(type.name());
                if (match != null) {
                    matchEnd = matcher.end();
                    tokens.add(new Token(type, match));
                }
            }
        }

        if (matchEnd != matcher.regionEnd()) {
            throw new CalculatorException("Failed to tokenize from " + line.subSequence(matchEnd, line.length()));
        }
        return tokens;
    }
    private static Pattern compileAllTerminals() {
        String finalRegex = Arrays.stream(TokenType.values()).map(type ->
                "\\G\\s*(?<%s>%s)\\s*".formatted(type.name(), type.getPattern())
        ).collect(Collectors.joining("|"));
        return Pattern.compile(finalRegex);
    }

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
        boolean prevIsOperation = true;

        for (Token token: tokens.subList(2, tokens.size())) {
            if (token.isAssign()) {
                throw new CalculatorException("More than one assignable in line");
            }
            else if (token.isOperator()) {
                if (prevIsOperation) {
                    throw new CalculatorException("Two consecutive operations");
                }
                prevIsOperation = true;
                prevIsNumber = false;
            }
            else if (token.isEvaluable() && !token.isOpenBracket()) {
                if (prevIsNumber) {
                    throw new CalculatorException("Two consecutive integers/variables");
                }
                prevIsOperation = false;
                prevIsNumber = true;
            }
            else if (token.isOpenBracket()) {
                openBrackets++;
            }
            else if (token.isClosedBracket()) {
                openBrackets--;
                if (openBrackets < 0) {
                    throw new CalculatorException("Closed bracket missing open bracket");
                }
                if (prevIsOperation) {
                    throw new CalculatorException("Bracket closed without completing operation");
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

}

//TODO: this is bad when line is empty


