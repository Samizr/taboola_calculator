package main.java.calculator.token;

import java.util.regex.Pattern;

enum TokenType {
    // Order dictates matching priority
    // Below types have no underscores to support Regex name-catching
    PREINC("\\+\\+[a-zA-Z]\\w*"),
    PREDEC("--[a-zA-Z]\\w*"),
    POSTINC("[a-zA-Z]\\w*\\+\\+"),
    POSTDEC("[a-zA-Z]\\w*--"),
    VARIABLE("[a-zA-Z]\\w*"),
    ASSIGN("[+-]?="),
    OPERATOR("[\\+\\*-/]"),
    INTEGER("\\d+"),
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

    public boolean isPreInc() {
        return type == TokenType.PREINC;
    }
    public boolean isPreDec() {
        return type == TokenType.PREDEC;
    }
    public boolean isPostInc() {
        return type == TokenType.POSTINC;
    }
    public boolean isPostDec() {
        return type == TokenType.POSTDEC;
    }

    public boolean isVariable() {
        return type == TokenType.VARIABLE;
    }
    public boolean isInteger() {
        return type == TokenType.INTEGER;
    }
    public boolean isOperator() {
        return type == TokenType.OPERATOR;
    }
    public boolean isPriorityOperator() {
        return isOperator() && (value.contains("*") || value.contains("/"));
    }
    public boolean isAssign() {
        return type == TokenType.ASSIGN;
    }
    public boolean isOpenBracket() {
        return type == TokenType.OPENBRACKET;
    }
    public boolean isClosedBracket() {
        return type == TokenType.CLOSEDBRACKET;
    }

    public boolean isEvaluable() {
        return isPostInc() || isPostDec() || isPreInc() || isPreDec() || isInteger() || isVariable() || isOpenBracket();
    }

    public String getValue() {
        return value;
    }


}


//TODO: Unit test tokenizer
