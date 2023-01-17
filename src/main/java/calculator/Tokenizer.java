package main.java.calculator;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    public static LinkedList<Token> tokenize (String string) throws Exception {
        LinkedList<Token> tokens = new LinkedList<>();
        Pattern typesPattern = Pattern.compile(tokenTypesRegex());
        Matcher inputMatcher = typesPattern.matcher(string);
        int matchEnd = 0;
        while(inputMatcher.find()) {
            for (TokenType tokenType : TokenType.values()) {
                String match = inputMatcher.group(tokenType.name());
                matchEnd = inputMatcher.end();
                if (match != null) {
                    tokens.add(new Token(tokenType, match));
                    break;
                }
            }
        }
        if (matchEnd != inputMatcher.regionEnd()) { //TODO: better Exception?
            throw new CalculatorException("Failed to tokenize " + string.subSequence(matchEnd, string.length()));
        }
        return tokens;
    }
    private static String tokenTypesRegex() { //TODO: Should pass by token types?
        StringBuilder finalRegex = new StringBuilder();
        for (TokenType tokenType : TokenType.values()) {
            String namedPattern = String.format("\\G(?<%s>%s)", tokenType.name(), tokenType.getPattern());
            finalRegex.append(namedPattern).append("|");
        }
        finalRegex.append("[\\s\\t]+");
        return finalRegex.toString();
    }

}
//TODO: this catches i+++ as legitimate

//TODO: this can still tokenize sa @!!!

