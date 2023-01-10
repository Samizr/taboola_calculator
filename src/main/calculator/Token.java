package main.calculator;

import java.util.regex.Pattern;

enum TokenType { //TODO: Enum or hashtable?
    // Order dictates matching priority
    // Below types have no underscores to support Regex name-catching
    POSTINC("[a-zA-Z]\\w*\\+\\+"),
    PREINC("\\+\\+[a-zA-Z]\\w*"),
    VARIABLE("[a-zA-Z]\\w*"),
    INTEGER("\\d+"),
    ASSIGNABLE("[+-]?="),
    OPERATION("[\\*/\\+-]"),
    OPENBRACKET("\\("),
    CLOSEDBRACKET("\\)");

    private final Pattern pattern;
    TokenType(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
public class Token {
    private final TokenType type;
    private final String value;
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        return String.format("%s: %s", type, value);
    }


    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }
    public boolean isInteger() {
        return type == TokenType.INTEGER;
    }
    public boolean isOperation() {
        return type == TokenType.OPERATION;
    }
    public boolean isAssignable() {
        return type == TokenType.ASSIGNABLE;
    }
    public boolean isPostIncrement() {
        return type == TokenType.POSTINC;
    }
    public boolean isPreIncrement() {
        return type == TokenType.PREINC;
    }
    public boolean isOpenBracket() {
        return type == TokenType.OPENBRACKET;
    }
    public boolean isClosedBracket() {
        return type == TokenType.CLOSEDBRACKET;
    }
}

//TODO: Unit test tokenizer